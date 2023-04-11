package com.example.youtubeapi.data.remote


import com.example.youtubeapi.data.remote.model.ItemList
import com.example.youtubeapi.data.remote.model.PlayList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServise {

    @GET("playlists")
    fun getPlayList(
        @Query("key") apiKey: String,
        @Query("channelId") channelId: String,
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int
    ): Call<PlayList>

    @GET("playlistItems")
    fun getItemsList(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int,
        @Query("playlistId") id: String

    ): Call<ItemList>

}