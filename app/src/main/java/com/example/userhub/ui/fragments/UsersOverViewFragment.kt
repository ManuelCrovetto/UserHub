package com.example.userhub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.userhub.R
import com.example.userhub.core.dialog.BottomDialogFragmentLauncher
import com.example.userhub.core.dialog.DialogLauncher
import com.example.userhub.core.ex.showBottomDialog
import com.example.userhub.core.ex.showDialogFragment
import com.example.userhub.core.types.Gender
import com.example.userhub.databinding.UsersOverviewFragmentBinding
import com.example.userhub.ui.adapters.UsersAdapter
import com.example.userhub.ui.dialogs.ErrorDialog
import com.example.userhub.ui.dialogs.GenderFilterBottomDialog
import com.example.userhub.ui.fragments.viewmodel.UsersOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersOverViewFragment @Inject constructor(private val usersAdapter: UsersAdapter,
    private val bottomDialogFragmentLauncher: BottomDialogFragmentLauncher,
    private val dialogLauncher: DialogLauncher): Fragment() {

    private var _binding: UsersOverviewFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UsersOverviewViewModel by viewModels()

    private var gender: Gender? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UsersOverviewFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerview()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            fabFilterResults.setOnClickListener { launchFilterDialog() }
            swipeRefreshLayout.setOnRefreshListener { viewModel.genderFilteringOrRefreshingList(gender) }
        }
    }

    private fun launchFilterDialog() {
        GenderFilterBottomDialog.create(
            gender = gender,
            action = GenderFilterBottomDialog.Action {
                this.gender = it
                viewModel.genderFilteringOrRefreshingList(gender)
            }
        ).showBottomDialog(bottomDialogFragmentLauncher, requireActivity())
    }

    private fun initObservers() {
        with(viewModel) {
            users.observe(viewLifecycleOwner) { pagingData ->
                usersAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    private fun initRecyclerview() {
        binding.recyclerView.apply {
            adapter = usersAdapter
        }
        usersAdapter.addLoadStateListener { loadState ->
            with(binding) {
                swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                if (loadState.source.refresh is LoadState.Error) launchErrorDialog()
            }
        }

        usersAdapter.onClickItem = { userInfo ->
            findNavController().navigate(UsersOverViewFragmentDirections.actionUsersOverViewFragmentToUserDetailFragment(userInfo))
        }
    }

    private fun launchErrorDialog() {
        ErrorDialog.create(
            action = ErrorDialog.Action(getString(R.string.retry_placeholder)) {
                viewModel.genderFilteringOrRefreshingList(gender)
            }
        ).showDialogFragment(dialogLauncher, requireActivity())
    }
}