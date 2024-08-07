package khani.behnam.common.domain.usecase

import khani.behnam.common.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasks @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke() = taskRepository.getTasks()
}