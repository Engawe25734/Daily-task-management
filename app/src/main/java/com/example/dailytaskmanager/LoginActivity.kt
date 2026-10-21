
package com.example.dailytaskmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.edtLoginEmail)
        val password = findViewById<EditText>(R.id.edtLoginPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val registerLink = findViewById<TextView>(R.id.txtGoToRegister)

        loginButton.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString()

            if (userEmail.isBlank() || userPassword.isBlank()) {
                showMessage("Enter your email and password")
                return@setOnClickListener
            }

            if (AuthStorage.verify(this, userEmail, userPassword)) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                showMessage("Invalid email or password")
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
