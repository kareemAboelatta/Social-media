package com.example.socialmedia.authentication.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.authentication.domain.use_case.CreateUserUseCase
import com.example.socialmedia.authentication.domain.use_case.SignInUseCase
import com.example.socialmedia.authentication.domain.use_case.validate.*
import com.example.socialmedia.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ViewModelAuth @Inject constructor(
    val signInUseCase: SignInUseCase,
    val createUserUseCase: CreateUserUseCase
) : ViewModel()  {

    private val validateEmail: ValidateEmail = ValidateEmail()
    private val validatePassword: ValidatePassword = ValidatePassword()
    private val validateBio: ValidateBio = ValidateBio()
    private val validateName: ValidateName = ValidateName()
    private val validateImage: ValidateImage = ValidateImage()


    var _validateState= MutableStateFlow(RegistrationFormState())
    val validateState: StateFlow<RegistrationFormState>  = _validateState

    private var _signInState= MutableStateFlow<Resource<Boolean,String>>(Resource.Empty())
    val signInState: StateFlow<Resource<Boolean,String>>  = _signInState

    private var _createUserState= MutableStateFlow<Resource<Boolean,String>>(Resource.Empty())
    val createUserState: StateFlow<Resource<Boolean,String>>  = _createUserState




    fun signIn(email: String, password: String) {
        signInUseCase(email, password).onEach {
            when (it) {
                is Resource.Loading -> _signInState.value = Resource.Loading(null)
                is Resource.Success -> _signInState.value = Resource.Success(it.data!!)
                is Resource.Error -> _signInState.value = Resource.Error(it.message)
            }

        }.launchIn(viewModelScope)



    }

    private fun createUser(email: String, password: String, bio: String, name: String, image: Uri) {
        createUserUseCase(
           email =  email,
            password = password,
            bio = bio,
            name = name,
            image = image
        ).onEach {
            when (it) {
                is Resource.Loading -> _createUserState.value = Resource.Loading(null)
                is Resource.Success -> _createUserState.value = Resource.Success(it.data!!)
                is Resource.Error -> _createUserState.value = Resource.Error(it.message)
            }
        }.launchIn(viewModelScope)
    }


    fun submitData(name :String ,email: String, password: String, bio: String, image: Uri) {
        val emailResult = validateEmail.execute(email)
        val passwordResult = validatePassword.execute(password)
        val bioResult = validateBio.execute(bio)
        val nameResult = validateName.execute(name)
        val imageResult = validateImage.execute(image)

        val hasError = listOf(
            emailResult,
            passwordResult,
            bioResult,
            nameResult,
            imageResult
        ).any { !it.successful }

        if (hasError) {
            _validateState.value = _validateState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                bioError = bioResult.errorMessage,
                imageError = imageResult.errorMessage,
                nameError = nameResult.errorMessage
            )
            return
        }else{
            _validateState.value = _validateState.value.copy(
                emailError = null,
                passwordError = null,
                bioError = null,
                imageError = null,
                nameError = null,
                progress = true
            )

            createUser(email, password, bio, name, image)


        }




    }


   fun checkIfUserIsLoggedIn(): Boolean {
       return signInUseCase.isUserLoggedIn()
   }

}