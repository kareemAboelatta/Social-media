package com.example.socialmedia.authentication.domain.use_case.validate

import android.util.Patterns

class ValidateBio {

    fun execute(bio: String): ValidationResult {

        if(bio.isBlank()) {

            return ValidationResult(
                successful = false,
                errorMessage = "The bio can't be blank"
            )
        }

        if(bio.length < 20) {
            return ValidationResult(
                successful = false,
                errorMessage = "The bio must be longer than 20 characters"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}