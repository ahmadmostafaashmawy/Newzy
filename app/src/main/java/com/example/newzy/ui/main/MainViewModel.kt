package com.example.newzy.ui.main

import androidx.lifecycle.*
import com.example.newzy.data.NewsRepository
import com.example.newzy.model.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: NewsRepository) : ViewModel() {
    val repoResult: LiveData<RepoSearchResult> =
        liveData {
            val repos = repository.getSearchResultStream().asLiveData(Dispatchers.Main)
            emitSource(repos)

        }
}