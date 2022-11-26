# CoinMarketCap - Clean architecture
  A simple project to display a list of cryptocurrencies built with MVVM and Clean Architecture

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Navigation](https://developer.android.com/guide/navigation/) - The Navigation component is designed for apps that have one main activity with multiple fragment destinations
- [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection Framework
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager?gclid=CjwKCAiA7IGcBhA8EiwAFfUDscJxzgqofa1Kd06_afL2T8lBVKVVWvVO0A9QZfI2q2wm0Gb6RtuSNBoCsvsQAvD_BwE&gclsrc=aw.ds) - WorkManager is the recommended solution for persistent work
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [OkHttp3](https://github.com/square/okhttp) - implementing interceptor, logging and mocking web server
- [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Picasso](https://square.github.io/picasso/) - A powerful image downloading and caching library for Android.
- [Mockk ](https://mockk.io/) - Mockk is a mocking framework built for Kotlin.

# Architecture
	Architecture is a system design that breaks down components into individual parts. The goal of clean architecture is to find better software like maintainable, testable, easy to use, scalable, and much more — an accurate guide for engineers.
    The architecture proposed consist of 3 different layers:
    
    # Root Package
    .
    
    ├── app                 #Contains all the Android UI framework components (e.g. Activities, Fragments, ViewModels...) and the related resources (e.g. images, strings...).
    │   ├── ui                This module has access to all modules But it uses the domain module to communicate with the data module
    |    
    |   
    |
    ├── domain              #Contains the platform-independent business logic and Domain models.
    |   │ 		         This module cannot access any other module    
    |   |
    |   ├── di                
    |   ├── entities           
    |   ├── usecase                        
    |    
    │ 
    |
    ├── data                #Contains the repositories, Database, the data sources api implementations and the corresponding api-specific models.
	|   │ 		         This module can only access domain module
    │   ├── di              
    │   ├── db 
    │   ├── datasource	
    │   ├── mapper	
    │   ├── model
    │   ├── repository		
    │   ├── source		
    │   ├── typedef	
    │   ├── worker	
    │   ├── utils	
    
	
# Offline-first	
  An offline-first app is an app that is able to perform all, or a critical subset of its core functionality without access to the internet. That is, it can perform some or all of its business logic offline.
  In this app, data is received only through the database. If the data is not found in the database, the database is updated by calling api and receiving data from the server. In offline mode, as soon as the user connects to the Internet, the database receives the latest data through a work manager.

# Testing     
* In the data layer the database, repository and mappers have been tested
* MockK has been used for mocking.


#
