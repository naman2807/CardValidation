package com.example.cardvalidation

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardvalidation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cardNumber: String
    private lateinit var dateMonth: String
    private lateinit var securityCode: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private val number: CardNumber = CardNumber()

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
        if (!number.validateCard(cardNumber)){
            binding.cardLayout.error = "Check Card Number"
            return false
        }
        if (!CardHolder().isCharacters(firstName)) {
            binding.firstLayout.error = "First name error"
            submit = false
        }
        if (!CardHolder().isCharacters(lastName)){
            binding.lastLayout.error = "Last Name error"
            submit = false
        }

        if (!SecurityCode().validateSecurityCode(securityCode, number.getRegex())){
            binding.cvvLayout.error = "Security Code error"
            submit = false
        }

        if (!ExpiryDate().validateExpiryDate(getExpiryYear(), getExpiryMonth())){
            binding.dateLayout.error = "Expiry Date error"
            submit = false
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