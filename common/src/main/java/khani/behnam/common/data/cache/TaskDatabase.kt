package khani.behnam.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import khani.behnam.common.data.cache.dao.TaskDao
import khani.behnam.common.data.cache.model.CachedTask

@Database(
    entities = [
        CachedTask::class,
    ],
    version = 1
)

abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}