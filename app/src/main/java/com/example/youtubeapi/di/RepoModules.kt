package com.example.youtubeapi.di

import com.example.youtubeapi.repository.Repository
import org.koin.dsl.module

val repoModules = module { 
    single { Repository(get()) }

}