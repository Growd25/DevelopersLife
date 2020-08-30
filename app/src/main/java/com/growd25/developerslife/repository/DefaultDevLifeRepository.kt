package com.growd25.developerslife.repository

import com.growd25.developerslife.data.DevLifeApi
import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultDevLifeRepository @Inject constructor(
    private val devLifeApi: DevLifeApi
) : DevlLifeRepository {

    override fun getPosts(category: PostsCategory, pageNumber: Int): Single<List<Post>> {
        return devLifeApi.getPosts(category.name.toLowerCase(), pageNumber)
            .map { response -> response.result.map { post -> post.copy(gifURL = post.gifURL.replace("http://","https://")) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
