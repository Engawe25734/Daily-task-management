package com.example.dailytaskmanager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//elvis ngawe
//project
//08/16/2026

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_task)

        val edtTaskName =
            findViewById<EditText>(R.id.edtTaskName)

        val edtTaskDescription =
            findViewById<EditText>(R.id.edtTaskDescription)

        val btnSaveTask =
            findViewById<Button>(R.id.btnSaveTask)

        val btnCancelTask =
            findViewById<Button>(R.id.btnCancelTask)

        btnSaveTask.setOnClickListener {

            val taskName =
                edtTaskName.text.toString().trim()

            val description =
                edtTaskDescription.text.toString().trim()

            if (taskName.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter a task name",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val task = Task(
                name = taskName,
                description = description,
                completed = false
            )

            val resultIntent = Intent()

            resultIntent.putExtra("task", task)

            setResult(
                RESULT_OK,
                resultIntent
            )

            finish()
        }

        btnCancelTask.setOnClickListener {

            setResult(RESULT_CANCELED)

            finish()
        }
    }
}