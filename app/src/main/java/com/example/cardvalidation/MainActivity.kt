package com.example.cardvalidation

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cardvalidation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cardNumber: String
    private lateinit var dateMonth: String
    private lateinit var securityCode: String
    private lateinit var firstName: String
    private lateinit var lastName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submit.setOnClickListener {
            checkEmptyFields()
            showAlert()
        }
    }

    private fun loadData() {
        cardNumber = binding.cardInputLayout.text.toString()
        dateMonth = binding.dateInputLayout.text.toString()
        securityCode = binding.cvvInputLayout.text.toString()
        firstName = binding.firstInputLayout.text.toString()
        lastName = binding.lastInputLayout.text.toString()
    }

    private fun checkEmptyFields(): Boolean {
        loadData()
        var empty = false
        if (cardNumber.trim().equals("")) {
            empty = true
            binding.cardLayout.error = getString(R.string.card_number)
        }
        if (dateMonth.trim().equals("")) {
            empty = true
            binding.dateLayout.error = "Check Date Month"
        }
        if (securityCode.trim().equals("")) {
            empty = true
            binding.cvvLayout.error = "Check Security Code"
        }
        if (firstName.trim().equals("")) {
            empty = true
            binding.firstLayout.error = "Check First Name"
        }

        if (lastName.trim().equals("")) {
            empty = true
            binding.lastLayout.error = "Check Last Name"
        }

        return empty
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Payment Successful")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("OK") { dialogInterface, which ->
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun isCharacters(name: String): Boolean {
        return name.matches("^[a-zA-Z]*$".toRegex())
    }

    private fun validateLuhn(number: String): Boolean {
        val numbers = number.map { it.toString().toInt() }.toIntArray()
        val sum = getEvenSum(numbers) + getOddSum(numbers)
        return sum % 10 == 0
    }

    private fun getOddSum(numbers: IntArray): Int {
        var sum = 0
        for (i in 0..numbers.size - 1) {
            if (i % 2 == 1) {
                sum += numbers[i]
            }
        }
        return sum
    }

    private fun getEvenSum(numbers: IntArray): Int {
        var sum = 0
        for (i in 0..numbers.size - 1) {
            if (i % 2 == 0) {
                val digit = numbers[i] * 2
                if (digit > 9) {
                    val digits = digit.toString().map { it.toString().toInt() }.toIntArray()
                    for (j in 0..digits.size - 1) {
                        sum += digits[j]
                    }
                } else {
                    sum += digit
                }
            }
        }
        return sum
    }
}