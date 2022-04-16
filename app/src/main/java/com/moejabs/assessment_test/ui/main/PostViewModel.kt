package com.moejabs.assessment_test.ui.main

import androidx.lifecycle.*
import com.moejabs.assessment_test.adapter.PostsAdapter
import com.moejabs.assessment_test.model.PostModel
import com.moejabs.assessment_test.repository.PostRepository
import kotlinx.coroutines.*


class PostViewModel(private val postRepository: PostRepository = PostRepository()): ViewModel() {

    private val _postsMutableLiveData: MutableLiveData<MutableList<PostModel>> = MutableLiveData()
    private val _getPostMutableLiveData: MutableLiveData<PostModel> = MutableLiveData()
    private val _createPostMutableLiveData: MutableLiveData<PostModel> = MutableLiveData()
    private val _editPostMutableLiveData: MutableLiveData<PostModel> = MutableLiveData()
    private val _deletePostMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val postsMutableLiveData: LiveData<MutableList<PostModel>>
        get() = _postsMutableLiveData

    val getPostMutableLiveData: LiveData<PostModel>
        get() = _getPostMutableLiveData

    val createPostMutableLiveData: LiveData<PostModel>
        get() = _createPostMutableLiveData

    val editPostMutableLiveData: LiveData<PostModel>
        get() = _editPostMutableLiveData

    val deletePostMutableLiveData: LiveData<Boolean>
        get() = _deletePostMutableLiveData



     fun getPosts(userId: String) {
        viewModelScope.launch {
            val response = postRepository.getPosts(userId)
            if(response.isSuccessful && postsMutableLiveData.value == null)
                _postsMutableLiveData.value = response.body()
        }
    }

    fun getPostsDetails(id: String) {
        viewModelScope.launch {
            val response = postRepository.getPostDetails(id)
            if (response.isSuccessful)
                _getPostMutableLiveData.value = response.body()
        }
    }


    fun createPost(post: PostModel) {
        viewModelScope.launch {
            val response = postRepository.createPost(post)
            if(response.isSuccessful) {
                _createPostMutableLiveData.postValue(response.body())
            }
        }
    }

    fun editPost(id: String, post:PostModel) {
        viewModelScope.launch {
            val response = postRepository.editPost(id, post)
            if (response.isSuccessful)
                _editPostMutableLiveData.postValue(response.body())
        }
    }

    fun deletePost(id: String) {
        viewModelScope.launch  {
            val response = postRepository.deletePost(id)
            if(response.isSuccessful)
                _deletePostMutableLiveData.value = true
        }
    }


}