package com.example.youtubeapi.di

import com.example.youtubeapi.core.network.networkModule
import com.example.youtubeapi.data.remote.remoteDataSource.remoteDataSource


val koinModules = listOf(
    repoModules,
    viewModules,
    remoteDataSource,
    networkModule
)