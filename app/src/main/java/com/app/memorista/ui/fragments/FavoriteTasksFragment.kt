package com.app.memorista.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.memorista.databinding.FragmentSavedTasksBinding
import com.app.memorista.ui.adapters.TaskAdapter
import com.app.memorista.ui.viewmodels.FavoriteTasksViewModel
import com.app.memorista.utils.addRepeatedJob
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTasksFragment : Fragment() {

    private var mBinding: FragmentSavedTasksBinding? = null
    private val mViewModel by viewModel<FavoriteTasksViewModel>()
    private val mTaskAdapter by lazy {
        TaskAdapter(
            onStatusChecked = mViewModel::changeTaskStatus,
            onFavoriteStatusChecked = mViewModel::changeTaskFavoriteStatus
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentSavedTasksBinding.inflate(inflater)
            setupFavoritesList()
        }
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { handleTasksFlow() }
        }
    }

    private fun setupFavoritesList() {
        mBinding?.rvFavoriteTasks?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mTaskAdapter
        }
    }

    private suspend fun handleTasksFlow() {
        mViewModel.favoriteTasks.collect { tasks ->
            mTaskAdapter.submitList(tasks)
        }
    }
}