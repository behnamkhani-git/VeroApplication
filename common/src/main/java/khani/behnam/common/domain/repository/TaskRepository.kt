package khani.behnam.common.domain.repository

import khani.behnam.common.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasks(): Flow<List<Task>>
    suspend fun requestTasks(): List<Task>
    suspend fun storeTasks(tasks: List<Task>)
    suspend fun searchTasks(searchQuery: String): List<Task>
}