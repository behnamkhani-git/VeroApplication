package khani.behnam.displaytasks.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import khani.behnam.common.domain.usecase.RequestTasks
import javax.inject.Inject

@HiltWorker
class ResourceUpdateWorker @AssistedInject constructor(
    @Assisted private val requestTasks: RequestTasks,
    @Assisted context: Context,
    @Assisted params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            requestTasks()
            Result.success()
        } catch (e: Exception) {
            Log.e("ResourceUpdateWorker", "Work failed", e)
            e.printStackTrace()
            Result.failure()
        }
    }
}