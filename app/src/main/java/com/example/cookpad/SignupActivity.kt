package com.example.cookpad

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailLayout = findViewById<TextInputLayout>(R.id.emailRegisterInputLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordRegisterInputLayout)
        val retypePasswordLayout = findViewById<TextInputLayout>(R.id.retypePasswordRegisterInputLayout)
        val signupButton = findViewById<Button>(R.id.signupButton)

        signupButton.setOnClickListener {
            val email = emailLayout.editText?.text.toString().trim()
            val password = passwordLayout.editText?.text.toString().trim()
            val retypePassword = retypePasswordLayout.editText?.text.toString().trim()

            emailLayout.error = null
            passwordLayout.error = null
            retypePasswordLayout.error = null

            var isValid = true

            if (email.isNullOrEmpty()) {
                emailLayout.error = "Email address cannot be empty"
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLayout.error = "Please enter a valid email address"
                isValid = false
            }

            if (password.isNullOrEmpty()) {
                passwordLayout.error = "Password cannot be empty"
                isValid = false
            } else if (password.length < 8) {
                passwordLayout.error = "Password must be at least 8 characters long"
                isValid = false
            } else if (!password.any { it.isDigit() }) {
                passwordLayout.error = "Password must have at least one number"
                isValid = false
            } else if (!password.any { it.isUpperCase() }) {
                passwordLayout.error = "Password must have at least one uppercase letter"
                isValid = false
            } else if (!password.any { !it.isLetterOrDigit() }) {
                passwordLayout.error = "Password must contain at least one special character"
                isValid = false
            }

            if (retypePassword.isNullOrEmpty()) {
                retypePasswordLayout.error = "Please retype your password"
                isValid = false
            } else if (password != retypePassword) {
                retypePasswordLayout.error = "Passwords do not match"
                isValid = false
            }

            if (isValid) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        val signinLinkText = findViewById<TextView>(R.id.signinLinkText)
        signinLinkText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}