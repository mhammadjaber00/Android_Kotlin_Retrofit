package com.moejabs.assessment_test.repository

import com.moejabs.assessment_test.api.PostsClient
import com.moejabs.assessment_test.model.PostModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext
import retrofit2.Response

class PostRepository (
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getPosts(userId: String):
            Response<MutableList<PostModel>> = withContext(ioDispatcher) {
        PostsClient.postInterface.getPosts(userId)
    }

    suspend fun getPostDetails(id: String):
            Response<PostModel> = withContext(ioDispatcher) {
        PostsClient.postInterface.getPostDetails(id)
    }

    suspend fun createPost(post: PostModel):
            Response<PostModel> = withContext(ioDispatcher) {
        PostsClient.postInterface.createPost(post)
    }

    suspend fun editPost(id: String, post: PostModel):
            Response<PostModel> = withContext(ioDispatcher) {
        PostsClient.postInterface.editPost(id, post)
    }

    suspend fun deletePost(id: String):
            Response<Unit> = withContext(ioDispatcher) {
        PostsClient.postInterface.deletePost(id)
    }


}