package com.example.githubuserapp.data.api

import com.example.githubuserapp.data.model.SearchResponse
import com.example.githubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsersList(): Call<List<UserResponse>>

    @GET("search/users")
    fun searchUser(
        @Query("q")
        query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")
        username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<UserResponse>>
}