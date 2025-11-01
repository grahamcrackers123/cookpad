package com.example.cookpad

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val forgotPasswordLink = findViewById<TextView>(R.id.forgotPasswordLink)
        forgotPasswordLink.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        val emailLayout = findViewById<TextInputLayout>(R.id.emailLoginInputLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLoginInputLayout)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailLayout.editText?.text.toString().trim()
            val password = passwordLayout.editText?.text.toString().trim()

            emailLayout.error = null
            passwordLayout.error = null

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
            }

            if (isValid) {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
        }

        val signupLink = findViewById<TextView>(R.id.signupLink)
        signupLink.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

}