# Social-media
Is a new version of code for my [Social media app](https://github.com/kareemAboelatta/social-media-app) with Clean Architecture. 
I used most of Clean code tips with android, SOLID principles and design-patterns..

# :heart: Clean Architecture :heart:
- I have written about how to architect android application using the Uncle Bob's clean architecture approach. and what's this architecture and why we should use an architecture [here](https://github.com/kareemAboelatta/Clean-architecture).
- **And this an old project but i made it again with Uncle Bob's clean architecture approach Let's go and see what's the new here.**

## <img src="https://media.giphy.com/media/5WILqPq29TyIkVCSej/giphy.gif" width="50"> Clean Architecture maximizes the use of SOLID principles and we used all of them let's see :runner: : 
## :star: Single Responsibility
Each software component should have only one reason to change – one responsibility.
So whatever **class** you have or whatever **function** you have these functions and classes should always only have one **single responsibility** and **one reason** to change.
> for example getPosts function ! 🤩


```kotlin
class RepositoryImp @Inject constructor(
    private var database: Database
) : Repository {
    // I have one responsibility and it's just get post from database and return list of posts
    override suspend fun getPosts(): List<Post> = database.getAllPosts()
    
    override fun getUser(): User {
        TODO("Not yet implemented")
    }
}
```

## :star: Open-closed
- You should be able to extend the behavior of a component, without breaking its usage, or modifying its extensions.
- For example in this project we have a **Database interface** and **DatabaseFromFirebase class** which is extend or implement **Database interface** methods. **Now** if we want to modify something or add a function for example we can do in (**Child**):baby: class.
- Now you **opened** your *Database interface* **to extention**, anyone wants to add or modify something he will extend your *Database interface* and add what he want in his own child class :baby: (*DatabaseFromFirebase class*). and your class is **closed to modification**.

```kotlin
interface Database {
    suspend fun setUserDataInfoOnDatabase(user: User): Task<Void>
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
    override suspend fun setUserDataInfoOnDatabase(user: User): Task<Void> {
        return databaseRef.child("users").child(user.id).setValue(user)
    }

    override suspend fun getCurrentUserData(id: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }
}

```





## :star: Liskov Substitution 
If you have a class of one type, and any subclasses of that class, you should be able to represent the base class usage with the subclass, without breaking the app.
- We do that [here](https://github.com/kareemAboelatta/Social-media/blob/d03bc0e318f3d0787569c5e16608346c774bf80c/app/src/main/java/com/example/socialmedia/di/RepositoryModule.kt#L64) when we inject RepositoryImp() from RepositoryImp (**Child**) :baby: Type whith provideMainRepository function which is return type of it Repository(**Parent**):man:.

```kotlin
    @Singleton
    @Provides
    fun provideMainRepository(
        database: Database 
    ): Repository = RepositoryImp(database) //function return type is Repository 'Parent' and this able to return RepositoryImp instead  

```
- Now the parent class(Repository) :man: is  replaceable by their subclasses (RepositoryImp) :baby::baby: and that without altering the behavior so that again

## :star: Interface Segregation
- It’s better to have many smaller interfaces than a large one, to prevent the class from implementing the methods that it actually doesn’t need.
- Don't force him :muscle: to implement it :joy:  
- Note we can do that by making a default body for this method in `Kotlin` like this:
```kotlin
interface RepositoryAuth {
     fun notImportantForAll(){}
}
```
</br>
## :star: Dependency Inversion
Components should depend on abstractions rather than concrete implementations. Also higher level modules shouldn’t depend on lower level modules.
- See This example :
```kotlin
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

```
- Now We can depended on **abstractions (Database interface)** and not on **concretions (like Firebase or Custom Api)** by this way. The Datebase interface now is a **replaceable** with its childern classes and we will make our reposiory class take Database interface as argument like this: :heart:
```kotlin
class RepositoryImp @Inject constructor(
    private var database: Database                      //abstractions (firebase or your custom api)
   // private var refDatabase: DatabaseReference       // concretions (just for firebase)
) : Repository {
    
    override fun getUser(): User {
        TODO("Not yet implemented")
    }
    
    override suspend fun getPosts(): List<Post> = database.getAllPosts()
    
}
```
- Now it's to easy if we want to *convert* from firebase to custom api and vice versa *because our repository don't now what's firebase or what's api*, Because it's take **atractions** not **concretions** .

