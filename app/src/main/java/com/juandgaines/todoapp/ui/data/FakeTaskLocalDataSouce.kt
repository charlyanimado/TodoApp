package com.juandgaines.todoapp.ui.data

import com.juandgaines.todoapp.ui.domain.Task
import com.juandgaines.todoapp.ui.domain.TaskLocalDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object FakeTaskLocalDataSouce: TaskLocalDataSource {
    private val _taskFlow = MutableStateFlow<List<Task>>(emptyList())

    override val taskFlow: Flow<List<Task>>
        get() = _taskFlow

    override suspend fun addTask(task: Task) {
        val tasks = _taskFlow.value.toMutableList()
        tasks.add(task)
        delay(1000)
        _taskFlow.value = tasks
    }

    override suspend fun updateTask(task: Task) {
        val tasks = _taskFlow.value.toMutableList()
        val taskindex = tasks.indexOfFirst { it.id == task.id }
        if (taskindex != -1) {
            tasks[taskindex] = task
            delay(100)
            _taskFlow.value = tasks
        }
    }

    override suspend fun removeTask(task: Task) {
        val tasks = _taskFlow.value.toMutableList()
        tasks.remove(task)
        delay(100)
        _taskFlow.value = tasks
    }

    override suspend fun deleteAllTasks() {
        _taskFlow.value = emptyList()
    }

    override suspend fun getTaskById(id: String): Task? {
        return _taskFlow.value.firstOrNull { it.id == id }

    }


}