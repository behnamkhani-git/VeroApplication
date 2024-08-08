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
    @Query("SELECT * FROM tasks ORDER BY task")
    abstract fun getAllTasks(): Flow<List<CachedTask>>

    @Query("SELECT * FROM tasks WHERE task LIKE '%' || :searchQuery || '%' " +
            "OR title LIKE '%' || :searchQuery || '%'" +
            "OR description LIKE '%' || :searchQuery || '%'" +
            "OR sort LIKE '%' || :searchQuery || '%'" +
            "OR wageType LIKE '%' || :searchQuery || '%'" +
            "OR businessUnitKey LIKE '%' || :searchQuery || '%'" +
            "OR businessUnit LIKE '%' || :searchQuery || '%'" +
            "OR parentTaskId LIKE '%' || :searchQuery || '%'" +
            "OR preplanningBoardQuickSelect LIKE '%' || :searchQuery || '%'" +
            "OR colorCode LIKE '%' || :searchQuery || '%'" +
            "OR workingTime LIKE '%' || :searchQuery || '%'" +
            "OR isAvailableInTimeTrackingKioskMode LIKE '%' || :searchQuery || '%' ORDER BY task")
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