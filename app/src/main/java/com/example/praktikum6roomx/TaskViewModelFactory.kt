package com.example.praktikum6roomx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.praktikum6roomx.data.TasksRepository

class TaskViewModelFactory (private val tasksRepository: TasksRepository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(tasksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}