package com.moejabs.assessment_test.ui.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moejabs.assessment_test.api.PostsClient
import com.moejabs.assessment_test.model.PostModel
import kotlinx.coroutines.*


class PostViewModel(): ViewModel() {

    val postsMutableLiveData: MutableLiveData<MutableList<PostModel>> = MutableLiveData()
    val getPostMutableLiveData: MutableLiveData<PostModel> = MutableLiveData()
    val createPostMutableLiveData: MutableLiveData<PostModel> = MutableLiveData()
    val editPostMutableLiveData: MutableLiveData<PostModel> = MutableLiveData()
    val deletePostMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getPosts(userId: String) {
        viewModelScope.launch {
            val response = PostsClient.getINSTANCE().getPosts(userId)
            if(response.isSuccessful) {
                postsMutableLiveData.value = response.body()
            }
        }
    }

    fun getPostsDetails(id: String) {
        viewModelScope.launch {
            val response = PostsClient.getINSTANCE().getPostDetails(id)
            if (response.isSuccessful) {
                getPostMutableLiveData.value = response.body()
            }
        }
    }

    fun createPost(post: PostModel) {
        viewModelScope.launch {
            val response = PostsClient.getINSTANCE().createPost(post)
            if(response.isSuccessful) {
                createPostMutableLiveData.value = response.body()
            }
        }
    }

    fun editPost(id: String, post:PostModel) {
        viewModelScope.launch {
            val response = PostsClient.getINSTANCE().editPost(id, post)
            if (response.isSuccessful) {
                editPostMutableLiveData.value = response.body()
            }
        }
    }

    fun deletePost(id: String) {
        viewModelScope.launch  {
            val response = PostsClient.getINSTANCE().deletePost(id)
            if(response.isSuccessful) deletePostMutableLiveData.value = true
        }
    }


}