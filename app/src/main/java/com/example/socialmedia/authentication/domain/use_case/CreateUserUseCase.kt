package com.example.socialmedia.authentication.domain.use_case

import android.net.Uri
import com.example.socialmedia.authentication.domain.repository.RepositoryAuth
import com.example.socialmedia.core.models.User
import com.example.socialmedia.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CreateUserUseCase  @Inject constructor(
    private val repository: RepositoryAuth
) {

    operator fun invoke(
        email: String,
        password: String,
        name: String,
        bio: String,
        image: Uri
    ): Flow<Resource<Boolean,String>> = flow {
        emit(Resource.Loading())
        try {
           val result= repository.createUser(email, password)
           var userId=result.user?.uid

           val image =repository.uploadUserPictureOnFireStorage(image)

            var user = User(
                    id = userId!!,
                    name=name,
                    email= email,
                    bio=bio,
                    image=image,
                    cover = "",
                    token = "",
                    status = ""
                )

            repository.setUserDataInfoOnDatabase(user)

            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString(), false))
        }

    }

}