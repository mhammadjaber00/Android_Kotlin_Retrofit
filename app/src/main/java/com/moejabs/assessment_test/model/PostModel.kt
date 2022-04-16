package com.moejabs.assessment_test.model

data class PostModel(
    val userId: String = "",
    var id: String = "",
    val title: String? = "",
    val body: String? = ""
) { constructor(userId: String,title: String, body: String) : this(userId, "", title, body) }
