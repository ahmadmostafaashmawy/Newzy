/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.newzy.data

import android.util.Log
import com.example.newzy.api.NewsService
import com.example.newzy.model.Repo
import com.example.newzy.model.RepoSearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
class NewsRepository(private val service: NewsService) {

    private val inMemoryCache = mutableListOf<Repo>()
    private val searchResults = ConflatedBroadcastChannel<RepoSearchResult>()

    suspend fun getSearchResultStream(): Flow<RepoSearchResult> {
        inMemoryCache.clear()
        requestAndSaveData()

        return searchResults.asFlow()
    }


    private suspend fun requestAndSaveData(): Boolean {
        var successful = false

        try {
            val response = service.getRepos()
            Log.d("NewsRepository", "response $response")
            val repos = response.items ?: emptyList()
            inMemoryCache.addAll(repos)
            searchResults.offer(RepoSearchResult.Success(inMemoryCache))
            successful = true
        } catch (exception: IOException) {
            searchResults.offer(RepoSearchResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(RepoSearchResult.Error(exception))
        }
        return successful
    }
}
