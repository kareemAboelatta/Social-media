package com.example.socialmedia.main.presentaion


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia.core.utils.Resource
import com.example.socialmedia.main.data.models.Post
import com.example.socialmedia.main.domain.use_case.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MainViewModel @Inject constructor(
    val getPostsUseCase: GetPostsUseCase
) :ViewModel(){

    private var _postsState= MutableStateFlow<Resource<List<Post>,String>>(Resource.Empty())
    val postsState: StateFlow<Resource<List<Post>, String>> = _postsState


    fun getPosts() {
        getPostsUseCase().onEach {
            when (it) {
                is Resource.Loading -> _postsState.value = Resource.Loading()
                is Resource.Success -> _postsState.value = Resource.Success(it.data!!)
                is Resource.Error -> _postsState.value = Resource.Error(it.message)
            }

        }.launchIn(viewModelScope)
    }

}