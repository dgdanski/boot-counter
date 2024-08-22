package com.fromdevwithlove.task.room

class Repository(private val db: AppDatabase) {

    suspend fun addTimestamp() {
        val timestampEntity = TimestampEntity(timestamp = System.currentTimeMillis())
        db.appDao().insertTimestamp(timestampEntity)
    }

    suspend fun deleteTimestamp(id: Long) {
        val timestamp = db.appDao().getTimestampById(id)
        if (timestamp != null) {
            db.appDao().deleteTimestamp(timestamp)
        }
    }

    suspend fun countTimestamps(): Int {
        return db.appDao().countTimestamps()
    }

    suspend fun getLastTwoTimestampDifference(): Long? {
        val timestamps = db.appDao().getLastTwoTimestamps()
        return if (timestamps.size == 2) {
            timestamps[0] - timestamps[1]
        } else {
            null
        }
    }

    suspend fun incrementCounter() {
        db.appDao().incrementCounter()
    }

    suspend fun resetCounter() {
        db.appDao().resetCounter()
    }

    suspend fun getCounter(): Int {
        return db.appDao().getCounter()?.counter ?: 0
    }
}