# Social-media
Is a new version of code for my [Social media app](https://github.com/kareemAboelatta/social-media-app) with Clean Architecture. 
I used most of Clean code tips with android, SOLID principles and design-patterns..

## Clean Architecture 
- I have written about how to architect android application using the Uncle Bob's clean architecture approach. and what's this architecture and why we should use an architecture [here](https://github.com/kareemAboelatta/Clean-architecture).
- **And this an old project but i made it again with Uncle Bob's clean architecture approach Let's go and see what's the new here.**

# Clean Architecture maximizes the use of these SOLID principles: 
## Single Responsibility
Each software component should have only one reason to change – one responsibility.
So whatever **class** you have or whatever **function** you have these functions and classes should always only have one **single responsibility** and one reason to change.

## Open-closed:
- You should be able to extend the behavior of a component, without breaking its usage, or modifying its extensions.
- For example in the project we have a [**Repository interface**](https://github.com/kareemAboelatta/Social-media/blob/master/app/src/main/java/com/example/socialmedia/main/domain/Repository.kt) and [**RepositoryImp class**](https://github.com/kareemAboelatta/Social-media/blob/master/app/src/main/java/com/example/socialmedia/main/data/models/repository/RepositoryImp.kt) which is extend or implement           **Repository interface** methods **now** if we want to modify something or add a function for example.
- Now you **opened** your *Repository** to **extention**, anyone wants to add or modify something he will extend your *Repository* and add what he want in his own child class (*RepositoryImp class*). and your class is **closed to modification**.




