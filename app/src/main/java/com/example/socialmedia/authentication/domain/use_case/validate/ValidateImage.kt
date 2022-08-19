package com.example.socialmedia.authentication.domain.use_case.validate

import android.net.Uri

class ValidateImage  {

    fun execute(uri: Uri): ValidationResult {
        if(uri == null || Uri.EMPTY == uri) {
            return ValidationResult(
                successful = false,
                errorMessage = "You must Select a Profile Image"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}