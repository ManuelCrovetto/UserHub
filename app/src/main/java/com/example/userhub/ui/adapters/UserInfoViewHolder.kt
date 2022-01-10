package com.example.userhub.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.userhub.R
import com.example.userhub.core.ex.getCountryName
import com.example.userhub.databinding.UserItemBinding
import com.example.userhub.domain.model.UserInfo

class UserInfoViewHolder (private val binding: UserItemBinding, private val glide: RequestManager): RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind(user: UserInfo, onClickItem: (UserInfo) -> Unit) {
        setUpCopies(user)
        binding.root.setOnClickListener { onClickItem(user) }
    }

    private fun setUpCopies(user: UserInfo) {
        with(binding) {
            glide.load(user.response.picture.thumbnail).into(ivProfileAvatar)
            tvName.text = context.getString(R.string.user_name_placeholder, user.response.name.first, user.response.name.last)
            tvGender.text = user.response.gender
            tvAge.text = user.response.dob.age.toString()
            tvNationality.text = user.response.nat.getCountryName()
        }
    }
}