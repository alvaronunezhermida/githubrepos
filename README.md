# Github Repositories - Clean Architecture and MVVM
This project probably doesn't need this overengineering, it is just an example of clean architecture and MVVM pattern written in kotlin. It is a simple app that shows a list of github repositories and their details. The data is retrieved from the [Github API](https://api.github.com/).

[**Project Repository**](https://github.com/alvaronunezhermida/githubrepos)

## Screenshots

<div style="display: flex;">
    <img src="README_FILES/repos-screen.jpg" alt="Repos Screen" style="width: 35%; height: auto;">
    <img src="README_FILES/repo-detail-screen.jpg" alt="Repo Detail Screen" style="width: 35%; height: auto;">
</div>

## Clean Architecture Layers

![Alt text](README_FILES/clean-architecture-own-layers.png?raw=true "Clean Architecture Layers")

## Layers
* **Presentation Layer (MVVM)**: Contains all the Android UI related code (activities, fragments, views) and view models.
* **Use Cases Layer**: These are mainly the actions that the user can trigger. They are called by the presentation layer and they will execute the business logic.
* **Domain Layer**: These are the rules of your business.
* **Data Layer (Repositoy Pattern)**: Contains the abstract definition of the different data sources, and how they should be used.
* **Framework Layer**: Contains the implementation of the different data sources.

### Interaction between layers
![Alt text](README_FILES/clean-architecture-interaction.png?raw=true "Layer interaction")

## Architecture concepts used here
* [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
* [Repository Pattern](https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern#3)
* [MVVM](https://medium.com/@ami0275/mvvm-clean-architecture-pattern-in-android-with-use-cases-eff7edc2ef76)
* [Dependency Injection](https://developer.android.com/training/dependency-injection)
* [Unidirectional Data Flow](https://developer.android.com/jetpack/compose/architecture#:~:text=A%20unidirectional%20data%20flow%20(UDF,that%20store%20and%20change%20state))

# Includes
* **XML** and **View Binding** were used to implement the UI
* **Room** was used to cache the **Repos List**
* **Flow** was used to retrieve the data from the **Repository** and to update the ui through the **ViewModel**
* **Libs Dependencies** are managed in the **buildSrc** folder
* **Error Handling** is done through **Either**

# Dependencies
* **Retrofit**: HTTP client
* **Room**: Local database
* **Flow**: Asynchronous data stream 
* **Navigation Component**: Navigation between screens
* **View Binding**: View binding
* **Coroutines**: Asynchronous programming
* **Dagger Hilt**: Dependency Injection
* **Moshi**: JSON parser for Kotlin
* **Coil**: Image Loading System
* **Mockito**: Mocking framework for unit tests
* **Espresso**: UI testing framework

## TO DO:
### Improvements
- Add more tests
- Add more documentation and generate code documentation with dokka
- Add github login for get the authorization token through the github web flow authentication
- Add pagination
- Add a CI to the repository
- Cache all the repositories data