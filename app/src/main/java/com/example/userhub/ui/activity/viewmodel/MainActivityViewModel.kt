package com.example.userhub.ui.activity.viewmodel

import androidx.lifecycle.ViewModel
import com.example.userhub.core.connectivitymanager.ConnectionStatusLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var connectionStatus: ConnectionStatusLiveData

}