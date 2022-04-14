package com.moejabs.assessment_test.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.moejabs.assessment_test.R
import kotlinx.coroutines.DelicateCoroutinesApi

class PostDescriptionActivity : AppCompatActivity() {


    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_description)

        val postTitle: TextView = findViewById(R.id.postTitle)
        val postDescription: TextView = findViewById(R.id.postDescription)
        val toolBar: androidx.appcompat.widget.Toolbar = findViewById(R.id.descToolbar)

        setSupportActionBar(toolBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        val id: String? = intent.getStringExtra("id")

        postViewModel.getPostsDetails(id!!)

        postViewModel.singlePostMutableLiveData.observe(this) {
            postModel ->
            postTitle.text = postModel.title
            postDescription.text = postModel.body
        }
    }
}