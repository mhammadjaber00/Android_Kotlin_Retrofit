package com.moejabs.assessment_test.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.moejabs.assessment_test.databinding.PostItemBinding
import com.moejabs.assessment_test.model.PostModel



class PostsAdapter(
    private var postsList: MutableList<PostModel> = mutableListOf(),
    ) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    private lateinit var binding: PostItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    inner class PostViewHolder(binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleTV: TextView
        val bodyTV: TextView
        init {
            titleTV = binding.titleTV
            bodyTV = binding.bodyTV
        }
    }


    override fun onBindViewHolder(postViewHolder: PostViewHolder, position: Int) {
        postViewHolder.titleTV.text = postsList[position].title
        postViewHolder.bodyTV.text = postsList[position].body
    }

    override fun getItemCount(): Int = postsList.size


    @SuppressLint("NotifyDataSetChanged")
    fun setList(postsList: MutableList<PostModel>) {
            this.postsList = postsList
            notifyDataSetChanged()
    }

    fun getList() = postsList

    fun addPost(p: PostModel) {
        postsList.add(0,p)
    }

    fun editPost(position: Int, p: PostModel) {
        postsList[position] = p
    }

    fun deletePost(position: Int) {
        postsList.removeAt(position)
    }
}