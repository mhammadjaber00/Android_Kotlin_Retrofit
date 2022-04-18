package com.moejabs.assessment_test.utils

import androidx.recyclerview.widget.DiffUtil
import com.moejabs.assessment_test.model.PostModel

open class MyDiffCallback (
    private val oldPostsList: List<PostModel>,
    private val newPostsList: List<PostModel>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldPostsList.size

    override fun getNewListSize(): Int = newPostsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPostsList[oldItemPosition].id == newPostsList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldPostsList[oldItemPosition].userId == newPostsList[newItemPosition].userId &&
         oldPostsList[oldItemPosition].title == newPostsList[newItemPosition].title &&
         oldPostsList[oldItemPosition].body == newPostsList[newItemPosition].body)
    }

}