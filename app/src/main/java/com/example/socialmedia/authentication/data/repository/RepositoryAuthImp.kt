package com.example.socialmedia.authentication.data.repository

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.socialmedia.authentication.domain.repository.RepositoryAuth
import com.example.socialmedia.core.models.User
import com.example.socialmedia.core.others.Authenticator
import com.example.socialmedia.core.others.Database
import com.example.socialmedia.core.others.Storage
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryAuthImp  @Inject constructor(

    private var database: Database,
    private var storager: Storage,
    private var auth: Authenticator
) : RepositoryAuth {


    override suspend fun createUser(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
    }


    override suspend fun uploadUserPictureOnFireStorage(uri: Uri) :String {
        return storager.uploadImage(uri)
    }

    override suspend fun setUserDataInfoOnDatabase(user: User) {
         database.setUserDataInfoOnDatabase(user)
    }


    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) {
        auth.signInWithEmailAndPassword(email, password)
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUserId() != null
    }

    override fun getCurrentUerId(): String =
        auth.currentUserId()!!



}