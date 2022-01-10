package com.example.userhub.data.network.api

import com.example.userhub.data.constants.ApiConstants.API_PATH_URL
import com.example.userhub.data.constants.ApiConstants.EXCLUDE_DATA_PARAMETER
import com.example.userhub.data.constants.ApiConstants.GENDER
import com.example.userhub.data.constants.ApiConstants.PAGE
import com.example.userhub.data.constants.ApiConstants.RESULTS_PER_PAGE
import com.example.userhub.data.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserAPI {

    @GET(API_PATH_URL)
    suspend fun getRandomUsers(
        @Query(EXCLUDE_DATA_PARAMETER) exclude: String,
        @Query(GENDER) gender: String,
        @Query(RESULTS_PER_PAGE) resultsPerPage: Int,
        @Query(PAGE) page: Int
    ): Response<UsersResponse>

}