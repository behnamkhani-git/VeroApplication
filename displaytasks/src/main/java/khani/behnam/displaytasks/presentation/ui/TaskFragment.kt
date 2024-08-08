package khani.behnam.displaytasks.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import khani.behnam.displaytasks.R
import khani.behnam.displaytasks.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.recylcerViewTasks.adapter = null
        _binding = null
    }
}