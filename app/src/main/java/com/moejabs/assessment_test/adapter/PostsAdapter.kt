package com.moejabs.assessment_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.moejabs.assessment_test.databinding.PostItemBinding
import com.moejabs.assessment_test.model.PostModel

private lateinit var binding: PostItemBinding

class PostsAdapter(private var postsList: MutableList<PostModel> = mutableListOf()) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    inner class PostViewHolder(binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = postsList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = postsList[position]
        binding.apply {
            titleTV.text = currentPost.title
            bodyTV.text = currentPost.body
        }
    }


    fun setList(newPostsList: MutableList<PostModel>) {
        if (postsList.isEmpty()) {
            postsList = newPostsList
            notifyDataSetChanged()
        }
    }

    fun addPost(p: PostModel) {
        postsList.add(0,p)
        notifyItemInserted(0)
    }

    fun editPost(position: Int, p: PostModel) {
        postsList[position] = p
        notifyItemChanged(position, p)
    }

    fun deletePost(position: Int) {
        postsList.removeAt(position)
        setList(postsList)
        notifyItemRemoved(position)
    }
}