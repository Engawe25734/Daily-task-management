package com.example.dailytaskmanager

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object TaskStorage {

    private const val PREFS_NAME = "DailyTaskManagerTasks"
    private const val TASKS_KEY = "tasks"

    fun saveTasks(context: Context, tasks: List<Task>) {
        val jsonArray = JSONArray()

        for (task in tasks) {
            val item = JSONObject()
            item.put("name", task.name)
            item.put("description", task.description)
            item.put("completed", task.completed)
            item.put("dueDate", task.dueDate)
            jsonArray.put(item)
        }

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(TASKS_KEY, jsonArray.toString())
            .apply()
    }

    fun loadTasks(context: Context): MutableList<Task> {
        val savedData = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        ).getString(TASKS_KEY, null) ?: return mutableListOf()

        return try {
            val array = JSONArray(savedData)
            val tasks = mutableListOf<Task>()

            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)

                tasks.add(
                    Task(
                        name = item.optString("name", ""),
                        description = item.optString("description", ""),
                        completed = item.optBoolean("completed", false),
                        dueDate = item.optString("dueDate", "")
                    )
                )
            }

            tasks
        } catch (_: Exception) {
            mutableListOf()
        }
    }
}
