package com.moejabs.assessment_test.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.moejabs.assessment_test.model.PostModel

class MyDiffUtil(
    private val oldList: List<PostModel>,
    private val newList: List<PostModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].body == newList[newItemPosition].body
    }
}