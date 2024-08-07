package khani.behnam.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiTask(
    @field:Json(name = "task") val task: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "sort") val sort: String?,
    @field:Json(name = "wageType") val wageType: String?,
    @field:Json(name = "businessUnitKey") val businessUnitKey: String?,
    @field:Json(name = "businessUnit") val businessUnit: String?,
    @field:Json(name = "parentTaskId") val parentTaskId: String?,
    @field:Json(name = "preplanningBoardQuickSelect") val preplanningBoardQuickSelect: String?,
    @field:Json(name = "colorCode") val colorCode: String?,
    @field:Json(name = "workingTime") val workingTime: String?,
    @field:Json(name = "isAvailableInTimeTrackingKioskMode") val isAvailableInTimeTrackingKioskMode: Boolean?,
)