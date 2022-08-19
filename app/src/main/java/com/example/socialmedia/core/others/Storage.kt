package com.example.socialmedia.core.others

import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface Storage {

    suspend fun uploadImage(image: Uri) : String

}


class StorageByFireBase  @Inject constructor(
    private var storageRef:StorageReference,
    private var authenticator: Authenticator

    ) : Storage {

   override suspend fun uploadImage(image: Uri): String {
        val imageRef = storageRef.child("images/${authenticator.currentUserId()}")
       val result = imageRef.putFile(image).await()
       var uriTask = result.storage.downloadUrl
       while (!uriTask.isSuccessful);
       return uriTask.result.toString()
    }



}