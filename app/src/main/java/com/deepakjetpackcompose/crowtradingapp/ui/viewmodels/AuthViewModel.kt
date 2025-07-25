package com.deepakjetpackcompose.crowtradingapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.crowtradingapp.data.model.CryptoModelItem
import com.deepakjetpackcompose.crowtradingapp.data.model.SparklineIn7d
import com.deepakjetpackcompose.crowtradingapp.domain.constant.BOUGHT
import com.deepakjetpackcompose.crowtradingapp.domain.constant.TRANSACTION
import com.deepakjetpackcompose.crowtradingapp.domain.constant.USER
import com.deepakjetpackcompose.crowtradingapp.domain.model.BuyCoinModel
import com.deepakjetpackcompose.crowtradingapp.domain.model.FirebaseCoinModel
import com.deepakjetpackcompose.crowtradingapp.domain.model.Transaction
import com.deepakjetpackcompose.crowtradingapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _isFav = MutableStateFlow<Boolean>(false)
    val isFav = _isFav.asStateFlow()

    private val _favCoinList = MutableStateFlow<List<FirebaseCoinModel>>(emptyList())
    val favCoinList = _favCoinList.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _loading = MutableStateFlow<UpdateBalance>(UpdateBalance.Idle)
    val loading = _loading.asStateFlow()

    private val _singleBoughtCoin = MutableStateFlow<BuyCoinModel?>(null)
    val singleBoughtCoin = _singleBoughtCoin.asStateFlow()

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _boughtCoins = MutableStateFlow<List<BuyCoinModel>>(emptyList())
    val boughtCoins = _boughtCoins.asStateFlow()

    private val _profileLoader=MutableStateFlow<Boolean>(false)
    val profileLoading= _profileLoader.asStateFlow()

    val isSelected= mutableIntStateOf(1)




    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        _authState.value = AuthState.Loading
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    onResult(true, "Login Successfully")
                    _authState.value = AuthState.Success
                }
                .addOnFailureListener {
                    onResult(false, it.localizedMessage.toString())
                    _authState.value = AuthState.Error(it.localizedMessage)
                }
        } catch (e: Exception) {
            onResult(false, e.localizedMessage.toString())
            _authState.value = AuthState.Error(e.localizedMessage)
        }
    }

    fun register(
        email: String,
        password: String,
        name: String,
        onResult: (Boolean, String) -> Unit
    ) {
        _authState.value = AuthState.Loading
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {

                    createUser(name, email) { success, msg ->
                        if (!success) onResult(false, msg)
                        else {
                            onResult(true, "Registered Successfully")
                            _authState.value = AuthState.Success
                        }
                    }

                }
                .addOnFailureListener {
                    onResult(false, it.localizedMessage.toString())
                    _authState.value = AuthState.Error(it.localizedMessage)
                }
        } catch (e: Exception) {
            onResult(false, "Registration Failed")
            _authState.value = AuthState.Error(e.localizedMessage)
        }
    }

    fun createUser(name: String, email: String, onResult: (Boolean, String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            firestore.collection(USER).document(uid)
                .set(User(name = name, email = email, balance = "0.00"))
                .addOnSuccessListener {
                    onResult(true, "Registered Successfully")
                }
                .addOnFailureListener {
                    onResult(false, it.localizedMessage.toString())
                }
        }
    }

    fun getUser() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            firestore.collection(USER).document(uid).get().addOnSuccessListener {
                _user.value = it.toObject(User::class.java)!!
            }
                .addOnFailureListener {
                    _user.value = User()
                }
        }
    }


    fun addCoinToFavorites(
        id: String,
        symbol: String,
        currentPrice: Double,
        percentage: Double,
        image: String,
        name: String,
        sparklineIn7d: List<Double>,
        price_change_24h: Double, onResult: (Boolean, String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        val coinMap = FirebaseCoinModel(
            symbol = symbol,
            id = id,
            current_price = currentPrice,
            price_change_percentage_24h = percentage,
            image = image,
            name = name,
            price = sparklineIn7d,
            price_change_24h = price_change_24h
        )
        if (uid != null) {
            val favRef = firestore.collection(USER)
                .document(uid)
                .collection("favorites")
                .document(id.toString())

            if (!_isFav.value) {
                favRef.set(coinMap)
                    .addOnSuccessListener {
                        onResult(true, "Coin Added Successfully")
                    }
                    .addOnFailureListener {
                        onResult(false, it.localizedMessage.toString())
                    }
            } else {
                favRef.delete()
                    .addOnSuccessListener {
                        onResult(true, "Coin Removed Successfully")
                    }
                    .addOnFailureListener {
                        onResult(false, it.localizedMessage.toString())
                    }
            }


        }
    }

    fun isCoinFavorite(coinId: String) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            return
        }

        firestore
            .collection(USER)
            .document(userId)
            .collection("favorites")
            .document(coinId)
            .get()
            .addOnSuccessListener { document ->
                _isFav.value = document.exists()

            }
            .addOnFailureListener {
                _isFav.value = false
            }
    }


    fun fetchFavoriteCoins() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            return
        }

        firestore
            .collection(USER)
            .document(userId)
            .collection("favorites")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val coins =
                    querySnapshot.documents.mapNotNull { it.toObject(FirebaseCoinModel::class.java) }
                Log.d("firestore", coins.toString())
                _favCoinList.value = coins
            }
            .addOnFailureListener {
                _favCoinList.value = emptyList()
            }
    }


    fun updateAccount(amount: String, sign: String, onResult: (Boolean, String) -> Unit) {
        val updatedAmount = amount.trim()
        Log.d("balance", updatedAmount)
        _loading.value = UpdateBalance.Loading
        val userId = auth.currentUser?.uid
        if (userId == null) {
            return
        }

        val accountRef = firestore.collection(USER).document(userId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(accountRef)
            val currentBalance = snapshot.getString("balance") ?: "0.00"
            val updateBalance = if (sign == "+") {
                currentBalance.toDouble() + updatedAmount.toDouble()
            } else {
                currentBalance.toDouble() - updatedAmount.toDouble()
            }
            transaction.update(accountRef, "balance", updateBalance.toString())
        }
            .addOnSuccessListener {
                _loading.value = UpdateBalance.Success
                onResult(true, "transaction Successful")

            }
            .addOnFailureListener {
                _loading.value = UpdateBalance.Error
                onResult(false, "transaction failed")
            }
    }

    fun buyCoin(buyModel: BuyCoinModel, quantityToBuy: Int, onResult: (Boolean, String) -> Unit) {
        _loading.value = UpdateBalance.Loading
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onResult(false, "User not authenticated")
            return
        }

        val coinRef = firestore.collection(USER).document(userId).collection(BOUGHT)
            .document(buyModel.id ?: "")
        val userRef = firestore.collection(USER).document(userId)

        firestore.runTransaction { transaction ->
            val userSnapshot = transaction.get(userRef)
            val currentBalance = userSnapshot.getString("balance")?.toDoubleOrNull() ?: 0.0

            val coinSnapshot = transaction.get(coinRef)
            val currentPrice = buyModel.current_price ?: throw Exception("Price unavailable")
            val totalCost = quantityToBuy * currentPrice

            if (totalCost > currentBalance) {
                throw Exception("Insufficient balance to complete the purchase")
            }

            val updatedBalance = currentBalance - totalCost
            val newBalance = String.format("%.2f", updatedBalance).toDouble()
            transaction.update(userRef, "balance", newBalance.toString())

            // If already bought this coin, update quantity
            if (coinSnapshot.exists()) {
                val existingCoin = coinSnapshot.toObject(BuyCoinModel::class.java)
                val existingQty = existingCoin?.coinCnt ?: 0
                val updatedCoin = existingCoin?.copy(
                    coinCnt = existingQty + quantityToBuy,
                    boughtPrice = currentPrice // optional update
                )
                updatedCoin?.let {
                    transaction.set(coinRef, it)
                }
            } else {
                // New purchase
                val newCoin = buyModel.copy(
                    coinCnt = quantityToBuy,
                    boughtPrice = currentPrice
                )
                transaction.set(coinRef, newCoin)
            }
        }.addOnSuccessListener {
            onResult(true, "Coin bought successfully")
            addTransaction(boughtModel=buyModel, trans = "Buy")

            _loading.value = UpdateBalance.Success

        }.addOnFailureListener { e ->
            onResult(false, e.localizedMessage ?: "Buy transaction failed")
            _loading.value = UpdateBalance.Error

        }
    }


    fun sellCoin(boughtModel: BuyCoinModel, onResult: (Boolean, String) -> Unit) {
        _loading.value = UpdateBalance.Loading

        val userId = auth.currentUser?.uid ?: return

        val coinRef = firestore.collection(USER).document(userId).collection(BOUGHT)
            .document(boughtModel.id.toString())
        val userRef = firestore.collection(USER).document(userId)

        firestore.runTransaction { transaction ->
            val coinSnapshot = transaction.get(coinRef)
            val userSnapshot = transaction.get(userRef)

            if (!coinSnapshot.exists()) {
                throw Exception("Coin not found in your portfolio.")
            }

            val ownedCoin = coinSnapshot.toObject(BuyCoinModel::class.java)
                ?: throw Exception("Failed to parse coin data.")

            val ownedQuantity = ownedCoin.coinCnt ?: 0
            if (boughtModel.coinCnt ?: 0 > ownedQuantity) {
                throw Exception("You don’t have enough coins.")
            }

            val currentBalance = userSnapshot.getString("balance")?.toDoubleOrNull() ?: 0.0
            val sellAmount = (boughtModel.coinCnt ?: 0) * (boughtModel.current_price ?: 0.0)
            val newBalance = currentBalance + sellAmount.toDouble()
            val updatedBalance = String.format("%.2f", newBalance).toDouble()


            // Update user balance
            transaction.update(userRef, "balance", updatedBalance.toString())

            if (boughtModel.coinCnt ?: 0 == ownedQuantity) {
                // Remove the coin entry
                transaction.delete(coinRef)
            } else {
                // Update the remaining quantity
                val updatedCoin = ownedCoin.copy(coinCnt = ownedQuantity - boughtModel.coinCnt!!)
                transaction.set(coinRef, updatedCoin)
            }
        }.addOnSuccessListener {
            _loading.value = UpdateBalance.Success
            addTransaction(boughtModel=boughtModel, trans = "Sell")
            onResult(true, "Transaction successful")
        }.addOnFailureListener { e ->
            onResult(false, e.localizedMessage ?: "Transaction failed")
            _loading.value = UpdateBalance.Error
        }

    }

    fun fetchBoughtCoinById(coinId: String, onResult: (Boolean, String) -> Unit = { _, _ -> }) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _singleBoughtCoin.value = null
            onResult(false, "User not logged in")
            return
        }

        firestore.collection(USER)
            .document(userId)
            .collection(BOUGHT)
            .document(coinId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _singleBoughtCoin.value = document.toObject(BuyCoinModel::class.java)
                    onResult(true, "Coin found")
                } else {
                    _singleBoughtCoin.value = null
                    onResult(false, "Coin not found")
                }
            }
            .addOnFailureListener {
                _singleBoughtCoin.value = null
                onResult(false, it.localizedMessage ?: "Failed to fetch coin")
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }

    fun addTransaction(boughtModel: BuyCoinModel,trans:String) {
        val userId = auth.currentUser?.uid ?: return

        val uniqueId = UUID.randomUUID().toString()
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val totalAmount = (boughtModel.coinCnt ?: 0) * (boughtModel.current_price ?: 0.0)

        val transaction = Transaction(
            id = boughtModel.id,
            name = boughtModel.name,
            symbol = boughtModel.symbol,
            transactionId = uniqueId,
            totalAmount = totalAmount,
            coinCount = boughtModel.coinCnt,
            date = currentDate,
            mode = trans
        )

        firestore.collection(USER)
            .document(userId)
            .collection(TRANSACTION)
            .document(uniqueId)
            .set(transaction)
            .addOnSuccessListener {
                Log.d("Transaction", "Transaction added successfully: $uniqueId")
            }
            .addOnFailureListener { e ->
                Log.e("Transaction", "Failed to add transaction: ${e.localizedMessage}")
            }
    }

    fun fetchTransactions() {

        val userId = auth.currentUser?.uid ?: return

        firestore.collection(USER)
            .document(userId)
            .collection(TRANSACTION)
            .get()
            .addOnSuccessListener { result ->
                val list = result.documents.mapNotNull { doc ->
                    doc.toObject(Transaction::class.java)
                }
                _transactions.value = list
                Log.d("TransactionFetch", "Transactions fetched successfully: $list")
            }
            .addOnFailureListener { e ->
                Log.e("TransactionFetch", "Failed to fetch transactions: ${e.localizedMessage}")
            }
    }

    fun fetchBoughtCoins() {
        _profileLoader.value=true
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _boughtCoins.value = emptyList()
            return
        }

        firestore.collection(USER)
            .document(userId)
            .collection(BOUGHT)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val coins = querySnapshot.documents.mapNotNull { it.toObject(BuyCoinModel::class.java) }
                _boughtCoins.value = coins
                _profileLoader.value=false

            }
            .addOnFailureListener {
                _boughtCoins.value = emptyList()
                _profileLoader.value=false
            }
    }

    fun updateProfileImage(imageUrl: String) {
        val userId = auth.currentUser?.uid
        if(userId!=null) {
            firestore.collection(USER).document(userId)
                .update("image", imageUrl)
        }
    }





}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val msg: String?) : AuthState()
}

sealed class UpdateBalance {
    object Idle : UpdateBalance()
    object Loading : UpdateBalance()
    object Success : UpdateBalance()
    object Error : UpdateBalance()
}
