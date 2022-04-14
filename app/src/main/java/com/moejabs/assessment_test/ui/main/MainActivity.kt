package com.moejabs.assessment_test.ui.main

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.moejabs.assessment_test.R
import com.moejabs.assessment_test.databinding.BottomSheetAddPostBinding
import com.moejabs.assessment_test.pojo.PostModel
import com.moejabs.assessment_test.ui.recyclerswipe.RecyclerTouchListener
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostsAdapter
    private lateinit var touchListener: RecyclerTouchListener


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fabAddPost: FloatingActionButton = findViewById(R.id.fab_add)

        fabAddPost.setOnClickListener{
            val dialog = BottomSheetDialog(this)
            val dialogBinding =BottomSheetAddPostBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.closeSheet.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnCreate.setOnClickListener {
                val newTitle = dialogBinding.etNewTitle.text.toString()
                val newBody = dialogBinding.etNewBody.text.toString()
                val newPost = PostModel(1, newTitle, newBody)
                postViewModel.createPost(newPost)
                postViewModel.singlePostMutableLiveData.observe(this) {
                    postModel -> adapter.addPost(postModel)
                }
                dialog.dismiss()
            }

            dialog.show()
        }


        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        postViewModel.getPosts("1")

        postViewModel.postsMutableLiveData.observe(this
        ) { postModels -> adapter.setList(postModels!!.toMutableList()) }






        // Recycler View
        recyclerView = findViewById(R.id.recycler)
        adapter = PostsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        touchListener = RecyclerTouchListener(this, recyclerView)
            .setClickable(object: RecyclerTouchListener.OnRowClickListener {
                override fun onRowClicked(position: Int) {
                    val intent = Intent(this@MainActivity, PostDescriptionActivity::class.java)
                    intent.putExtra("id", postViewModel.postsMutableLiveData.value!![position].id.toString())
                    startActivity(intent)
                }

                override fun onIndependentViewClicked(independentViewID: Int, position: Int) {
                    // Not Needed
                }

            })
            .setSwipeOptionViews(R.id.delete_post, R.id.edit_post)
            .setSwipeable(R.id.rowFG, R.id.rowBG)
            {
                    viewID, position ->
                val positiveButtonClick =
                    { _: DialogInterface, _: Int ->
                        postViewModel.deletePost(postViewModel.postsMutableLiveData.value!![position].id.toString())
                        postViewModel.singlePostMutableLiveData.observe(this) { adapter.deletePost(position) }
                    }
                val negativeButtonClick = { _: DialogInterface, _: Int ->
                    Toast.makeText(applicationContext, "Action Aborted", Toast.LENGTH_LONG).show()
                }

                when (viewID) {

                    R.id.delete_post -> {
                        AlertDialog.Builder(this)
                            .setTitle("Delete Post")
                            .setMessage("You are about to delete this post. Are you sure you want to continue?")
                            .setPositiveButton("Delete", DialogInterface.OnClickListener(
                                function = positiveButtonClick)
                            )
                            .setNegativeButton("Cancel", DialogInterface.OnClickListener(negativeButtonClick))
                            .show()

                    }

                    R.id.edit_post -> {
                        val dialog = BottomSheetDialog(this)
                        val dialogBinding =BottomSheetAddPostBinding.inflate(layoutInflater)
                        dialogBinding.tvcreate.text = "Edit post"

                        dialogBinding.etNewTitle.setText(postViewModel.postsMutableLiveData.value!![position].title)
                        dialogBinding.etNewBody.setText(postViewModel.postsMutableLiveData.value!![position].body)
                        dialog.setContentView(dialogBinding.root)

                        dialogBinding.closeSheet.setOnClickListener {
                            dialog.dismiss()
                        }

                        dialogBinding.btnCreate.setOnClickListener {
                            val newTitle = dialogBinding.etNewTitle.text.toString()
                            val newBody = dialogBinding.etNewBody.text.toString()
                            val newPost = PostModel(1, newTitle, newBody)
                            postViewModel.editPost(postViewModel.postsMutableLiveData.value!![position].id.toString(), newPost)
                            postViewModel.singlePostMutableLiveData.observe(this) {
                                adapter.editPost(position, newPost)
                            }
                            dialog.dismiss()
                        }
                        dialog.show()
                    }
                }
            }

        recyclerView.addOnItemTouchListener(touchListener)


    }
}