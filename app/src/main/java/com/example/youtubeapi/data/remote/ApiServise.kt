package com.example.youtubeapi.data.remote


import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.data.remote.model.ItemList
import com.example.youtubeapi.data.remote.model.PlayList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServise {

    @GET("playlists")
    suspend fun getPlayList(
        @Query("key") apiKey: String,
        @Query("channelId") channelId: String,
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int
    ): Response<PlayList>

    @GET("playlistItems")
    suspend fun getItemsList(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int,
        @Query("playlistId") channelId: String,
    ): Response<ItemList>

    @GET("videos")
    suspend fun getItemVideo(
        @Query("key") apiKey: String,
        @Query("channelId") channelId: String,
        @Query("part") part: String,
    ): Response<Item>

}