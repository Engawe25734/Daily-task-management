
package com.example.dailytaskmanager

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale

class AddTaskActivity : AppCompatActivity() {

    private var selectedDueDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val edtTaskName = findViewById<EditText>(R.id.edtTaskName)
        val edtTaskDescription =
            findViewById<EditText>(R.id.edtTaskDescription)
        val btnSelectDueDate =
            findViewById<Button>(R.id.btnSelectDueDate)
        val btnSaveTask = findViewById<Button>(R.id.btnSaveTask)
        val btnCancelTask = findViewById<Button>(R.id.btnCancelTask)

        btnSelectDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, day ->
                    selectedDueDate = String.format(
                        Locale.US,
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )

                    btnSelectDueDate.text = "Due Date: $selectedDueDate"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        btnSaveTask.setOnClickListener {
            val name = edtTaskName.text.toString().trim()
            val description = edtTaskDescription.text.toString().trim()

            if (name.isEmpty()) {
                edtTaskName.error = "Please enter a task name"
                edtTaskName.requestFocus()
                return@setOnClickListener
            }

            val task = Task(
                name = name,
                description = description,
                completed = false,
                dueDate = selectedDueDate
            )

            val resultIntent = Intent()
            resultIntent.putExtra("task", task)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        btnCancelTask.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}

