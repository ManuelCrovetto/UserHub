package com.example.userhub.ui.fragments.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.userhub.data.network.services.getOrAwaitValue
import com.example.userhub.domain.usecases.GetUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UsersOverviewViewModelTest {
    @Mock
    private lateinit var context: Application

    private lateinit var usersOverviewViewModel: UsersOverviewViewModel

    @Mock
    private lateinit var getUser: GetUsers


    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        BDDMockito.`when`(context.applicationContext).thenReturn(context)

        Dispatchers.setMain(testDispatcher)

        usersOverviewViewModel = UsersOverviewViewModel(getUser)

    }
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `we get a paging data object`() {
        runBlockingTest {
            Mockito.`when`(getUser.invoke(null)).thenReturn(MutableLiveData(PagingData.empty()))

            usersOverviewViewModel.genderFilteringOrRefreshingList(null)

        }
        assertFalse(usersOverviewViewModel.users.getOrAwaitValue() is List<*>)
    }


}