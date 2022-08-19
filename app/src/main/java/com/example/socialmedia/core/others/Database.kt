package com.example.socialmedia.core.others

import com.example.socialmedia.core.models.User
import com.example.socialmedia.core.utils.Constants
import com.example.socialmedia.main.data.models.Post
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface Database {


    suspend fun setUserDataInfoOnDatabase(user: User): Task<Void>

    suspend fun getCurrentUserData(id: String): User

    suspend fun getAllUsers(): List<User>


    suspend fun getAllPosts(): List<Post>



}


class DatabaseFromFirebase @Inject constructor(
    private var databaseRef: DatabaseReference,
    ) : Database {


    override suspend fun setUserDataInfoOnDatabase(user: User): Task<Void> {
        return databaseRef.child("users").child(user.id).setValue(user)
    }

    override suspend fun getCurrentUserData(id: String): User {
        TODO("Not yet implemented")

    }

    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPosts(): List<Post> {
        return databaseRef.child(Constants.POSTS).get().await()
            .children.map {
                it.getValue(Post::class.java)!!
            }


    }
}


class DatabaseFromCustomApi : Database {


    override suspend fun setUserDataInfoOnDatabase(user: User): Task<Void> {
        TODO("Not yet implemented")
    }
    override suspend fun getCurrentUserData(id: String): User {
        TODO("Not yet implemented")
    }
    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPosts(): List<Post> {
        TODO("Not yet implemented")
    }

}
