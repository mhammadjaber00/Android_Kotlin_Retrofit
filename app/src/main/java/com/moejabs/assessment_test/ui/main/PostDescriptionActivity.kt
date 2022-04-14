package com.moejabs.assessment_test.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.moejabs.assessment_test.databinding.ActivityPostDescriptionBinding

class PostDescriptionActivity : AppCompatActivity() {


    private lateinit var postViewModel: PostViewModel
    private lateinit var binding: ActivityPostDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postTitle: TextView = binding.postTitle
        val postDescription: TextView = binding.postDescription
        val toolBar: androidx.appcompat.widget.Toolbar = binding.descToolbar

        setSupportActionBar(toolBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        val id: String? = intent.getStringExtra("id")

        postViewModel.getPostsDetails(id!!)

        postViewModel.editPostMutableLiveData.observe(this) {
            postModel ->
            postTitle.text = postModel.title
            postDescription.text = postModel.body
        }
    }
}