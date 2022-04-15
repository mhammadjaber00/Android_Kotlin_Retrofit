package com.moejabs.assessment_test.model

data class PostModel(
    val userId: Int = -1,
    var id: Int = -1,
    val title: String? = null,
    val body: String? = null
) { constructor(userId: Int,title: String, body: String) : this(userId, 101, title, body) }