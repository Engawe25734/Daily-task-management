package com.example.dailytaskmanager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//Elvis Ngawe
//project
//08/16/2026

class MainActivity : AppCompatActivity() {

    private val tasks = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter

    private val addTaskLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                val task =
                    result.data?.getSerializableExtra("task") as? Task

                if (task != null) {
                    tasks.add(task)
                    taskAdapter.notifyItemInserted(tasks.size - 1)
                }
            }
        }

    private val detailsLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                val position =
                    result.data?.getIntExtra("position", -1) ?: -1

                val updatedTask =
                    result.data?.getSerializableExtra("task") as? Task

                val deleted =
                    result.data?.getBooleanExtra("deleted", false)
                        ?: false

                if (position >= 0 && position < tasks.size) {

                    if (deleted) {

                        tasks.removeAt(position)
                        taskAdapter.notifyItemRemoved(position)

                    } else if (updatedTask != null) {

                        tasks[position] = updatedTask
                        taskAdapter.notifyItemChanged(position)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerTasks =
            findViewById<RecyclerView>(R.id.recyclerTasks)

        val btnAddTask =
            findViewById<Button>(R.id.btnAddTask)

        taskAdapter = TaskAdapter(
            tasks,

            onTaskClick = { position ->

                val intent =
                    Intent(this, TaskDetailsActivity::class.java)

                intent.putExtra("task", tasks[position])
                intent.putExtra("position", position)

                detailsLauncher.launch(intent)
            },

            onTaskChecked = { _, _ ->
                // Completion is already updated in the adapter.
            }
        )

        recyclerTasks.layoutManager =
            LinearLayoutManager(this)

        recyclerTasks.adapter = taskAdapter

        btnAddTask.setOnClickListener {

            val intent =
                Intent(this, AddTaskActivity::class.java)

            addTaskLauncher.launch(intent)
        }
    }
}