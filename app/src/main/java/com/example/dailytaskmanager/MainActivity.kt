package com.example.dailytaskmanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val tasks = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            refreshReminders()
        }

    private val addTaskLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val task =
                    result.data?.getSerializableExtra("task") as? Task

                if (task != null) {
                    tasks.add(task)
                    TaskStorage.saveTasks(this, tasks)
                    refreshTasks()
                    refreshReminders()
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
                    result.data?.getBooleanExtra("deleted", false) ?: false

                if (position in tasks.indices) {
                    if (deleted) {
                        tasks.removeAt(position)
                    } else if (updatedTask != null) {
                        tasks[position] = updatedTask
                    }

                    TaskStorage.saveTasks(this, tasks)
                    refreshTasks()
                    refreshReminders()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasks.addAll(TaskStorage.loadTasks(this))

        val recyclerTasks =
            findViewById<RecyclerView>(R.id.recyclerTasks)

        val edtSearchTasks =
            findViewById<EditText>(R.id.edtSearchTasks)

        val btnAddTask =
            findViewById<Button>(R.id.btnAddTask)

        val btnChangePassword =
            findViewById<Button>(R.id.btnChangePassword)

        val btnLogout =
            findViewById<Button>(R.id.btnLogout)

        taskAdapter = TaskAdapter(
            tasks,
            onTaskClick = { position ->
                if (position in tasks.indices) {
                    val intent = Intent(
                        this,
                        TaskDetailsActivity::class.java
                    )

                    intent.putExtra("task", tasks[position])
                    intent.putExtra("position", position)

                    detailsLauncher.launch(intent)
                }
            },
            onTaskChecked = { position, checked ->
                if (position in tasks.indices) {
                    val task = tasks[position]

                    if (checked) {
                        ReminderScheduler.cancelReminder(
                            this,
                            reminderId(task)
                        )
                    } else {
                        ReminderScheduler.scheduleReminder(
                            this,
                            task,
                            reminderId(task)
                        )
                    }

                    TaskStorage.saveTasks(this, tasks)
                }
            }
        )

        recyclerTasks.layoutManager = LinearLayoutManager(this)
        recyclerTasks.adapter = taskAdapter

        edtSearchTasks.addTextChangedListener(
            object : android.text.TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    taskAdapter.filterTasks(s?.toString() ?: "")
                }

                override fun afterTextChanged(
                    s: android.text.Editable?
                ) {}
            }
        )

        btnAddTask.setOnClickListener {
            addTaskLauncher.launch(
                Intent(this, AddTaskActivity::class.java)
            )
        }

        btnChangePassword.setOnClickListener {
            startActivity(
                Intent(this, ChangePasswordActivity::class.java)
            )
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }

        requestNotificationPermission()
        refreshReminders()
    }

    private fun reminderId(task: Task): Int {
        return (task.name + "|" + task.description).hashCode()
    }

    private fun refreshReminders() {
        for (task in tasks) {
            ReminderScheduler.cancelReminder(
                this,
                reminderId(task)
            )
        }

        for (task in tasks) {
            if (!task.completed && task.dueDate.isNotBlank()) {
                ReminderScheduler.scheduleReminder(
                    this,
                    task,
                    reminderId(task)
                )
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS

            if (
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(permission)
            }
        }
    }

    private fun refreshTasks() {
        val search =
            findViewById<EditText>(
                R.id.edtSearchTasks
            ).text.toString()

        taskAdapter.filterTasks(search)
    }
}
