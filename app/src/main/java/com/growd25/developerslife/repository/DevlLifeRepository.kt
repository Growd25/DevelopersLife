package com.growd25.developerslife.repository

import com.growd25.developerslife.model.Post
import io.reactivex.Single

interface DevlLifeRepository {

    fun getPosts(part: String, pageNumber: String): Single<List<Post>>

}
