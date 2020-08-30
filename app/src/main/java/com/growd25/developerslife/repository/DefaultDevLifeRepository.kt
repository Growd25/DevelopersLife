package com.growd25.developerslife.repository

import com.growd25.developerslife.data.DevLifeApi
import com.growd25.developerslife.model.Post
import javax.inject.Inject

class DefaultDevLifeRepository @Inject constructor(
    private val devLifeApi: DevLifeApi
) : DevLifeRepository {

    override suspend fun getRandomPost(): Post =
        devLifeApi.getRandomPost().run { copy(gifURL = gifURL?.replace("http://","https://")) }

}
