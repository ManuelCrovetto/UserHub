package com.example.userhub.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.userhub.core.types.Gender
import com.example.userhub.data.constants.ApiConstants
import com.example.userhub.data.constants.ApiConstants.FEMALE_GENDER
import com.example.userhub.data.constants.ApiConstants.MALE_GENDER
import com.example.userhub.data.constants.PagingConstants.ENABLE_PLACE_HOLDERS
import com.example.userhub.data.constants.PagingConstants.MAX_SIZE
import com.example.userhub.data.constants.PagingConstants.PAGE_SIZE
import com.example.userhub.data.network.api.RandomUserAPI
import com.example.userhub.data.network.services.GetDemoRandomUsersService
import com.example.userhub.domain.model.UserInfo
import javax.inject.Inject

class GetUsers @Inject constructor(private val api: RandomUserAPI) {

    operator fun invoke(gender: Gender?): LiveData<PagingData<UserInfo>> {
        val query = when (gender) {
            Gender.MALE -> MALE_GENDER
            Gender.FEMALE -> FEMALE_GENDER
            null -> ""
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = ENABLE_PLACE_HOLDERS
            ),
            pagingSourceFactory = { GetDemoRandomUsersService(api = api, query = query) }
        ).liveData
    }

}