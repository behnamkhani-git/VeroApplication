package khani.behnam.common.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import khani.behnam.common.data.api.TaskApi
import khani.behnam.common.data.cache.Cache
import khani.behnam.common.data.cache.RoomCache
import khani.behnam.common.data.cache.TaskDatabase
import khani.behnam.common.data.cache.dao.TaskDao
import khani.behnam.common.data.repository.VeroTaskRepository
import khani.behnam.common.domain.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context,
        ): TaskDatabase {
            return Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                "tasks.db"
            )
                .build()
        }

//        @Provides
//        @Singleton
//        fun provideDatabaseInMemory(
//            @ApplicationContext context: Context,
//        ): TaskDatabase {
//            return Room.inMemoryDatabaseBuilder(
//                context,
//                TaskDatabase::class.java,
//            )
//
//                .build()
//        }




        @Provides
        fun provideProductsDao(
            taskDatabase: TaskDatabase,
        ): TaskDao = taskDatabase.taskDao()
    }
}