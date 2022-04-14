package com.moejabs.assessment_test.api

import com.moejabs.assessment_test.model.PostModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class PostsClient() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        internal fun getINSTANCE(): PostsClient = PostsClient()
    }

    private val postInterface: PostInterface by lazy {
        retrofit.create(PostInterface::class.java)
    }

    suspend fun getPosts(userId: String): Response<List<PostModel>> = postInterface.getPosts(userId)

    suspend fun getPostDetails(id: String): Response<PostModel> = postInterface.getPostDetails(id)

    suspend fun createPost(post: PostModel): Response<PostModel> = postInterface.createPost(post)

    suspend fun editPost(id: String, post: PostModel): Response<PostModel> = postInterface.editPost(id, post)

    suspend fun deletePost(id: String): Response<Unit> = postInterface.deletePost(id)

}
