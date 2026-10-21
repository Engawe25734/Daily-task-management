package com.example.dailytaskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val currentPassword =
            findViewById<EditText>(R.id.edtCurrentPassword)

        val newPassword =
            findViewById<EditText>(R.id.edtNewPassword)

        val confirmPassword =
            findViewById<EditText>(R.id.edtConfirmNewPassword)

        val saveButton =
            findViewById<Button>(R.id.btnSavePassword)

        val cancelButton =
            findViewById<Button>(R.id.btnCancelPassword)

        saveButton.setOnClickListener {
            val current = currentPassword.text.toString()
            val new = newPassword.text.toString()
            val confirm = confirmPassword.text.toString()

            if (current.isBlank() || new.isBlank() || confirm.isBlank()) {
                showMessage("Complete all fields")
                return@setOnClickListener
            }

            val strongPassword =
                new.length >= 8 &&
                        new.any { it.isUpperCase() } &&
                        new.any { it.isLowerCase() } &&
                        new.any { it.isDigit() } &&
                        new.any { !it.isLetterOrDigit() }

            if (!strongPassword) {
                showMessage(
                    "New password needs 8+ characters, uppercase and lowercase letters, a number, and a symbol"
                )
                return@setOnClickListener
            }

            if (new != confirm) {
                showMessage("New passwords do not match")
                return@setOnClickListener
            }

            if (current == new) {
                showMessage("Choose a different new password")
                return@setOnClickListener
            }

            val changed = AuthStorage.changePassword(
                this,
                current,
                new
            )

            if (changed) {
                showMessage("Password changed successfully")
                finish()
            } else {
                showMessage("Current password is incorrect")
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}
