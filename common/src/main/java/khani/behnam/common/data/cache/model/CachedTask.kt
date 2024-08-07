package khani.behnam.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import khani.behnam.common.domain.model.Task

@Entity(
    tableName = "tasks",
)
data class CachedTask(
    @PrimaryKey
    val task: String,
    val title: String,
    val description: String,
    val sort: String,
    val wageType: String,
    val businessUnitKey: String?,
    val businessUnit: String,
    val parentTaskId: String,
    val preplanningBoardQuickSelect: String?,
    val colorCode: String,
    val workingTime: String?,
    val isAvailableInTimeTrackingKioskMode: Boolean,
) {
    fun toDomain(): Task
    {
        return Task(
            task = task,
            title = title,
            description = description,
            sort = sort,
            wageType = wageType,
            businessUnitKey = businessUnitKey,
            businessUnit = businessUnit,
            parentTaskId = parentTaskId,
            preplanningBoardQuickSelect = preplanningBoardQuickSelect,
            colorCode = colorCode,
            workingTime = workingTime,
            isAvailableInTimeTrackingKioskMode = isAvailableInTimeTrackingKioskMode
        )
    }

    companion object{
        fun fromDomain(domainModel: Task): CachedTask{
            return CachedTask(
                task = domainModel.task,
                title = domainModel.title,
                description =  domainModel.description,
                sort =  domainModel.sort,
                wageType =  domainModel.wageType,
                businessUnitKey =  domainModel.businessUnitKey,
                businessUnit =  domainModel.businessUnit,
                parentTaskId =  domainModel.parentTaskId,
                preplanningBoardQuickSelect =  domainModel.preplanningBoardQuickSelect,
                colorCode =  domainModel.colorCode,
                workingTime =  domainModel.workingTime,
                isAvailableInTimeTrackingKioskMode =  domainModel.isAvailableInTimeTrackingKioskMode
            )
        }
    }
}