package com.example.socialmedia.main.domain

import com.example.socialmedia.core.models.User
import com.example.socialmedia.main.data.models.Post

interface Repository {
    fun getUser(): User
    suspend fun getPosts(): List<Post>


}