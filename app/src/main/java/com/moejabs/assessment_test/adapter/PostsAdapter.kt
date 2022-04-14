package com.moejabs.assessment_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.moejabs.assessment_test.R
import com.moejabs.assessment_test.model.PostModel
import com.moejabs.assessment_test.ui.main.MyDiffUtil

class PostsAdapter: RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    private var postsList = emptyList<PostModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = postsList[position]
        holder.titleTV.text = currentPost.title
        holder.bodyTV.text = currentPost.body
    }

    override fun getItemCount(): Int = postsList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.titleTV)
        var bodyTV : TextView = itemView.findViewById(R.id.bodyTV)
    }

    fun setList(newPostsList: List<PostModel>) {
        val diffUtil = MyDiffUtil(postsList, newPostsList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        postsList = newPostsList
        diffResults.dispatchUpdatesTo(this)
    }

    fun addPost(p: PostModel) {
        val newPostsList: List<PostModel> = postsList + p
        setList(newPostsList)
    }

    fun editPost(position: Int, p: PostModel) {
        postsList.toMutableList()[position] = p
        setList(postsList)
    }

    fun deletePost(position: Int) {
        val newPostsList = postsList.toMutableList()
        newPostsList.removeAt(position)
        setList(newPostsList)
    }
}