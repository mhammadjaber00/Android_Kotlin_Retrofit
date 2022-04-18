package com.moejabs.assessment_test.model

data class PostModel(
    val userId: String? = null,
    val id: String? = null,
    val title: String? = null,
    val body: String? = null
) { constructor(userId: String,title: String, body: String) : this(userId, null, title, body) }
