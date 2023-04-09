package com.example.githubuserapp.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.database.User
import com.example.githubuserapp.data.util.UserDiffCallback
import com.example.githubuserapp.databinding.ItemUsersBinding
import com.example.githubuserapp.ui.detail.DetailActivity

class FavoriteAdapter: ListAdapter<User, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {
    private val userFavorite = ArrayList<User>()

    fun setFavorite(userFav : List<User>){
        val diffCallback = UserDiffCallback(this.userFavorite, userFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.userFavorite.clear()
        this.userFavorite.addAll(userFav)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userFav :User){
            with(binding){
                tvUsername.text = userFav.login
                itemView.setOnClickListener {
                    val intent = Intent (itemView.context, FavoriteDetail::class.java)
                    intent.putExtra(DetailActivity.EXTRA_FAVORITE, userFav.login)
                    itemView.context.startActivity(intent)
                    println(userFav.login)
                }
            }
            Glide.with(itemView.context)
                .load(userFav.imageUrl)
                .centerCrop()
                .circleCrop()
                .into(binding.ciAvatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemUsersBinding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemUsersBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = userFavorite[position]
        holder.bind(favorites)
    }

    override fun getItemCount(): Int = userFavorite.size

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<User> = object :DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldUser: User, newUser: User): Boolean {
                return oldUser.login == newUser.login
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldUser: User, newUser: User): Boolean {
                return oldUser == newUser
            }

        }
    }
}