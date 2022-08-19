 package com.example.socialmedia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.socialmedia.authentication.domain.repository.RepositoryAuth
import com.example.socialmedia.authentication.domain.use_case.SignInUseCase
import com.example.socialmedia.authentication.presentation.ViewModelAuth
import com.example.socialmedia.core.others.Authenticator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

 @AndroidEntryPoint
class TestActivity : AppCompatActivity() {





     @Inject
    lateinit var auth: Authenticator

    @Inject
    lateinit var repositoryAuth: RepositoryAuth


     @Inject
     lateinit var signInUseCase: SignInUseCase

     private val viewModelAuth by viewModels<ViewModelAuth>()


     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_test)


         try {
             runBlocking {
                 auth.signInWithEmailAndPassword("kareem", "kareem")
             }

         } catch (e: Exception) {
             Log.e("TestActivity", e.message.toString())
         }


     }


 }
