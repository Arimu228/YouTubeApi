package com.example.youtubeapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtubeapi.model.ItemList

import com.example.youtubeapi.model.PlayList
import com.example.youtubeapi.remote.ApiServise
import com.example.youtubeapi.remote.RetroFitClient
import com.example.youtubeapi.ui.utils.Constant

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository {

    private val apiServise: ApiServise by lazy {
        RetroFitClient.create()
    }

    fun getItemList(id: String): LiveData<ItemList> {
        val data = MutableLiveData<ItemList>()
        apiServise.getItemsList(
            com.example.youtubeapi.BuildConfig.API_KEY,
            Constant.part, 100, id
        ).enqueue(object : Callback<ItemList> {
            override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }

            }

            override fun onFailure(call: Call<ItemList>, t: Throwable) {
                print(t.message)
            }
        })
        return data
    }


    fun getPlayLists(): LiveData<PlayList> {
        val data = MutableLiveData<PlayList>()

        apiServise.getPlayList(
            com.example.youtubeapi.BuildConfig.API_KEY,
            Constant.channelId,
            Constant.part,
            Constant.maxResult
        ).enqueue(object : Callback<PlayList> {
            override fun onResponse(call: Call<PlayList>, response: Response<PlayList>) {
                if (response.isSuccessful){
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<PlayList>, t: Throwable) {
                print(t.message)

            }

        })
        return data
    }


}