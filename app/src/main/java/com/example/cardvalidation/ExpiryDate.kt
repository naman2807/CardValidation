package com.example.cardvalidation

import java.util.*

class ExpiryDate() {
    val calendar by lazy { Calendar.getInstance() }

    private fun getCurrentYear():String{
        var string: String = ""
        val numbers = calendar.get(Calendar.YEAR).toString().map{ it.toString().toInt() }.toIntArray()
        for (i in numbers.indices){
            if (i > 1){
                string += numbers[i].toString()
            }
        }
        return string
    }

    private fun getCurrentMonth():String = (calendar.get(Calendar.MONTH) + 1).toString()

    fun validateExpiryDate(year: String, month: String): Boolean = getCurrentYear().toInt() <= year.toInt() && getCurrentMonth().toInt() <= month.toInt()
}