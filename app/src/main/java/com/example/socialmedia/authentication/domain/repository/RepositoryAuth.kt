package com.example.socialmedia.authentication.domain.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.socialmedia.core.models.User
import com.google.firebase.auth.AuthResult


interface RepositoryAuth {


    suspend fun createUser(email: String, password: String) : AuthResult

    suspend fun uploadUserPictureOnFireStorage(uri: Uri):String

    suspend fun setUserDataInfoOnDatabase(user: User)
     suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

     abstract fun isUserLoggedIn(): Boolean


}