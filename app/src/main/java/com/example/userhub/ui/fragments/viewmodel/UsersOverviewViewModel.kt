package com.example.userhub.ui.fragments.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.userhub.core.types.Gender
import com.example.userhub.domain.usecases.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersOverviewViewModel @Inject constructor(getUsers: GetUsers): ViewModel() {

    private val _currentQuery = MutableLiveData<Gender?>(null)

    val users = _currentQuery.switchMap { query ->
        getUsers(query).cachedIn(viewModelScope)
    }

    fun genderFilteringOrRefreshingList(gender: Gender?) {
        _currentQuery.value = gender
    }

}