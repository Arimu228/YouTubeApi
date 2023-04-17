package com.example.youtubeapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.youtubeapi.data.remote.model.ItemList
import com.example.youtubeapi.data.remote.model.PlayList
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.data.remote.remoteDataSource.RemoteDataSource
import kotlinx.coroutines.Dispatchers


class Repository {

    private val dataSource: RemoteDataSource by lazy {
        RemoteDataSource()
    }

    fun getPlayLists(): LiveData<Resource<PlayList>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getPlaylist()
            emit(response)

        }
    }

    fun getItemList(id: String): LiveData<Resource<ItemList>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val itemResponse = dataSource.getItemList(id)
            emit(itemResponse)
        }
    }

    fun getItemVideo(id:String): LiveData<Resource<Item>>{
        return liveData (Dispatchers.IO){
            emit(Resource.loading())
            val videoResponse = dataSource.getItemVideo(id)
            emit(videoResponse)
        }
    }


}