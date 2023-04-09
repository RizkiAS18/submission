package com.example.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.database.User
import com.example.githubuserapp.data.util.UserRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean> = _error

    private val userRepo: UserRepository = UserRepository(application)
    fun getFavorite(): LiveData<List<User>> = userRepo.getAllFavorites()
}