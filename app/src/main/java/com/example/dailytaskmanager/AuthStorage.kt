package com.example.dailytaskmanager

import android.content.Context
import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object AuthStorage {

    private const val PREFS = "DailyTaskManagerAuth"
    private const val ITERATIONS = 150000
    private const val KEY_LENGTH = 256

    private fun hashPassword(
        password: String,
        salt: ByteArray
    ): ByteArray {
        val spec = PBEKeySpec(
            password.toCharArray(),
            salt,
            ITERATIONS,
            KEY_LENGTH
        )

        return try {
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                .generateSecret(spec).encoded
        } finally {
            spec.clearPassword()
        }
    }

    fun hasAccount(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )

        return prefs.contains("email") &&
                prefs.contains("passwordHash") &&
                prefs.contains("salt")
    }

    fun register(
        context: Context,
        email: String,
        password: String
    ): Boolean {
        val prefs = context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )

        if (hasAccount(context)) return false

        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)

        val hash = hashPassword(password, salt)

        return prefs.edit()
            .putString("email", email.trim())
            .putString(
                "salt",
                Base64.encodeToString(salt, Base64.NO_WRAP)
            )
            .putString(
                "passwordHash",
                Base64.encodeToString(hash, Base64.NO_WRAP)
            )
            .remove("password")
            .commit()
    }

    fun verify(
        context: Context,
        email: String,
        password: String
    ): Boolean {
        val prefs = context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )

        val savedEmail = prefs.getString("email", null)
            ?: return false

        val savedSalt = prefs.getString("salt", null)
            ?: return false

        val savedHash = prefs.getString("passwordHash", null)
            ?: return false

        if (!email.trim().equals(savedEmail, ignoreCase = true)) {
            return false
        }

        return try {
            val salt = Base64.decode(
                savedSalt,
                Base64.NO_WRAP
            )

            val expectedHash = Base64.decode(
                savedHash,
                Base64.NO_WRAP
            )

            val actualHash = hashPassword(password, salt)

            MessageDigest.isEqual(expectedHash, actualHash)
        } catch (_: IllegalArgumentException) {
            false
        }
    }

    // Change the password after verifying the current password.
    fun changePassword(
        context: Context,
        currentPassword: String,
        newPassword: String
    ): Boolean {
        val prefs = context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )

        val savedEmail = prefs.getString("email", null)
            ?: return false

        if (!verify(context, savedEmail, currentPassword)) {
            return false
        }

        val newSalt = ByteArray(16)
        SecureRandom().nextBytes(newSalt)

        val newHash = hashPassword(newPassword, newSalt)

        return prefs.edit()
            .putString(
                "salt",
                Base64.encodeToString(newSalt, Base64.NO_WRAP)
            )
            .putString(
                "passwordHash",
                Base64.encodeToString(newHash, Base64.NO_WRAP)
            )
            .remove("password")
            .commit()
    }
}
