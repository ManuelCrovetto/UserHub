package com.example.userhub.ui.fragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.userhub.domain.model.UserInfo
import com.example.userhub.ui.fragments.UserDetailFragmentArgs
import com.example.userhub.ui.fragments.viewstates.UserDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(): ViewModel() {

    private val _viewState = MutableStateFlow(UserDetailViewState())
    val viewState = _viewState.asStateFlow()

    private val _result = MutableLiveData<UserInfo>()
    val result: LiveData<UserInfo>
        get() = _result

    fun checkArgs(args: UserDetailFragmentArgs?) {
        args?.let { nonNullArgs ->
            _result.value = nonNullArgs.result
        } ?: run {
            _viewState.value = UserDetailViewState(error = true)
        }
    }
}