package com.example.dailytaskmanager

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//elvis ngawe
//project
//08/16/2026

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskClick: (Int) -> Unit,
    private val onTaskChecked: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkBox: CheckBox =
            itemView.findViewById(R.id.chkTaskCompleted)

        val taskName: TextView =
            itemView.findViewById(R.id.txtTaskName)

        val taskDescription: TextView =
            itemView.findViewById(R.id.txtTaskDescription)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {

        val task = tasks[position]

        // Display task name
        holder.taskName.text = task.name

        // Display task description
        holder.taskDescription.text =
            if (task.description.isEmpty()) {
                "No description"
            } else {
                task.description
            }

        // Prevent the listener from firing while setting the checkbox
        holder.checkBox.setOnCheckedChangeListener(null)

        holder.checkBox.isChecked = task.completed

        updateTextStyle(
            holder.taskName,
            task.completed
        )

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->

            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {

                val currentPosition =
                    holder.bindingAdapterPosition

                tasks[currentPosition].completed = isChecked

                updateTextStyle(
                    holder.taskName,
                    isChecked
                )

                onTaskChecked(
                    currentPosition,
                    isChecked
                )
            }
        }

        // Open the Task Details screen
        // when the task is clicked
        holder.itemView.setOnClickListener {

            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {

                onTaskClick(
                    holder.bindingAdapterPosition
                )
            }
        }
    }

    private fun updateTextStyle(
        textView: TextView,
        completed: Boolean
    ) {

        if (completed) {

            textView.paintFlags =
                textView.paintFlags or
                        Paint.STRIKE_THRU_TEXT_FLAG

        } else {

            textView.paintFlags =
                textView.paintFlags and
                        Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}