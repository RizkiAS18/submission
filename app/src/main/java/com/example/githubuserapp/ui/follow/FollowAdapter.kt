package com.example.githubuserapp.ui.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserapp.data.model.UserResponse
import com.example.githubuserapp.databinding.ItemUsersBinding

class FollowAdapter(private val UserList: List<UserResponse>): RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gitUser = UserList[position]
        holder.binding.tvUsername.text = gitUser.login
        Glide.with(holder.itemView.context)
            .load(gitUser.avatarUrl)
            .centerCrop()
            .circleCrop()
            .into(holder.binding.ciAvatar)
    }

    override fun getItemCount(): Int = UserList.size
}