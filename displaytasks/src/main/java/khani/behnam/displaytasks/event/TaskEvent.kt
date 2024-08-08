package khani.behnam.displaytasks.event

sealed class TaskEvent {
    object RequestInitialTasks: TaskEvent()
    data class SearchTasks(val searchQuery: String) : TaskEvent()
}