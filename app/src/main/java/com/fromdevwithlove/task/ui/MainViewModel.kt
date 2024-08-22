package com.fromdevwithlove.task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fromdevwithlove.task.room.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _timestampCount = MutableLiveData<Int>()
    val timestampCount: LiveData<Int> get() = _timestampCount

    private val _counterValue = MutableLiveData<Int>()
    val counterValue: LiveData<Int> get() = _counterValue

    init {
        getTimestampCount()
        getCounterValue()
    }

    fun addTimestamp() {
        viewModelScope.launch {
            repository.addTimestamp()
            getTimestampCount() // Refresh count after adding
        }
    }

    fun resetCounter() {
        viewModelScope.launch {
            repository.resetCounter()
            getCounterValue() // Refresh after resetting
        }
    }

    fun incrementCounter() {
        viewModelScope.launch {
            repository.incrementCounter()
            getCounterValue() // Refresh after incrementing
        }
    }

    private fun getTimestampCount() {
        viewModelScope.launch {
            _timestampCount.value = repository.countTimestamps()
        }
    }

    private fun getCounterValue() {
        viewModelScope.launch {
            _counterValue.value = repository.getCounter()
        }
    }

    fun getLastTwoTimestampDifference(): LiveData<Long?> {
        val difference = MutableLiveData<Long?>()
        viewModelScope.launch {
            difference.value = repository.getLastTwoTimestampDifference()
        }
        return difference
    }
}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

