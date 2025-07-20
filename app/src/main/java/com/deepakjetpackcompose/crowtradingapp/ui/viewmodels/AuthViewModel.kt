package com.deepakjetpackcompose.crowtradingapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.deepakjetpackcompose.crowtradingapp.data.model.CryptoModelItem
import com.deepakjetpackcompose.crowtradingapp.data.model.SparklineIn7d
import com.deepakjetpackcompose.crowtradingapp.domain.constant.USER
import com.deepakjetpackcompose.crowtradingapp.domain.model.FirebaseCoinModel
import com.deepakjetpackcompose.crowtradingapp.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val auth: FirebaseAuth,private val firestore: FirebaseFirestore): ViewModel() {
    private val _authState= MutableStateFlow<AuthState>(AuthState.Idle)
    val authState= _authState.asStateFlow()

    private val _isFav= MutableStateFlow<Boolean>(false)
    val isFav= _isFav.asStateFlow()

    private val _favCoinList=MutableStateFlow<List<FirebaseCoinModel>>(emptyList())
    val favCoinList= _favCoinList.asStateFlow()

    private  val _user=MutableStateFlow(User())
    val user=_user.asStateFlow()


    fun login(email:String,password:String,onResult:(Boolean,String)->Unit){
        _authState.value= AuthState.Loading
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    onResult(true,"Login Successfully")
                    _authState.value= AuthState.Success
                }
                .addOnFailureListener {
                    onResult(false,it.localizedMessage.toString())
                    _authState.value= AuthState.Error(it.localizedMessage)
                }
        }
        catch (e: Exception){
            onResult(false,e.localizedMessage.toString())
            _authState.value= AuthState.Error(e.localizedMessage)
        }
    }

    fun register(email:String,password:String,name:String,onResult:(Boolean,String)->Unit){
        _authState.value= AuthState.Loading
        try {
            auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {

                    createUser(name,email){success,msg->
                        if(!success) onResult(false,msg)
                        else{
                            onResult(true,"Registered Successfully")
                            _authState.value= AuthState.Success
                        }
                    }

                }
                .addOnFailureListener {
                    onResult(false,it.localizedMessage.toString())
                    _authState.value= AuthState.Error(it.localizedMessage)
                }
        }
        catch (e: Exception){
            onResult(false,"Registration Failed")
            _authState.value= AuthState.Error(e.localizedMessage)
        }
    }

    fun createUser(name:String,email:String,onResult:(Boolean,String)->Unit){
        val uid=auth.currentUser?.uid
        if(uid!=null) {
            firestore.collection(USER).document(uid).set(User(name=name,email=email, balance ="0.00" ))
                .addOnSuccessListener {
                    onResult(true,"Registered Successfully")
                }
                .addOnFailureListener {
                    onResult(false,it.localizedMessage.toString())
                }
        }
    }

    fun getUser(){
        val uid=auth.currentUser?.uid
        if(uid!=null) {
            firestore.collection(USER).document(uid).get().addOnSuccessListener {
                  _user.value=it.toObject(User::class.java)!!
                }
                .addOnFailureListener {
                    _user.value=User()
                }
        }
    }


    fun addCoinToFavorites(  id: String,
                             symbol: String,
                             currentPrice: Double,
                             percentage: Double,
                             image:String,
                             name:String,
                             sparklineIn7d: List<Double>,
                             price_change_24h: Double,onResult:(Boolean,String)->Unit) {
        val uid=auth.currentUser?.uid
        val coinMap= FirebaseCoinModel(
            symbol=symbol,
            id=id,
            current_price=currentPrice,
            price_change_percentage_24h=percentage,
            image=image,
            name=name,
            price = sparklineIn7d,
            price_change_24h = price_change_24h
        )
        if(uid!=null) {
            val favRef=firestore.collection(USER)
                .document(uid)
                .collection("favorites")
                .document(id.toString())

            if(!_isFav.value){
                favRef.set(coinMap)
                    .addOnSuccessListener {
                        onResult(true,"Coin Added Successfully")
                    }
                    .addOnFailureListener {
                        onResult(false,it.localizedMessage.toString())
                    }
            }
            else{
                favRef.delete()
                    .addOnSuccessListener {
                        onResult(true,"Coin Removed Successfully")
                    }
                    .addOnFailureListener {
                        onResult(false,it.localizedMessage.toString())
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
                _isFav.value=document.exists()

            }
            .addOnFailureListener {
               _isFav.value=false
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
                val coins = querySnapshot.documents.mapNotNull { it.toObject(FirebaseCoinModel::class.java) }
                Log.d("firestore",coins.toString())
                _favCoinList.value=coins
            }
            .addOnFailureListener {
               _favCoinList.value=emptyList()
            }
    }





}

sealed class AuthState{
    object Idle: AuthState()
    object Loading: AuthState()
    object Success: AuthState()
    data class Error(val msg:String?): AuthState()
}
