# Advanced Clean Architecture for Social Media App

Embark on a journey through refined code as we revisit the [Social Media app](https://github.com/kareemAboelatta/social-media-app), now re-architected with the robustness of Clean Architecture and the timelessness of SOLID principles.

## Leveraging Clean Architecture to Uphold SOLID Principles

Clean Architecture isn't just a structural blueprint for our apps; it's a commitment to quality, scalability, and maintainability. By intertwining Clean Architecture with SOLID principles, we've crafted a codebase that is resilient to change and open to extension.

### :bookmark_tabs: Single Responsibility Principle (SRP)

Our components are laser-focused, each with a singular purpose. Take a glimpse into our `RepositoryImp` class, a testament to SRP:

```kotlin
class RepositoryImp @Inject constructor(
    private var database: Database
) : Repository {
    override suspend fun getPosts(): List<Post> = database.getAllPosts()
    override fun getUser(): User { /* Simplified for brevity */ }
}
```

Here, each function has one responsibility: fetching posts and user data, respectively.

###  :hammer: :wrench: Open/Closed Principle (OCP)
We build for the future, ensuring our components are extendable without modification. Our database interface is a perfect example:

```kotlin
interface Database {
    suspend fun getAllPosts(): List<Post>
    // Other abstract methods
}

class DatabaseFromFirebase @Inject constructor(
    private var databaseRef: DatabaseReference
) : Database {
    override suspend fun getAllPosts(): List<Post> {
        // Implementation details...
    }
}
```

Extensions are made in child classes like DatabaseFromFirebase, keeping the parent interface intact.

### :recycle: Liskov Substitution Principle (LSP)
Our architecture allows for base classes to be substituted with their derived classes seamlessly:
```kotlin
@Singleton
@Provides
fun provideMainRepository(
    database: Database 
): Repository = RepositoryImp(database)

```

The RepositoryImp can replace its parent Repository type without altering the expected behavior.

### :ok_woman:  Interface Segregation Principle (ISP)
We embrace focused interfaces over monolithic ones. Our RepositoryAuth interface is an epitome of this, where optional implementations are provided with default methods:

```kotlin
interface RepositoryAuth {
    fun notImportantForAll() {}
    // Essential methods...
}

```

This prevents classes from being forced to implement methods that they don't need.

### :electric_plug: Dependency Inversion Principle (DIP)
Our components depend on abstractions, not on concrete implementations, allowing for flexible data sources:
```kotlin
class RepositoryImp @Inject constructor(
    private var database: Database
) : Repository {
    // Utilizes Database abstraction, not a specific implementation
}

```

This design allows us to switch from Firebase to a custom API with minimal friction.

### :sparkles: Wrapping Up
We've gone the extra mile to ensure each SOLID principle is not just met but exemplified within our Clean Architecture implementation. From SRP to DIP, our code demonstrates the harmony between architecture and design principles, resulting in a codebase that's both elegant and robust.

### :star: Support and Contributions
Each star :star: on our repository fuels our passion for sharing knowledge and developing open-source projects. Your support encourages more articles and continuous improvement.

We invite you to delve into the code, contribute, and join us in refining the art of Android development.




