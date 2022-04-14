package com.moejabs.assessment_test.repository

import com.moejabs.assessment_test.api.PostsClient
import com.moejabs.assessment_test.model.PostModel
import retrofit2.Response

class Repository {
    suspend fun getPosts(userId: String): Response<List<PostModel>> = PostsClient.postInterface.getPosts(userId)

    suspend fun getPostDetails(id: String): Response<PostModel> = PostsClient.postInterface.getPostDetails(id)

    suspend fun createPost(post: PostModel): Response<PostModel> = PostsClient.postInterface.createPost(post)

    suspend fun editPost(id: String, post: PostModel): Response<PostModel> = PostsClient.postInterface.editPost(id, post)

    suspend fun deletePost(id: String): Response<Unit> = PostsClient.postInterface.deletePost(id)
}