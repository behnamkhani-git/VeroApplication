package khani.behnam.displaytasks.model.mapper

import khani.behnam.common.domain.model.Task
import khani.behnam.displaytasks.model.UiTask
import javax.inject.Inject

class UiTaskMapper @Inject constructor(): UiMapper<Task, UiTask> {
    override fun mapToView(input: Task): UiTask {
        return UiTask(
            task = input.task,
            title = input.title,
            description = input.description,
            sort = input.sort,
            wageType = input.wageType,
            businessUnit = input.businessUnit,
            businessUnitKey = input.businessUnitKey,
            parentTaskId = input.parentTaskId,
            preplanningBoardQuickSelect = input.preplanningBoardQuickSelect,
            colorCode = input.colorCode,
            workingTime = input.workingTime,
            isAvailableInTimeTrackingKioskMode = input.isAvailableInTimeTrackingKioskMode
        )
    }
}