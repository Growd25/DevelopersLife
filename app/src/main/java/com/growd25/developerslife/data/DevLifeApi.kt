package com.growd25.developerslife.data

import com.growd25.developerslife.model.Post
import retrofit2.http.GET

interface DevLifeApi {

    @GET("random?json=true")
    suspend fun getRandomPost(): Post

    companion object {
        const val BASE_URL = "https://developerslife.ru"
    }
}
