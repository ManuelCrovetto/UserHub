package com.example.userhub.data.network.services

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.userhub.data.constants.ApiConstants.DEFAULT_QUERY
import com.example.userhub.data.network.api.RandomUserAPI
import com.example.userhub.domain.model.UserInfo

private const val STARTING_PAGE_INDEX = 1

class GetDemoRandomUsersService (private val api: RandomUserAPI, private val query: String): PagingSource<Int, UserInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserInfo> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.getRandomUsers(exclude = DEFAULT_QUERY, gender = query, resultsPerPage = params.loadSize, page = position)
            val users = response.body()?.results?.map { UserInfo(it) } ?: emptyList()

            LoadResult.Page(
                data = users,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (users.isEmpty()) null else position + 1
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserInfo>): Int? {
        return state.anchorPosition
    }
}