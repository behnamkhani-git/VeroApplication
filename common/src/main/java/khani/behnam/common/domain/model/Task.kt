package khani.behnam.common.domain.model

data class Task (
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
)