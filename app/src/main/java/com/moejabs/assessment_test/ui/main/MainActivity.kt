package com.moejabs.assessment_test.ui.main

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.moejabs.assessment_test.R
import com.moejabs.assessment_test.adapter.PostsAdapter
import com.moejabs.assessment_test.databinding.ActivityMainBinding
import com.moejabs.assessment_test.databinding.BottomSheetAddPostBinding
import com.moejabs.assessment_test.model.PostModel
import com.moejabs.assessment_test.ui.recyclerswipe.RecyclerTouchListener

class MainActivity : AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { PostsAdapter() }
    private lateinit var touchListener: RecyclerTouchListener
    var deletePosition = -1
    var editPosition = -1
    var mPost = PostModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fabAddPost: FloatingActionButton = binding.fabAdd
        postViewModel = PostViewModel()
        setupRecyclerview()


        fabAddPost.setOnClickListener{
            val dialog = BottomSheetDialog(this@MainActivity)
            val dialogBinding = BottomSheetAddPostBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.closeSheet.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnCreate.setOnClickListener {
                val newTitle = dialogBinding.etNewTitle.text.toString()
                val newBody = dialogBinding.etNewBody.text.toString()
                mPost = PostModel(1, newTitle, newBody)
                postViewModel.createPost(mPost)
                dialog.dismiss()
            }
            dialog.dismissWithAnimation
            dialog.show()
        }

        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        postViewModel.getPosts("1")

        postViewModel.postsMutableLiveData.observe(this) { postModels -> adapter.setList(postModels) }

        postViewModel.liveDelete().observe(this) { adapter.deletePost(deletePosition) }

        postViewModel.liveEdit().observe(this) { adapter.editPost(editPosition, mPost)  }

        postViewModel.createPostMutableLiveData.observe(this) { adapter.addPost(mPost) }

    }


    private fun setupRecyclerview() {
        recyclerView = binding.recycler
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
                when (viewID) {
                    R.id.delete_post -> {
                        onClickDeletePost(position)
                    }
                    R.id.edit_post -> {
                        onClickEditPost(position)
                    }
                }
            }
        recyclerView.addOnItemTouchListener(touchListener)
    }


    private fun onClickDeletePost(position: Int) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete Post")
            .setMessage("You are about to delete this post. Are you sure you want to continue?")
            .setPositiveButton("Delete"
            ) { _: DialogInterface, _: Int ->
                val id = postViewModel.postsMutableLiveData.value!![position].id.toString()
                postViewModel.deletePost(id)
                deletePosition = position


            }
            .setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                Toast.makeText(applicationContext, "Action Aborted", Toast.LENGTH_LONG).show()
            }
            .show()

        val mDisplayMetrics = windowManager.currentWindowMetrics
        val mDisplayWidth = mDisplayMetrics.bounds.width()
        val mDisplayHeight = mDisplayMetrics.bounds.height()
        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.width = (mDisplayWidth * 0.8f).toInt()
        mLayoutParams.height = (mDisplayHeight * 0.25f).toInt()
        dialog.window?.attributes = mLayoutParams
    }

    private fun onClickEditPost(position: Int) {
        val dialog = BottomSheetDialog(this)

        // Set global position to be called by observer later
        editPosition = position

        // Using same BottomSheet fragment of create post Action
        val dialogBinding = BottomSheetAddPostBinding.inflate(layoutInflater)

        // Changed Button Title
        dialogBinding.tvcreate.text = "Edit post"

        // Set Edit text values to current post values
        val title = postViewModel.postsMutableLiveData.value!![position].title
        val body = postViewModel.postsMutableLiveData.value!![position].body

        dialogBinding.etNewTitle.setText(title)
        dialogBinding.etNewBody.setText(body)

        dialog.setContentView(dialogBinding.root)

        dialogBinding.closeSheet.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnCreate.setOnClickListener {
            val id = postViewModel.postsMutableLiveData.value!![position].id.toString()
            val newTitle = dialogBinding.etNewTitle.text.toString()
            val newBody = dialogBinding.etNewBody.text.toString()
            mPost = PostModel(1, newTitle, newBody)
            postViewModel.editPost(id, mPost)
            dialog.dismiss()
        }
        dialog.show()
    }


//    private fun checkForInternet(context: Context): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//            val network = connectivityManager.activeNetwork ?: return false
//
//            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
//
//            return when {
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                else -> false
//            }
//    }
}