package com.growd25.developerslife.repository

import com.growd25.developerslife.data.DevLifeApi
import com.growd25.developerslife.model.Post
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultDevLifeRepository @Inject constructor(
    private val devLifeApi: DevLifeApi
) : DevlLifeRepository {

    override fun getPosts(part: String, pageNumber: String): Single<List<Post>> =
        devLifeApi.getPosts(part, pageNumber)
            .map { response -> response.result }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
