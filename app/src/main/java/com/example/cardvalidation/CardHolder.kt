package com.example.cardvalidation

class CardHolder {
    fun isCharacters(name: String): Boolean = name.matches("^[a-zA-Z]*$".toRegex())
}