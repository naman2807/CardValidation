package com.example.cardvalidation

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.withStyledAttributes
import androidx.core.widget.addTextChangedListener
import com.example.cardvalidation.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cardNumber: String
    private lateinit var dateMonth: String
    private lateinit var securityCode: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var cvvRegex: Regex

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DateFormat(binding).formatDate()
        binding.submit.setOnClickListener {
            if (!checkEmptyFields()){
               if (submit()){
                   showAlert()
               }
            }else{
                Toast.makeText(this, "Some Fields have problem", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        cardNumber = binding.cardInputLayout.text.toString()
        dateMonth = binding.dateInputLayout.text.toString()
        securityCode = binding.cvvInputLayout.text.toString()
        firstName = binding.firstInputLayout.text.toString()
        lastName = binding.lastInputLayout.text.toString()
    }

    private fun submit(): Boolean{
        var submit = true
        if (!validateCardType(cardNumber)){
            binding.cardLayout.error = "Check Card Number"
            return false
        }

        if (!validateLuhn(cardNumber)){
            binding.cardLayout.error = "Check Card Number"
            submit = false
        }
        if (!isCharacters(firstName)) {
            binding.firstLayout.error = "First name error"
            submit = false
        }
        if (!isCharacters(lastName)){
            binding.lastLayout.error = "Last Name error"
            submit = false
        }

        if (submit){
            if (!validateCvv(cvvNumber = securityCode)){
                binding.cvvLayout.error = "Security Code error"
                submit = false
            }

            if (!ExpiryDate().validateExpiryDate(getExpiryYear(), getExpiryMonth())){
                binding.dateLayout.error = "Expiry Date error"
                submit = false
            }
        }

        return submit
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
            clearData()
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
        for (i in numbers.indices) {
            if (i % 2 == 1) {
                sum += numbers[i]
            }
        }
        return sum
    }

    private fun getEvenSum(numbers: IntArray): Int {
        var sum = 0
        for (i in numbers.indices) {
            if (i % 2 == 0) {
                val digit = numbers[i] * 2
                if (digit > 9) {
                    val digits = digit.toString().map { it.toString().toInt() }.toIntArray()
                    for (j in digits.indices) {
                        sum += digits[j]
                    }
                } else {
                    sum += digit
                }
            }
        }
        return sum
    }

    private fun validateCardType(cardNumber: String): Boolean{
        val visa = "^4[0-9]{12}(?:[0-9]{3})?$".toRegex()
        val masterCard = "^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))\$".toRegex()
        val americanExpress = "^3[47][0-9]{13}\$".toRegex()
        val discover = "^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})\$".toRegex()
        if (validateVisa(cardNumber, visa)) cvvRegex = "\\d{3}".toRegex()
        if (validateMasterCard(cardNumber, masterCard)) cvvRegex = "\\d{3}".toRegex()
        if (validateAmericanExpress(cardNumber, americanExpress)) cvvRegex = "\\d{4}".toRegex()
        if (validateDiscover(cardNumber, discover)) cvvRegex = "\\d{3}".toRegex()
        return validateVisa(cardNumber, visa) || validateMasterCard(cardNumber, masterCard) || validateAmericanExpress(cardNumber, americanExpress)
                || validateDiscover(cardNumber, discover)
    }

    private fun validateVisa(cardNumber: String, regex: Regex): Boolean{
        return cardNumber.matches(regex)
    }

    private fun validateMasterCard(cardNumber: String, regex: Regex): Boolean{
        return cardNumber.matches(regex)
    }

    private fun validateAmericanExpress(cardNumber: String, regex: Regex): Boolean{
        return cardNumber.matches(regex)
    }

    private fun validateDiscover(cardNumber: String, regex: Regex): Boolean{
        return cardNumber.matches(regex)
    }

    private fun validateCvv(cvvNumber: String): Boolean{
        return cvvNumber.matches(cvvRegex)
    }

    private fun clearData(){
        with(binding) {
            cardInputLayout.setText("")
            dateInputLayout.setText("")
            cvvInputLayout.setText("")
            firstInputLayout.setText("")
            lastInputLayout.setText("")
            cardLayout.error = null
            dateLayout.error = null
            cvvLayout.error = null
            firstLayout.error = null
            lastLayout.error = null
        }
    }

    private fun getExpiryMonth():String = dateMonth.split("/")[0]
    private fun getExpiryYear(): String = dateMonth.split("/")[1]
}