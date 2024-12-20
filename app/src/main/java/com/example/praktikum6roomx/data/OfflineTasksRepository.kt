package com.example.praktikum6roomx.data

import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(private val taskDao: TaskDao) : TasksRepository {
    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAllTasks()

    override suspend fun insertTask(item: Task) = taskDao.insert(item)

    override suspend fun deleteTask(item: Task) = taskDao.delete(item)

    override suspend fun updateTask(item: Task) = taskDao.update(item)
}