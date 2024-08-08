package khani.behnam.common.data.repository

import khani.behnam.common.data.api.TaskApi
import khani.behnam.common.data.api.model.mapper.ApiTaskMapper
import khani.behnam.common.data.cache.Cache
import khani.behnam.common.data.cache.model.CachedTask
import khani.behnam.common.domain.model.Task
import khani.behnam.common.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VeroTaskRepository @Inject constructor(
    private val api: TaskApi,
    private val cache: Cache,
    private val apiTaskMapper: ApiTaskMapper,
    private val cachedTaskMapper: ApiTaskMapper
) : TaskRepository {

    override suspend fun requestTasks(): List<Task> {
        val tasks = api.requestTasks()
        val domainTasks = tasks.map {
            apiTaskMapper.mapToDomain(it)
        }
        storeTasks(domainTasks)
        return domainTasks
    }

    override suspend fun getTasks(): Flow<List<Task>> {
        return cache.getTasks().distinctUntilChanged().map { taskList ->
            taskList.map {
                it.toDomain()
            }
        }
    }

    override suspend fun storeTasks(tasks: List<Task>) {
        cache.storeTasks(tasks.map {
            CachedTask.fromDomain(it)
        })
    }

    override suspend fun searchTasks(searchQuery: String): List<Task> {
        return cache.searchTasks(searchQuery).map { it.toDomain()}
    }
}