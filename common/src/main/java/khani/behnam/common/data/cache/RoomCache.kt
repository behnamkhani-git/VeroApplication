package khani.behnam.common.data.cache

import khani.behnam.common.data.cache.dao.TaskDao
import khani.behnam.common.data.cache.model.CachedTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val tasksDao: TaskDao,
) : Cache {
    override fun getTasks(): Flow<List<CachedTask>> {
        return tasksDao.getAllTasks()
    }

    override suspend fun storeTasks(tasks: List<CachedTask>) {
        tasksDao.insertTasks(tasks)
    }

    override suspend fun searchTasks(searchQuery: String): List<CachedTask> {
        return tasksDao.searchTasks(searchQuery)
    }
}