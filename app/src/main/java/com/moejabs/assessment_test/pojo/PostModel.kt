package com.moejabs.assessment_test.pojo

data class PostModel(
    val userId: Int = -1,
    var id: Int = -1,
    val title: String = "",
    val body: String = ""
) { constructor(userId: Int,title: String, body: String) : this() }