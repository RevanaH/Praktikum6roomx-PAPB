package com.example.praktikum6roomx.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.praktikum6roomx.data.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Task)

    @Update
    suspend fun update(item: Task)

    @Delete
    suspend fun delete(item: Task)

    @Query("SELECT * from tasks ORDER BY name ASC")
    fun getAllTasks(): Flow<List<Task>>
}