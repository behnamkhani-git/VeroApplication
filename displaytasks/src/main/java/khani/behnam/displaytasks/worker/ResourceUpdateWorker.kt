package khani.behnam.displaytasks.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import khani.behnam.common.domain.usecase.RequestTasks
import javax.inject.Inject

class ResourceUpdateWorker @Inject constructor(
    private val requestTasks: RequestTasks,
    context: Context,
    params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            requestTasks()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}