package com.example.youtubeapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtubeapi.data.remote.model.ItemList

import com.example.youtubeapi.data.remote.model.PlayList
import com.example.youtubeapi.data.remote.ApiServise
import com.example.youtubeapi.core.network.RetroFitClient
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.ui.utils.Constant

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository {

    private val apiServise: ApiServise by lazy {
        RetroFitClient.create()
    }

    fun getItemList(id: String): LiveData <Resource<ItemList>> {
        val data = MutableLiveData<Resource<ItemList>>()
        apiServise.getItemsList(
            com.example.youtubeapi.BuildConfig.API_KEY,
            Constant.part, 100, id
        ).enqueue(object : Callback<ItemList> {
            override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                if (response.isSuccessful) {
                    data.value = Resource.success(response.body())
                }
            }

            override fun onFailure(call: Call<ItemList>, t: Throwable) {
                data.value = t.message?.let { Resource.error(it, null, null) }

            }
        })
        return data
    }


    fun getPlayLists(): LiveData<Resource<PlayList>> {
        val data = MutableLiveData<Resource<PlayList>>()
        data.value = Resource.loading()
        apiServise.getPlayList(
            com.example.youtubeapi.BuildConfig.API_KEY,
            Constant.channelId,
            Constant.part,
            Constant.maxResult
        ).enqueue(object : Callback<PlayList> {
            override fun onResponse(call: Call<PlayList>, response: Response<PlayList>) {
                if (response.isSuccessful) {
                    data.value = Resource.success(response.body())
                }
            }

            override fun onFailure(call: Call<PlayList>, t: Throwable) {
                print(t.message)

            }

        })
        return data
    }


}