package com.example.dailytaskmanager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

//Elvis ngawe
//8/16/2026
//project

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var task: Task
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_task_details)

        task =
            intent.getSerializableExtra("task") as Task

        position =
            intent.getIntExtra("position", -1)

        val txtTaskName =
            findViewById<TextView>(R.id.txtTaskName)

        val txtTaskDescription =
            findViewById<TextView>(R.id.txtTaskDescription)

        val txtTaskStatus =
            findViewById<TextView>(R.id.txtTaskStatus)

        val btnDeleteTask =
            findViewById<Button>(R.id.btnDeleteTask)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        txtTaskName.text = task.name

        txtTaskDescription.text =
            if (task.description.isEmpty()) {
                "No description"
            } else {
                task.description
            }

        txtTaskStatus.text =
            if (task.completed) {
                "Completed"
            } else {
                "Not Completed"
            }

        btnDeleteTask.setOnClickListener {

            val resultIntent = Intent()

            resultIntent.putExtra(
                "position",
                position
            )

            resultIntent.putExtra(
                "deleted",
                true
            )

            setResult(
                RESULT_OK,
                resultIntent
            )

            finish()
        }

        btnBack.setOnClickListener {

            val resultIntent = Intent()

            resultIntent.putExtra(
                "position",
                position
            )

            resultIntent.putExtra(
                "task",
                task
            )

            resultIntent.putExtra(
                "deleted",
                false
            )

            setResult(
                RESULT_OK,
                resultIntent
            )

            finish()
        }
    }
}