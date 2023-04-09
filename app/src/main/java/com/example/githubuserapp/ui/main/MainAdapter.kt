package com.example.githubuserapp.ui.main

import com.example.githubuserapp.data.model.UserResponse
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserapp.data.database.User
import com.example.githubuserapp.databinding.ItemUsersBinding

class MainAdapter(private val userList: List<UserResponse>) : ListAdapter<User, MainAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gitUser = userList[position]

        Glide.with(holder.itemView.context)
            .load(gitUser.avatarUrl)
            .centerCrop()
            .circleCrop()
            .into(holder.binding.ciAvatar)
        holder.apply {
            binding.apply {
                tvUsername.text = gitUser.login
                itemView.setOnClickListener{
                    onItemClickCallback.onItemClicked(userList[holder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int = userList.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: UserResponse)
    }

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<User> =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(oldUser: User, newUser: User): Boolean {
                    return oldUser.login == newUser.login
                }

                @SuppressLint("DiffUtilsEquals")
                override fun areContentsTheSame(oldUser: User, newUser: User): Boolean {
                    return oldUser == newUser
                }


            }
    }
}