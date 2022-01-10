package com.example.userhub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.example.userhub.databinding.UserItemBinding
import com.example.userhub.domain.model.UserInfo
import javax.inject.Inject

class UsersAdapter @Inject constructor(private val glide: RequestManager): PagingDataAdapter <UserInfo, UserInfoViewHolder>(USER_COMPARATOR){

    var onClickItem: (UserInfo) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserInfoViewHolder(binding = binding, glide = glide)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null)
            holder.bind(user = currentItem, onClickItem = onClickItem)
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserInfo>() {
            override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem.response.id.value == newItem.response.id.value


            override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem == newItem

        }
    }
}