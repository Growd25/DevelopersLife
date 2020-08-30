package com.growd25.developerslife.repository

import com.growd25.developerslife.data.DevLifeApi
import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory
import javax.inject.Inject

class DefaultDevLifeRepository @Inject constructor(
    private val devLifeApi: DevLifeApi
) : DevLifeRepository {

    override suspend fun getPosts(category: PostsCategory, pageNumber: Int): List<Post> =
        devLifeApi.getPosts(category.name.toLowerCase(), pageNumber).result.map { post ->
            post.copy(gifURL = post.gifURL?.replace("http://", "https://"))
        }
}
