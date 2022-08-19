package com.example.socialmedia.main.domain.use_case

import com.example.socialmedia.core.utils.Resource
import com.example.socialmedia.main.data.models.Post
import com.example.socialmedia.main.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: Repository
){

    operator fun invoke():Flow<Resource<List<Post>, String>> =flow {

        emit(Resource.Loading())

        try {
            val posts = repository.getPosts()
            emit(Resource.Success(posts))
        } catch (e: Exception) {

            emit(Resource.Error(e.message.toString()))
        }


    }
}