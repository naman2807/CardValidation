package com.example.cardvalidation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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
    }

    private fun loadData(){
        cardNumber = binding.cardInputLayout.text.toString()
        dateMonth = binding.dateInputLayout.text.toString()
        securityCode = binding.cvvInputLayout.text.toString()
        firstName = binding.firstInputLayout.text.toString()
        lastName = binding.lastInputLayout.text.toString()
    }
}