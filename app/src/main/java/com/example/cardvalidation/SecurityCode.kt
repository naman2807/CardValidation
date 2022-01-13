package com.example.cardvalidation

class SecurityCode {
    fun validateSecurityCode(cvvNumber: String, cvvRegex: Regex): Boolean = cvvNumber.matches(cvvRegex)
}