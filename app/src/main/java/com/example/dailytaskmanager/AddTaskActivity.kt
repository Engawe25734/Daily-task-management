package com.example.dailytaskmanager

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale

class AddTaskActivity : AppCompatActivity() {

    private var selectedDueDate = ""
    private var existingTask: Task? = null

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

        existingTask = intent.getSerializableExtra("task") as? Task
        existingTask?.let { task ->
            edtTaskName.setText(task.name)
            edtTaskDescription.setText(task.description)
            selectedDueDate = task.dueDate
            btnSaveTask.text = "Save Changes"
            findViewById<TextView>(R.id.txtAddTaskTitle).text = "Edit Task"
            btnSelectDueDate.text =
                if (selectedDueDate.isBlank()) "Select Due Date"
                else "Due Date: $selectedDueDate"
        }

        btnSelectDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            if (selectedDueDate.isNotBlank()) {
                val parts = selectedDueDate.split("-")
                if (parts.size == 3) {
                    calendar.set(
                        parts[0].toIntOrNull() ?: calendar.get(Calendar.YEAR),
                        (parts[1].toIntOrNull() ?: 1) - 1,
                        parts[2].toIntOrNull() ?: calendar.get(Calendar.DAY_OF_MONTH)
                    )
                }
            }

            DatePickerDialog(
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
            ).show()
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
                completed = existingTask?.completed ?: false,
                dueDate = selectedDueDate
            )

            setResult(RESULT_OK, Intent().putExtra("task", task))
            finish()
        }

        btnCancelTask.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
