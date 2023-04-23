package com.example.youtubeapi.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.core.ui.BaseViewModel
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.repository.Repository

class VideoViewModel(private val repository: Repository) : BaseViewModel() {
    private val mutableVideosId = MutableLiveData<List<String>>()
    val liveVideosId: LiveData<List<String>> = mutableVideosId
    fun getItemVideo(id: String): LiveData<Resource<Item>> {
        return repository.getItemVideo(id)
    }

    fun getItemVideos(data: List<Item>) {
        val result = arrayListOf<String>()
        for (i in data) {
            result.add(i.contentDetails.videoId)
        }
        mutableVideosId.value = (result)
    }
}