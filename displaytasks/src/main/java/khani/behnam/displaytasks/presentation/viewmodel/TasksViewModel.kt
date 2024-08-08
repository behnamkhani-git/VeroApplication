package khani.behnam.displaytasks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import khani.behnam.common.domain.usecase.GetTasks
import khani.behnam.common.domain.usecase.RequestTasks
import khani.behnam.common.domain.usecase.SearchTasks
import khani.behnam.common.util.createExceptionHandler
import khani.behnam.displaytasks.presentation.event.Event
import khani.behnam.displaytasks.presentation.event.TaskEvent
import khani.behnam.displaytasks.model.UiTask
import khani.behnam.displaytasks.model.mapper.UiTaskMapper
import khani.behnam.displaytasks.presentation.viewstate.TaskViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val requestTasks: RequestTasks,
    private val searchTasks: SearchTasks,
    private val uiTaskMapper: UiTaskMapper,
    private val getTasks: GetTasks,
) : ViewModel() {

    init {
        observeViewStateChanges()
    }

    private val _state = MutableStateFlow(TaskViewState())
    val state: StateFlow<TaskViewState> = _state.asStateFlow()

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.RequestInitialTasks ->
                loadTasks()

            is TaskEvent.SearchTasks ->
                searchOnTasks(event.searchQuery)
        }
    }

    private fun searchOnTasks(searchQuery: String) {
        val errorMessage = "Failed to fetch tasks!"
        val exceptionHandler =
            viewModelScope.createExceptionHandler(errorMessage) { onException(it) }
        viewModelScope.launch(exceptionHandler) {
            val result = searchTasks(searchQuery)
            onNewTaskList(result.map { uiTaskMapper.mapToView(it) })
        }
    }

    private fun loadTasks() {
        val errorMessage = "Failed to fetch tasks!"
        val exceptionHandler =
            viewModelScope.createExceptionHandler(errorMessage) { onException(it) }
        viewModelScope.launch(exceptionHandler) {
            requestTasks()
        }
    }

    private fun observeViewStateChanges() {
        viewModelScope.launch {
            getTasks()
                .map { tasks -> tasks.map { uiTaskMapper.mapToView(it) } }
                .flowOn(Dispatchers.IO)
                .catch { onException(it) }
                .collect { onNewTaskList(it) }
        }
    }

    private fun onNewTaskList(tasks: List<UiTask>) {
        //val updatedTasksSet = (state.value.tasks + tasks).toSet()

        _state.update { oldState ->
            oldState.copy(
                loading = false,
                tasks = tasks.toList()
            )
        }
    }

    private fun onException(exception: Throwable) {
        _state.update { oldState ->
            oldState.copy(
                loading = false,
                exception = Event(exception)
            )
        }
    }
}