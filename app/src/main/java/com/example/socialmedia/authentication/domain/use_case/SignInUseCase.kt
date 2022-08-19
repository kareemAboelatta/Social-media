package com.example.socialmedia.authentication.domain.use_case

import android.content.Context
import com.example.socialmedia.authentication.domain.repository.RepositoryAuth
import com.example.socialmedia.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: RepositoryAuth,
    val context: Context,
) {


    operator fun invoke(email: String, password: String):
            Flow<Resource<Boolean, String>> = flow {
        emit(Resource.Loading())
        try {

            repository.signInWithEmailAndPassword(email, password)
            emit(Resource.Success(true))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString(), false))
        }


    }


    fun isUserLoggedIn(): Boolean = repository.isUserLoggedIn()


}