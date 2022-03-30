package com.narcissus.marketplace.domain.auth

sealed interface PasswordRequirement {
    fun validate(password: String): Boolean

    object NotBlank : PasswordRequirement {
        override fun validate(password: String): Boolean =
            password.isNotBlank()
    }

    object NotTooShort : PasswordRequirement {
        private const val MIN_LENGTH = 8

        override fun validate(password: String): Boolean =
            password.length > MIN_LENGTH
    }

    object HasNumber : PasswordRequirement {
        override fun validate(password: String): Boolean =
            password.any(Char::isDigit)
    }

    object HasUppercaseLetter : PasswordRequirement {
        override fun validate(password: String): Boolean =
            password.any(Char::isUpperCase)
    }

    object HasLowercaseLetter : PasswordRequirement {
        override fun validate(password: String): Boolean =
            password.any(Char::isLowerCase)
    }

    companion object {
        fun findFailedRequirements(password: String): List<PasswordRequirement> {
            val requirements = listOf(NotBlank, NotTooShort, HasNumber, HasUppercaseLetter, HasLowercaseLetter)
            return requirements
                .filterNot { req ->
                    req.validate(password)
                }
        }
    }
}
