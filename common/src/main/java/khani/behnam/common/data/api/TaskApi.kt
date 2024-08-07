package khani.behnam.common.data.api

import khani.behnam.common.data.api.model.ApiTask
import retrofit2.http.GET

interface TaskApi {
    @GET(ApiConstant.TASK_ENDPOINT)
    suspend fun requestTasks(): List<ApiTask>
}