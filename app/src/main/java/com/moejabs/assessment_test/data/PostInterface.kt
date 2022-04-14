package com.moejabs.assessment_test.data

import com.moejabs.assessment_test.pojo.PostModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostInterface {
    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: String): Response<List<PostModel>>

    @GET("posts/{id}")
    suspend fun getPostDetails(@Path("id") id: String): Response<PostModel>

    @POST("posts")
    suspend fun createPost(@Body post: PostModel): Response<PostModel>

    @PUT("posts/{id}")
    suspend fun editPost(@Path("id") id: String, @Body post: PostModel): Response<PostModel>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: String): Response<Unit>

}