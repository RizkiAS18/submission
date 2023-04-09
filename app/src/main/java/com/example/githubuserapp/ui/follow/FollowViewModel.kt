package com.example.githubuserapp.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.ApiConfig
import com.example.githubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _listFollow = MutableLiveData<List<UserResponse>>()
    val listFollow : LiveData<List<UserResponse>> = _listFollow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean> = _error

    fun getUserFollower(username:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<UserResponse>>{
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                _listFollow.value = response.body()
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
            }
        })
    }

    fun getUserFollowing(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<UserResponse>>{
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                _listFollow.value = response.body()
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
            }

        })
    }
    fun doneToastError(){
        _error.value = false
    }
}