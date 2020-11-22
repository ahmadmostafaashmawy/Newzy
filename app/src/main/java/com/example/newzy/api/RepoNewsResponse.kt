
package com.example.newzy.api

import com.example.newzy.model.Repo
import com.google.gson.annotations.SerializedName

data class RepoNewsResponse(
    @SerializedName("value") val items: List<Repo> = emptyList(),
)
