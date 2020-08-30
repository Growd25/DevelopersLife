package com.growd25.developerslife.data

import com.growd25.developerslife.model.PostsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DevLifeApi {

    @GET("{category}/{pageNumber}?json=true")
    suspend fun getPosts(
        @Path("category") category: String,
        @Path("pageNumber") pageNumber: Int
    ): PostsResponse

    companion object {
        const val BASE_URL = "https://developerslife.ru"
    }
}
