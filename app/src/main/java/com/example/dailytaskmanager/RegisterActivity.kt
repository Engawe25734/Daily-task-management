

package com.example.dailytaskmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val email = findViewById<EditText>(R.id.edtRegisterEmail)
        val password = findViewById<EditText>(R.id.edtRegisterPassword)
        val confirmPassword =
            findViewById<EditText>(R.id.edtConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)

        registerButton.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString()
            val confirmation = confirmPassword.text.toString()

            if (userEmail.isBlank() ||
                userPassword.isBlank() ||
                confirmation.isBlank()
            ) {
                showMessage("Complete all fields")
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS
                    .matcher(userEmail).matches()
            ) {
                showMessage("Enter a valid email address")
                return@setOnClickListener
            }

            val strongPassword =
                userPassword.length >= 8 &&
                        userPassword.any { it.isUpperCase() } &&
                        userPassword.any { it.isLowerCase() } &&
                        userPassword.any { it.isDigit() } &&
                        userPassword.any { !it.isLetterOrDigit() }

            if (!strongPassword) {
                showMessage(
                    "Password needs 8+ characters, uppercase and " +
                            "lowercase letters, a number, and a symbol"
                )
                return@setOnClickListener
            }

            if (userPassword != confirmation) {
                showMessage("Passwords do not match")
                return@setOnClickListener
            }

            if (AuthStorage.hasAccount(this)) {
                showMessage("An account already exists on this device")
                return@setOnClickListener
            }

            val saved = AuthStorage.register(
                this,
                userEmail,
                userPassword
            )

            if (saved) {
                showMessage("Account created. Please log in.")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                showMessage("Could not create account. Try again.")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
