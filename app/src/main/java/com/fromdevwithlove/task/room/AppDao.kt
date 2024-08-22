package com.fromdevwithlove.task.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {

    // Timestamps

    @Insert
    suspend fun insertTimestamp(timestampEntity: TimestampEntity)

    @Delete
    suspend fun deleteTimestamp(timestampEntity: TimestampEntity)

    @Query("SELECT COUNT(*) FROM timestamps")
    suspend fun countTimestamps(): Int

    @Query("SELECT timestamp FROM timestamps ORDER BY id DESC LIMIT 2")
    suspend fun getLastTwoTimestamps(): List<Long>

    // Get timestamp by ID
    @Query("SELECT * FROM timestamps WHERE id = :timestampId LIMIT 1")
    suspend fun getTimestampById(timestampId: Long): TimestampEntity?

    // Counter

    @Query("SELECT * FROM counter LIMIT 1")
    suspend fun getCounter(): CounterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCounter(counterEntity: CounterEntity)

    @Query("UPDATE counter SET counter = counter + 1 WHERE id = 1")
    suspend fun incrementCounter()

    @Query("UPDATE counter SET counter = 0 WHERE id = 1")
    suspend fun resetCounter()
}