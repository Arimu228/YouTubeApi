package com.example.youtubeapi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtubeapi.App.Companion.repository
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.core.ui.BaseViewModel
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.data.remote.model.ItemList

class DetailViewModel : BaseViewModel() {
    private val mutableVideosId = MutableLiveData<List<String>>()

    val liveVideosId: LiveData<List<String>> = mutableVideosId

    fun getItemList(playlistId: String): LiveData<Resource<ItemList>> {
        return repository.getItemList(playlistId)
    }

    fun getVideosId(data: List<Item>) {
        val result = arrayListOf<String>()
        for (i in data) {
            result.add(i.contentDetails.videoId)
        }
        mutableVideosId.value = (result)
    }


}