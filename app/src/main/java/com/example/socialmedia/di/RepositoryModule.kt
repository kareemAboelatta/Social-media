package com.example.socialmedia.di


import android.content.Context
import com.example.socialmedia.authentication.data.repository.RepositoryAuthImp
import com.example.socialmedia.authentication.domain.repository.RepositoryAuth
import com.example.socialmedia.core.others.Authenticator
import com.example.socialmedia.core.others.Database
import com.example.socialmedia.core.others.Storage
import com.example.socialmedia.main.data.models.repository.RepositoryImp
import com.example.socialmedia.main.domain.Repository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

/*
    @Singleton
    @Provides
    fun provideMainRepository(
        refDatabase: DatabaseReference,
        refStorage: StorageReference,
        auth: FirebaseAuth,
        firebaseMessaging: FirebaseMessaging,
        @ApplicationContext
        context: Context
    )= Repository(refDatabase,refStorage,auth,firebaseMessaging,context)


    @Singleton
    @Provides
    fun provideMessengerRepository(
        refDatabase: DatabaseReference,
        auth: FirebaseAuth,
        @ApplicationContext
        context: Context
    )= RepositoryMessenger(refDatabase,auth,context)
*/



    @Singleton
    @Provides
    fun provideUserRepository(
        database: Database,
        storage: Storage,
        auth: Authenticator,
        @ApplicationContext
        context: Context
    ): RepositoryAuth =
        RepositoryAuthImp(database, storage, auth)





    @Singleton
    @Provides
    fun provideMainRepository(
        database: Database
    ): Repository = RepositoryImp(database)






}