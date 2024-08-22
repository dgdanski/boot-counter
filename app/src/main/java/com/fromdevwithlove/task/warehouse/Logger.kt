package com.fromdevwithlove.task.warehouse

import android.util.Log

class Logger private constructor(private val tag: String = "") {

    fun log(message: String?, priority: Int = Log.DEBUG): Logger {
        try {
            Log.println(priority, tag, message!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun e(message: String?): Logger {
        return log(message, Log.ERROR)
    }

    fun withException(cause: Exception?) {
        Log.println(Log.DEBUG, tag, Log.getStackTraceString(cause))
    }

    fun withThrowable(throwable: Throwable?) {
        Log.println(Log.DEBUG, tag, Log.getStackTraceString(throwable))
    }

    companion object {
        fun tag(tag: String): Logger {
            return Logger(tag)
        }

        fun log(message: String?): Logger {
            return Logger().log(message)
        }

        fun e(message: String?): Logger {
            return Logger().log(message, Log.ERROR)
        }

        fun i(message: String?): Logger {
            return Logger().log(message, Log.INFO)
        }

        fun v(message: String?): Logger {
            return Logger().log(message, Log.VERBOSE)
        }

        fun w(message: String?): Logger {
            return Logger().log(message, Log.WARN)
        }
    }
}