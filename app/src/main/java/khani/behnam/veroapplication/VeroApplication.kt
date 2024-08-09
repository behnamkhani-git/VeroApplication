package khani.behnam.veroapplication

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.hilt.android.HiltAndroidApp
import khani.behnam.common.domain.usecase.RequestTasks
import khani.behnam.displaytasks.worker.ResourceUpdateWorker
import javax.inject.Inject

@HiltAndroidApp
class VeroApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

class CustomWorkerFactory @Inject constructor(private val requestTasks: RequestTasks): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = ResourceUpdateWorker(requestTasks, appContext, workerParameters)
}