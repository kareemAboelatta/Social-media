package com.example.socialmedia.core.others

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface Authenticator {

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    fun currentUserId() : String?

    suspend fun createUserWithEmailAndPassword(email: String, password: String)

}

class FirebaseAuthenticator @Inject constructor(
    private var auth: FirebaseAuth,

    ) : Authenticator{

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) {
          auth.signInWithEmailAndPassword(email, password).await()
    }

    override fun currentUserId(): String? {
        return auth.currentUser?.uid

    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ) {
          auth.createUserWithEmailAndPassword(email, password).await()
    }


}


class CustomApiAuthenticator : Authenticator{


    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun currentUserId(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ){
        TODO("Not yet implemented")
    }

}