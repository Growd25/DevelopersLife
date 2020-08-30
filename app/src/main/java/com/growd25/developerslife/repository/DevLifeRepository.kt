package com.growd25.developerslife.repository

import com.growd25.developerslife.model.Post

interface DevLifeRepository {
    suspend fun getRandomPost(): Post
}
