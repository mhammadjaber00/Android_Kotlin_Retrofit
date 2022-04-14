package com.moejabs.assessment_test.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moejabs.assessment_test.api.PostsClient
import com.moejabs.assessment_test.model.PostModel
import kotlinx.coroutines.*


class PostViewModel(): ViewModel() {
    val postsMutableLiveData: MutableLiveData<List<PostModel>> = MutableLiveData()
    val singlePostMutableLiveData: MutableLiveData<PostModel> = MutableLiveData()


    fun getPosts(userId: String) {
        viewModelScope.launch {
            val response = PostsClient.getINSTANCE().getPosts(userId)
            if(response.isSuccessful) {
                println("this is the response getAll: ${response.body()}")
                postsMutableLiveData.value = response.body()
            }
        }
    }

    fun getPostsDetails(id: String) {
        viewModelScope.launch {
            val response = PostsClient.getINSTANCE().getPostDetails(id)
            if (response.isSuccessful) {
                println("this is the response getOne: ${response.body()}")
                singlePostMutableLiveData.value = response.body()
            }
        }
    }

    fun createPost(post: PostModel) {
        viewModelScope.launch {
            val response = PostsClient.getINSTANCE().createPost(post)
            if(response.isSuccessful) {
                println("this is the response create: ${response.body()}")
                singlePostMutableLiveData.value = response.body()
            }
        }
    }

    fun editPost(id: String, post:PostModel) {
        viewModelScope.launch {
            val response = PostsClient.getINSTANCE().editPost(id, post)
            if (response.isSuccessful) {
                println("this is the response edit: ${response.body()}")
                singlePostMutableLiveData.value = response.body()
            }
        }
    }

    fun deletePost(id: String) {
        viewModelScope.launch  {
            val response = PostsClient.getINSTANCE().deletePost(id)
            if (response.isSuccessful) {
                println("this is the response delete: ${response.body()}" )
            }
        }
    }


}