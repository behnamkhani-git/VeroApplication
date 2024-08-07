package khani.behnam.displaytasks.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import khani.behnam.displaytasks.databinding.RecyclerViewTaskItemBinding
import khani.behnam.displaytasks.model.UiTask

class TasksAdapter(): ListAdapter<UiTask, TasksAdapter.TasksViewHolder>(ITEM_COMPARATOR) {

    inner class TasksViewHolder(
        private val binding: RecyclerViewTaskItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: UiTask
        ) {
            binding.textViewTaskId.text = item.task
            binding.textViewTitle.text = item.title
            binding.textViewDescription.text = item.description
            binding.colorView.setColor(item.colorCode)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding = RecyclerViewTaskItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val item: UiTask = getItem(position)
        holder.bind(item)
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UiTask>() {
    override fun areItemsTheSame(oldItem: UiTask, newItem: UiTask): Boolean {
        return oldItem.task == newItem.task
    }

    override fun areContentsTheSame(oldItem: UiTask, newItem: UiTask): Boolean {
        return oldItem.task == newItem.task
    }
}