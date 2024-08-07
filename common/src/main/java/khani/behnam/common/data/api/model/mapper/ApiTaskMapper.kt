package khani.behnam.common.data.api.model.mapper

import khani.behnam.common.data.api.exception.MappingException
import khani.behnam.common.data.api.model.ApiTask
import khani.behnam.common.domain.model.Task
import javax.inject.Inject

class ApiTaskMapper  @Inject constructor(): ApiMapper<ApiTask, Task> {
    override fun mapToDomain(apiEntity: ApiTask): Task {
        return Task(
            task = apiEntity.task ?: throw MappingException("Task ID cannot be null"),
            title =apiEntity.title.orEmpty(),
            description = apiEntity.description.orEmpty(),
            sort = apiEntity.sort.orEmpty(),
            wageType = apiEntity.wageType.orEmpty(),
            businessUnitKey = apiEntity.businessUnitKey.orEmpty(),
            businessUnit = apiEntity.businessUnit.orEmpty(),
            parentTaskId = apiEntity.parentTaskId.orEmpty(),
            preplanningBoardQuickSelect = apiEntity.preplanningBoardQuickSelect.orEmpty(),
            colorCode = apiEntity.colorCode.orEmpty(),
            workingTime = apiEntity.workingTime.orEmpty(),
            isAvailableInTimeTrackingKioskMode = apiEntity.isAvailableInTimeTrackingKioskMode ?: false
        )
    }
}