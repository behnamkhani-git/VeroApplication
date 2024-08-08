package khani.behnam.displaytasks.viewstate

import khani.behnam.displaytasks.event.Event
import khani.behnam.displaytasks.model.UiTask

data class TaskViewState(
    val loading: Boolean = true,
    val tasks: List<UiTask> = emptyList(),
    val exception: Event<Throwable>? = null
)