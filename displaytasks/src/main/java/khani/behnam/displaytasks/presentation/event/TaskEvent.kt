package khani.behnam.displaytasks.presentation.event

sealed class TaskEvent {
    object RequestInitialTasks: TaskEvent()
    object SwipeToRefresh: TaskEvent()
    data class SearchTasks(val searchQuery: String) : TaskEvent()

}