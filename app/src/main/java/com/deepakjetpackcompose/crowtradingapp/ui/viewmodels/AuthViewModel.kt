package com.deepakjetpackcompose.crowtradingapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.deepakjetpackcompose.crowtradingapp.domain.constant.USER
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
            firestore.collection(USER).document(uid).set(User(name=name,email=email))
                .addOnSuccessListener {
                    onResult(true,"Registered Successfully")
                }
                .addOnFailureListener {
                    onResult(false,it.localizedMessage.toString())
                }
        }
    }



}

sealed class AuthState{
    object Idle: AuthState()
    object Loading: AuthState()
    object Success: AuthState()
    data class Error(val msg:String?): AuthState()
}