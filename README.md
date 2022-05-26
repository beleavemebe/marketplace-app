<p align = "center">    
  <img src="https://user-images.githubusercontent.com/79194789/166696631-9d4f044f-e26b-4679-a06f-07b877bc23ad.png" />
</p>
<h1>NCS Shop</h1>
<p><b>NCS Shop</b> is built with modern Android development tools. Aim of this project is to showcase good practice implementation of Android application development with reliable architecture</p>
<!-- <p><i><b>You can install and test latest NCS app from below</b></i></p> -->
<!-- <a href="link to artefact"> <image src="link to image"> </a>   temporary only image-->
<!-- <image src="https://user-images.githubusercontent.com/66072196/166214930-eb9b2a60-9f37-470e-8c4a-7f5b11959207.svg"/> -->
<h2>Features</h2>
The codebase focuses on following key things:
<br></br>

- [x] Single Activity Design
- [x] Multi-module architecture
- [x] Jetpack Compose/XML UI
- [x] Unit Tests
- [x] Dark theme
- [x] Notifications
<h2>Design</h2>

![Readme preview](https://user-images.githubusercontent.com/79194789/170563519-16b9de53-b47e-4f17-8b99-cd71f9ce8092.png)

Click below to see full design system of this app
<br></br>
 <a href="https://www.figma.com/file/KsQuIeHl9aypcJUHLYnAWU/marketplace?node-id=0%3A1">
 <img src="https://user-images.githubusercontent.com/66072196/166246753-662d7a8c-cab7-4ff8-84d1-4c4dd525da53.svg"></a>

<h2>Architecture</h2>
<p>This app follows principles of Clean Architecture, and therefore is separated into three layers. Data and Presentation layers are additionally modularized, with Data layer divided by data source and Presentation layer being multi-module by feature. </p>

![App Architecture](https://user-images.githubusercontent.com/79194789/166696151-58c6fd9c-1812-4822-afe0-311495946315.png)

<h3>Domain layer</h3>

 - Contains all of the core entity classes, upon which the rest of the app is built
 - Defines interfaces of repositories
 - Encapsulates business logic inside Use Case classes

<h3>Data layer</h3>

 - Implements repositories from the domain layer
 - Fetches content from the <a href="https://github.com/beleavemebe/marketplace-api">API</a>
 - Stores visited products and search history in Room database
 - Uses Firebase to manage authentication and obtain data of the current user

<h3>Presentation layer</h3>
<h4>App module</h4>

- Depends on every other module to initialize DI
- Contains the application class as well as the main activity
- Controls the navigation

<h4>UI feature modules</h4>

Each module is responsible for one logical feature of the app. All features implement <a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel#:~:text=Model%E2%80%93view%E2%80%93viewmodel%20(MVVM,is%20not%20dependent%20on%20any">MVVM</a> architecture, with the exception of User feature, which uses <a href="https://medium.com/quality-content/mvi-a-reactive-architecture-pattern-45c6f5096ab7#:~:text=In%20MVI%2C%20models%20are%20formalized,might%20have%20its%20own%20state.">MVI</a> approach to ease interaction with Compose

<h4>Core module</h4>

- Contains shared resources and utils for all UI feature modules
- Defines the navigation contract of this app, having `MarketplaceNavigator` interface as well as every `NavDestintaion` supported

<h2>Built with</h2>

- <p><a href="https://kotlinlang.org/">Kotlin</a> - First class and official programming language for Android development.</p>
- <p><a href="https://kotlinlang.org/docs/reference/coroutines-overview.html">Coroutines</a> - For asynchronous and more..</p>
- <p><a href="https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/">Flow</a> - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.</p>
- <p><a href="https://developer.android.com/topic/libraries/architecture">Android Architecture Components</a> - Collection of libraries that helps design robust, testable, and maintainable apps.</p>
- <p><a href="https://developer.android.com/topic/libraries/architecture/viewmodel">ViewModel</a> - Stores UI-related data that survives configuration changes.</p>
- <p><a href="https://developer.android.com/topic/libraries/view-binding">ViewBinding</a> - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.</p>
- <p><a href="https://developer.android.com/jetpack/androidx/releases/room">Room</a> - An object mapping library powered by SQLite.</p>
- <p><a href="https://developer.android.com/topic/libraries/architecture/workmanager">WorkManager</a> - Makes it easy to schedule deferrable, asynchronous tasks that are expected to run even if the app exits or the device restarts.</p>
- <p><a href="https://developer.android.com/guide/navigation/navigation-getting-started">Navigation Component</a> - eases navigating across, into, and back out from the different pieces of content within your app.</p>
- <p><a href="https://insert-koin.io/">Koin</a> - A pragmatic lightweight dependency injection framework for Kotlin developers. Koin is a DSL, a light container and a pragmatic API</p>
- <p><a href="https://square.github.io/retrofit/">Retrofit</a> - A type-safe HTTP client for Android and Java</p>
- <p><a href="https://github.com/material-components/material-components-android">Material Components for Android</a> - Modular and customizable Material Design UI components for Android.</p>
- <p><a href="https://junit.org/junit4/">JUnit</a> - Unit testing framework for the Java/Kotlin</p>
- <p><a href="https://developer.android.com/jetpack/compose">Jetpack Compose</a> - Modern UI development toolkit.</p>
- <p><a href="https://mockk.io/">Mockk</a> - Mocking library for Kotlin.</p>
- <p><a href="https://github.com/coil-kt/coil">Coil</a> - An image loading library for Android backed by Kotlin Coroutines.</p>
- <p><a href="https://github.com/orbit-mvi/orbit-mvi">Orbit</a> - A redux/MVI-like library - but without the baggage.</p>
- <p><a href="https://github.com/airbnb/lottie-android">Lottie</a> - Natively renders Adobe After Effects exported animations</p>
- <p><a href="https://firebase.google.com/docs">Firebase</a> - Stores data in the cloud and provides tools for tracking analytics, reporting and fixing app crashes, creating marketing and product experiment.
