package khani.behnam.common.data.cache

import khani.behnam.common.data.cache.model.CachedTask
import kotlinx.coroutines.flow.Flow

interface Cache {
    fun getTasks(): Flow<List<CachedTask>>

    suspend fun storeTasks(tasks: List<CachedTask>)

    suspend fun searchTasks(searchQuery: String): List<CachedTask>
}