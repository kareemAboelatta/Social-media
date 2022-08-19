package com.example.socialmedia.authentication.presentation

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow

data class RegistrationFormState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val bio: String = "",
    val bioError: String? = null,
    val image: Uri = Uri.EMPTY,
    val imageError: String? = null,
    val progress: Boolean = false
)
