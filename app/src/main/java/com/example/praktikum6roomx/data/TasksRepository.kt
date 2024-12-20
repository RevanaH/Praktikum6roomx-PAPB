package com.example.praktikum6roomx.data

import com.example.praktikum6roomx.data.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllTasksStream(): Flow<List<Task>>

    /**
     * Insert item in the data source
     */
    suspend fun insertTask(item: Task)

    /**
     * Delete item from the data source
     */
    suspend fun deleteTask(item: Task)

    /**
     * Update item in the data source
     */
    suspend fun updateTask(item: Task)
}