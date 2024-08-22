package com.fromdevwithlove.task

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fromdevwithlove.task.room.AppDatabase
import com.fromdevwithlove.task.room.Repository
import com.fromdevwithlove.task.warehouse.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class OnBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        Logger.tag("${OnBootReceiver::class.simpleName}")
            .log("Received broadcast: ${intent?.action ?: "null action"}")

        context?.let {
            val db = AppDatabase.getDatabase(it.applicationContext, CoroutineScope(SupervisorJob()))
            val repo = Repository(db)
            repo.addTimestamp()
        }
    }
}

fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    @OptIn(DelicateCoroutinesApi::class) // Must run globally; there's no teardown callback.
    GlobalScope.launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}