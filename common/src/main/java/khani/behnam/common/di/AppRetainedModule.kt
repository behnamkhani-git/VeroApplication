package khani.behnam.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import khani.behnam.common.data.repository.VeroTaskRepository
import khani.behnam.common.domain.repository.TaskRepository
import khani.behnam.common.util.DispatchersProvider
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(repository: VeroTaskRepository): TaskRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
            DispatchersProvider

}

class CoroutineDispatchersProvider @Inject constructor(): DispatchersProvider