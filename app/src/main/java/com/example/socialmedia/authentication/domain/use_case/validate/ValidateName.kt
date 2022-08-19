package com.example.socialmedia.authentication.domain.use_case.validate

class ValidateName {

    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't be blank"
            )
        }
        if(name.length > 15 || name.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't be longer than 15 characters or shorter than 3 characters"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}