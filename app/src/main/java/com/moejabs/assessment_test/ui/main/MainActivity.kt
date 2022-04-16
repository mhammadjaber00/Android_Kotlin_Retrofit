package com.moejabs.assessment_test.ui.main

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
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
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { PostsAdapter() }
    private lateinit var touchListener: RecyclerTouchListener
    private lateinit var simpleCallback: ItemTouchHelper.SimpleCallback
    private var deletePosition = -1
    private var editPosition = -1
    private var mPost = PostModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fabAddPost: FloatingActionButton = binding.fabAdd
        postViewModel = PostViewModel()
        setupRecyclerview()
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        fabAddPost.setOnClickListener{
            val dialog = BottomSheetDialog(this@MainActivity)
            val dialogBinding = BottomSheetAddPostBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.closeSheet.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnApply.setOnClickListener {
                val newTitle = if(dialogBinding.etNewTitle.text.toString() != "")  dialogBinding.etNewTitle.text.toString() else "No Title"
                val newBody = if(dialogBinding.etNewBody.text.toString() != "") dialogBinding.etNewBody.text.toString() else "No Description"
                this.mPost = PostModel("1", newTitle, newBody)
                postViewModel.createPost(this.mPost)
                dialog.dismiss()
            }
            dialog.dismissWithAnimation
            dialog.show()
        }

        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        postViewModel.getPosts("1")

        postViewModel.postsMutableLiveData.observe(this) {
                postModels -> adapter.setList(postModels)
        }

        postViewModel.deletePostMutableLiveData.observe(this) {
            adapter.deletePost(deletePosition)
            adapter.notifyItemRemoved(deletePosition)
        }

        postViewModel.editPostMutableLiveData.observe(this) { item ->
            adapter.editPost(editPosition, item)
            adapter.notifyItemChanged(editPosition, item)
        }

        postViewModel.createPostMutableLiveData.observe(this) { item ->
            adapter.addPost(item)
            adapter.notifyItemInserted(0)
        }


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
         simpleCallback = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.absoluteAdapterPosition
                val to = target.absoluteAdapterPosition
                Collections.swap(adapter.getList(), from, to)

                recyclerView.adapter?.notifyItemMoved(from, to)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
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
        dialog.window?.setLayout((resources.displayMetrics.widthPixels*0.8f).toInt(),
            (resources.displayMetrics.heightPixels* 0.30f).toInt()
        )

    }

    @SuppressLint("SetTextI18n")
    private fun  onClickEditPost(position: Int) {
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

        dialogBinding.btnApply.setOnClickListener {
            val id = postViewModel.postsMutableLiveData.value!![position].id
            val newTitle = dialogBinding.etNewTitle.text.toString()
            val newBody = dialogBinding.etNewBody.text.toString()
            this.mPost = PostModel("1", newTitle, newBody)
            postViewModel.editPost(id.toString(), this.mPost)
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