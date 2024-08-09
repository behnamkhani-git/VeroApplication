package khani.behnam.displaytasks.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import khani.behnam.common.shared.SharedViewModel
import khani.behnam.displaytasks.R
import khani.behnam.displaytasks.databinding.FragmentTaskBinding
import khani.behnam.displaytasks.presentation.adapter.TasksAdapter
import khani.behnam.displaytasks.presentation.event.Event
import khani.behnam.displaytasks.presentation.event.TaskEvent
import khani.behnam.displaytasks.presentation.viewmodel.TasksViewModel
import khani.behnam.displaytasks.presentation.viewstate.TaskViewState
import khani.behnam.displaytasks.worker.ResourceUpdateWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private lateinit var workManager: WorkManager

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var sharedViewModel: SharedViewModel

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ITEMS_PER_ROW = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupShared()
        setupUI()
        requestTasksList()
        setupSwipeToRefresh()
        setupTaskUpdateWorker()
    }

    private fun setupTaskUpdateWorker() {
        workManager = WorkManager.getInstance(requireContext())
        val request = PeriodicWorkRequestBuilder<ResourceUpdateWorker>(60, TimeUnit.MINUTES)

            .setConstraints(Constraints(
                requiresBatteryNotLow = true,
                requiredNetworkType = NetworkType.CONNECTED))
            .build()

        workManager.enqueueUniquePeriodicWork(
            "TaskUpdateWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun setupTaskUpdateWorkerTest() {
        workManager = WorkManager.getInstance(requireContext())

        val request = OneTimeWorkRequestBuilder<ResourceUpdateWorker>()

            .setConstraints(Constraints(
                requiresBatteryNotLow = true,
                requiredNetworkType = NetworkType.CONNECTED))
            .setInitialDelay(60, TimeUnit.SECONDS)
            .build()

        workManager.enqueue(
            request
        )
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onEvent(TaskEvent.SwipeToRefresh)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupShared() {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        sharedViewModel.sharedText.observe(viewLifecycleOwner, Observer { searchQuery ->
            viewModel.onEvent(TaskEvent.SearchTasks(searchQuery))
        })
    }

    private fun requestTasksList() {
        if (viewModel.state.value.tasks.isEmpty()) {
            viewModel.onEvent(TaskEvent.RequestInitialTasks)
        }
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        subscribeToViewStateUpdates(adapter)
    }

    private fun createAdapter(): TasksAdapter {
        return TasksAdapter()
    }

    private fun setupRecyclerView(
        tasksAdapter:
        TasksAdapter
    ) {
        binding.recylcerViewTasks.apply {
            adapter = tasksAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                ITEMS_PER_ROW
            )
            setHasFixedSize(true)
        }
    }

    private fun subscribeToViewStateUpdates(adapter: TasksAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    updateScreenState(it, adapter)
                }
            }
        }
    }

    private fun updateScreenState(state: TaskViewState, adapter: TasksAdapter) {
        binding.progressBar.isVisible = state.loading

        adapter.submitList(state.tasks)
        handleFailures(state.exception)
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        //val fallbackMessage = getString(R.string.an_error_occurred)
        val fallbackMessage = "No tasks!"
        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!!
        }

        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.recylcerViewTasks.adapter = null
        _binding = null
    }
}