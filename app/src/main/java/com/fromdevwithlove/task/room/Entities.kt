package com.fromdevwithlove.task.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timestamps")
data class TimestampEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long
)

@Entity(tableName = "counter")
data class CounterEntity(
    @PrimaryKey val id: Int = 1,
    var counter: Int
)