package com.example.cookpad

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailLayout = findViewById<TextInputLayout>(R.id.emailForgotPasswordInputLayout)
        val resetPasswordButton = findViewById<Button>(R.id.sendResetLinkButton)

        resetPasswordButton.setOnClickListener {
            val email = emailLayout.editText?.text.toString().trim()

            emailLayout.error = null

            var isValid = true

            if (email.isNullOrEmpty()) {
                emailLayout.error = "Email address cannot be empty"
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLayout.error = "Please enter a valid email address"
                isValid = false
            }

            if (isValid) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}