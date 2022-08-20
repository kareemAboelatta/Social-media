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

// if want to get the database from the Mars, I don't care just implement this interface and do what you want in your own class
interface Database {

    suspend fun setUserDataInfoOnDatabase(user: User)

    suspend fun getCurrentUserData(id: String): User
    suspend fun getAllUsers(): List<User>
    suspend fun getAllPosts(): List<Post>
}

// this is your own implementation of the Database interface do whatever you want here
class DatabaseFromFirebase @Inject constructor(
    private var databaseRef: DatabaseReference,
    ) : Database {

    override suspend fun getAllPosts(): List<Post> {
        return databaseRef.child(Constants.POSTS).get().await()
            .children.map {
                it.getValue(Post::class.java)!!
            }
    }
    override suspend fun setUserDataInfoOnDatabase(user: User) {
        databaseRef.child("users").child(user.id).setValue(user).await()
    }

    override suspend fun getCurrentUserData(id: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }
}


class DatabaseFromCustomApi : Database {

    override suspend fun setUserDataInfoOnDatabase(user: User) {
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
