package com.fromdevwithlove.task.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.fromdevwithlove.task.databinding.ActivityMainBinding
import com.fromdevwithlove.task.job.NotificationWorker
import com.fromdevwithlove.task.room.AppDatabase
import com.fromdevwithlove.task.room.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        val db = AppDatabase.getDatabase(application, CoroutineScope(SupervisorJob()))
        MainViewModelFactory(Repository(db))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.timestampCount.observe(this, Observer { timestampCount ->
            binding.infoTextview.text = "Number of boots: $timestampCount"
        })
    }

    override fun onResume() {
        super.onResume()

        // Schedule the NotificationWorker to run every 15 minutes
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}