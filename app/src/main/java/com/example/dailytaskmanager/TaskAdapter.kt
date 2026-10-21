package com.example.dailytaskmanager

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskClick: (Int) -> Unit,
    private val onTaskChecked: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var visibleTasks = tasks.toList()

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.chkTaskCompleted)
        val taskName: TextView = itemView.findViewById(R.id.txtTaskName)
        val taskDescription: TextView =
            itemView.findViewById(R.id.txtTaskDescription)
        val taskDueDate: TextView =
            itemView.findViewById(R.id.txtTaskDueDate)
    }

    fun filterTasks(query: String) {
        visibleTasks = if (query.isBlank()) {
            tasks.toList()
        } else {
            tasks.filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = visibleTasks[position]

        holder.taskName.text = task.name
        holder.taskDescription.text =
            if (task.description.isBlank()) "No description"
            else task.description

        if (task.dueDate.isBlank()) {
            holder.taskDueDate.visibility = View.GONE
        } else {
            holder.taskDueDate.visibility = View.VISIBLE
            holder.taskDueDate.text = "Due: ${task.dueDate}"
        }

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = task.completed
        updateTextStyle(holder.taskName, task.completed)

        holder.checkBox.setOnCheckedChangeListener { _, checked ->
            val originalPosition = tasks.indexOfFirst { it === task }

            if (originalPosition >= 0) {
                task.completed = checked
                updateTextStyle(holder.taskName, checked)
                onTaskChecked(originalPosition, checked)
            }
        }

        holder.itemView.setOnClickListener {
            val originalPosition = tasks.indexOfFirst { it === task }
            if (originalPosition >= 0) {
                onTaskClick(originalPosition)
            }
        }
    }

    private fun updateTextStyle(textView: TextView, completed: Boolean) {
        if (completed) {
            textView.paintFlags =
                textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags =
                textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int = visibleTasks.size
}
