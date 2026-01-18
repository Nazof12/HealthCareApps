package com.App.healtcare.ui.feature.questionSettings.presentation._main.domain

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.healtcare.data.domain.model.UserData
import com.App.healtcare.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val repo: AuthRepository
) : ViewModel(){
    private val _userState = MutableStateFlow<UserData?>(null)
    val userState = _userState.asStateFlow()

    init {
        checkUser()
    }
    fun checkUser(){
        val firebaseUser = repo.getSignInUser()
        _userState.value = firebaseUser?.let {
            UserData(it.uid, it.displayName, it.photoUrl?.toString(), it.email)
        }
    }
    fun login(idtoken: String){
        viewModelScope.launch {
            val result = repo.signInWithGoogle(idtoken)
            if(result.isSuccess){
                checkUser()
            }
        }
    }
    fun logout(){
        repo.signOut()
        _userState.value = null
    }
}

