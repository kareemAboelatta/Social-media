package com.example.socialmedia.main.data.models.repository

import android.content.Context
import com.example.socialmedia.core.models.User
import com.example.socialmedia.core.others.Authenticator
import com.example.socialmedia.core.others.Database
import com.example.socialmedia.core.others.Storage
import com.example.socialmedia.main.data.models.Post
import com.example.socialmedia.main.domain.Repository
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private var database: Database
) : Repository {

    override suspend fun getPosts(): List<Post> = database.getAllPosts()

    override fun getUser(): User {
        TODO("Not yet implemented")
    }
}