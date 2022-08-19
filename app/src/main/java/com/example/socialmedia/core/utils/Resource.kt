package com.example.socialmedia.core.utils

sealed class Resource<out T, out F>(val data : T? = null , val message : F? = null) {
    class Success<T>(data : T) : Resource<T, Nothing>(data)
    class Error<T,F>(message : F?, data : T? = null) : Resource<T, F>(data , message)
    class Loading<T>(data : T? = null) : Resource<T, Nothing>(data)
    class Empty<T>(data : T? = null) : Resource<T, Nothing>(data)

}
