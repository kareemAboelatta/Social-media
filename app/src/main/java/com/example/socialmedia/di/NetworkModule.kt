package com.example.socialmedia.di


import com.example.socialmedia.core.others.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideDatabaseReference() = FirebaseDatabase.getInstance().reference

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()


    @Singleton
    @Provides
    fun provideFirebaseStorage() = FirebaseStorage.getInstance().reference


    @Singleton
    @Provides
    fun provideAuthenticator(auth: FirebaseAuth): Authenticator = FirebaseAuthenticator(auth)

    @Singleton
    @Provides
    fun provideStorage(
        storage: StorageReference,
        authenticator: Authenticator
    ): Storage = StorageByFireBase(storage, authenticator)



    @Singleton
    @Provides
    fun provideDatabase(
        databaseRef: DatabaseReference,
    ): Database = DatabaseFromFirebase(databaseRef)


/*
    @Singleton
    @Provides
    fun provideFirebaseMessaging() = FirebaseMessaging.getInstance()




    @Singleton
    @Provides
    fun provideMovieService(): NotificationApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(NotificationApi::class.java)
    }
*/


}