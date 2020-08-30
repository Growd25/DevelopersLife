package com.growd25.developerslife.repository

import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory
import io.reactivex.Single

interface DevlLifeRepository {

    fun getPosts(category: PostsCategory, pageNumber: Int): Single<List<Post>>

}
