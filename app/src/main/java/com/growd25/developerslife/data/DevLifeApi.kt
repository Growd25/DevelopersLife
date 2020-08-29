package com.growd25.developerslife.data

import retrofit2.http.GET


interface DevLifeApi {


    @GET(value = "")
    fun getPost()



    companion object{
        const val BASE_URL = "https://developerslife.ru"
    }
}
