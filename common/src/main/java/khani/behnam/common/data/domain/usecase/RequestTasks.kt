package khani.behnam.common.data.domain.usecase

import khani.behnam.common.data.domain.model.Task
import khani.behnam.common.data.domain.repository.TaskRepository
import khani.behnam.common.data.domain.util.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestTasks @Inject constructor(
    private val taskRepository: TaskRepository,
    private val dispatchersProvider: DispatchersProvider,
) {
    suspend operator fun invoke(): List<Task> {
        return withContext(dispatchersProvider.io()) {
            val tasks = taskRepository.requestTasks()

            taskRepository.storeTasks(tasks)
            return@withContext tasks
        }
    }
}