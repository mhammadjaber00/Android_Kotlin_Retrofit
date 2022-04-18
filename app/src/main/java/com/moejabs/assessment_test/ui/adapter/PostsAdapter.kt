package com.moejabs.assessment_test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.moejabs.assessment_test.R
import com.moejabs.assessment_test.databinding.PostItemBinding
import com.moejabs.assessment_test.model.PostModel
import com.moejabs.assessment_test.utils.MyDiffCallback


class PostsAdapter(
    private val postsList: MutableList<PostModel> = mutableListOf(),
    ) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    private lateinit var binding: PostItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    inner class PostViewHolder constructor(binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleTV: TextView
        val bodyTV: TextView
        val container: RelativeLayout
        init {
            titleTV = binding.titleTV
            bodyTV = binding.bodyTV
            container = binding.container
        }
    }


    override fun onBindViewHolder(postViewHolder: PostViewHolder, position: Int) {
        postViewHolder.apply {
            titleTV.text = postsList[position].title
            bodyTV.text = postsList[position].body
            container.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_in)
        }
    }

    override fun getItemCount(): Int = postsList.size


    fun setList(postsList: MutableList<PostModel>) {
        val diffCallback = MyDiffCallback(this.postsList, postsList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        this.postsList.clear()
        this.postsList.addAll(postsList)
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