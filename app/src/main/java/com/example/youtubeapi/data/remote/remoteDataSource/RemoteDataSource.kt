package com.example.youtubeapi.data.remote.remoteDataSource

import com.example.youtubeapi.core.network.BaseDataSource
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.data.remote.ApiServise
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.data.remote.model.ItemList
import com.example.youtubeapi.data.remote.model.PlayList

import com.example.youtubeapi.ui.utils.Constant
import org.koin.dsl.module

val remoteDataSource = module {
    factory { RemoteDataSource(get()) }
}

class RemoteDataSource(private val apiServise: ApiServise ) : BaseDataSource() {

    suspend fun getPlaylist(): Resource<PlayList> = getResult {
        apiServise.getPlayList(
            com.example.youtubeapi.BuildConfig.API_KEY,
            Constant.channelId,
            Constant.part,
            Constant.maxResult
        )


    }

    suspend fun getItemList(id: String): Resource<ItemList> = getResult {
        apiServise.getItemsList(
            com.example.youtubeapi.BuildConfig.API_KEY,
            Constant.part, Constant.maxResult, id
        )
    }

    suspend fun getItemVideo(id: String): Resource<Item> = getResult {
        apiServise.getItemVideo(
            com.example.youtubeapi.BuildConfig.API_KEY,
            Constant.part, id
        )
    }
}


