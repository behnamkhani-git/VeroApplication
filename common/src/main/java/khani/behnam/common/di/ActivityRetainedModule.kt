package khani.behnam.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import khani.behnam.common.data.repository.VeroTaskRepository
import khani.behnam.common.domain.repository.TaskRepository
import khani.behnam.common.util.DispatchersProvider
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindTaskRepository(repository: VeroTaskRepository): TaskRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
            DispatchersProvider

}

class CoroutineDispatchersProvider @Inject constructor(): DispatchersProvider