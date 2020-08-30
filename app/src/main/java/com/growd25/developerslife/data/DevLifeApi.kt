package com.growd25.developerslife.data

import com.growd25.developerslife.model.PostsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DevLifeApi {

    @GET("{part}/{pageNumber}?json=true")
    fun getPosts(
        @Path("part") part: String,
        @Path("pageNumber") pageNumber: Int
    ): Single<PostsResponse>

    companion object {
        const val BASE_URL = "https://developerslife.ru"
    }
}
