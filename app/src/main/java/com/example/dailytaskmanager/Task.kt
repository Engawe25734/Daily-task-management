package com.example.dailytaskmanager

import java.io.Serializable

data class Task(
    var name: String,
    var description: String,
    var completed: Boolean = false
) : Serializable