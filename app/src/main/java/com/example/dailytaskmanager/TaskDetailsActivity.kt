package com.example.dailytaskmanager

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var task: Task
    private var position: Int = -1

    private val editTaskLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedTask =
                    result.data?.getSerializableExtra("task") as? Task
                if (updatedTask != null) {
                    task = updatedTask
                    displayTask()
                }
            }
        }

    private lateinit var txtTaskName: TextView
    private lateinit var txtTaskDescription: TextView
    private lateinit var txtTaskStatus: TextView
    private lateinit var txtTaskDueDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        task = intent.getSerializableExtra("task") as? Task
            ?: run {
                finish()
                return
            }
        position = intent.getIntExtra("position", -1)

        txtTaskName = findViewById(R.id.txtTaskName)
        txtTaskDescription = findViewById(R.id.txtTaskDescription)
        txtTaskStatus = findViewById(R.id.txtTaskStatus)
        txtTaskDueDate = findViewById(R.id.txtTaskDueDate)

        val btnEditTask = findViewById<Button>(R.id.btnEditTask)
        val btnDeleteTask = findViewById<Button>(R.id.btnDeleteTask)
        val btnBack = findViewById<Button>(R.id.btnBack)

        displayTask()

        btnEditTask.setOnClickListener {
            val editIntent = Intent(this, AddTaskActivity::class.java)
            editIntent.putExtra("task", task)
            editTaskLauncher.launch(editIntent)
        }

        btnDeleteTask.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete task?")
                .setMessage("Are you sure you want to delete \"${task.name}\"?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete") { _, _ ->
                    val resultIntent = Intent().apply {
                        putExtra("position", position)
                        putExtra("deleted", true)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
                .show()
        }

        btnBack.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("position", position)
                putExtra("task", task)
                putExtra("deleted", false)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun displayTask() {
        txtTaskName.text = task.name
        txtTaskDescription.text =
            if (task.description.isBlank()) "No description"
            else task.description
        txtTaskStatus.text =
            if (task.completed) "Completed" else "Not Completed"
        txtTaskDueDate.text =
            if (task.dueDate.isBlank()) "No due date"
            else task.dueDate
    }
}
