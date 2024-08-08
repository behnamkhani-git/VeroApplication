package khani.behnam.common.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import khani.behnam.common.data.cache.model.CachedTask
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskDao {

    @Transaction
    @Query("SELECT * FROM tasks")
    abstract fun getAllTasks(): Flow<List<CachedTask>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE task LIKE '%' || :searchQuery || '%'")
    abstract suspend fun searchTasks(searchQuery: String): List<CachedTask>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertTask(task: CachedTask)

    suspend fun insertTasks(tasks: List<CachedTask>){
        for (task in tasks){
            insertTask(task)
        }
    }
}