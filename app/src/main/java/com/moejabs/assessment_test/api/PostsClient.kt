package com.moejabs.assessment_test.api

import com.moejabs.assessment_test.model.PostModel
import com.moejabs.assessment_test.util.Constants.Companion.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PostsClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val postInterface: PostInterface by lazy {
        retrofit.create(PostInterface::class.java)
    }



}
