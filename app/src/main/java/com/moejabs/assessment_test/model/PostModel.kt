package com.moejabs.assessment_test.model

data class PostModel(
    val userId: Int,
    var id: Int,
    val title: String,
    val body: String
) { constructor(userId: Int,title: String, body: String) : this(-1, userId, title, body) }