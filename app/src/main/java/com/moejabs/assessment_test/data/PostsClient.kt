package com.moejabs.assessment_test.data

import com.moejabs.assessment_test.pojo.PostModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class PostsClient() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var postInterface: PostInterface = retrofit.create(PostInterface::class.java)

    companion object {
        internal fun getINSTANCE(): PostsClient = PostsClient()
    }

    suspend fun getPosts(userId: String): Response<List<PostModel>> = postInterface.getPosts(userId)

    suspend fun getPostDetails(id: String): Response<PostModel> = postInterface.getPostDetails(id)

    suspend fun createPost(post: PostModel): Response<PostModel> = postInterface.createPost(post)

    suspend fun editPost(id: String, post: PostModel): Response<PostModel> = postInterface.editPost(id, post)

    suspend fun deletePost(id: String): Response<Unit> = postInterface.deletePost(id)

}