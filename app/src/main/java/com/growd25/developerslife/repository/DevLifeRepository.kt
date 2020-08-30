package com.growd25.developerslife.repository

import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory

interface DevLifeRepository {
    suspend fun getPosts(category: PostsCategory, pageNumber: Int): List<Post>
}
