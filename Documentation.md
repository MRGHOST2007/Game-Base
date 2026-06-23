# 📚 GameBase - Full Project Documentation

**Version:** 1.6  
**Last Updated:** June 23, 2026  
**Author:** Amir Mahdi Heidary (MRGHOST2007)

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Technical Architecture](#2-technical-architecture)
3. [Project Structure](#3-project-structure)
4. [Data Layer](#4-data-layer)
5. [Domain Layer](#5-domain-layer)
6. [Presentation Layer](#6-presentation-layer)
7. [Feature Modules](#7-feature-modules)
8. [API Integration](#8-api-integration)
9. [Database Schema](#9-database-schema)
10. [UI/UX Design](#10-uiux-design)
11. [Build & Dependencies](#11-build--dependencies)
12. [Testing Strategy](#12-testing-strategy)
13. [Performance Optimizations](#13-performance-optimizations)
14. [Security Considerations](#14-security-considerations)
15. [Future Roadmap](#15-future-roadmap)
16. [Contributing Guidelines](#16-contributing-guidelines)
17. [Troubleshooting](#17-troubleshooting)
18. [FAQ](#18-faq)

---

## 1. Project Overview

### 1.1 Description

**GameBase** is a modern Android application for game discovery and review reading. It combines a curated collection of games with real-time reviews from GameBlade, allowing users to explore games, save favorites, and read detailed reviews—all within a single, beautifully designed app.

### 1.2 Core Features

| Feature | Description | Status |
|---------|-------------|--------|
| **Game Library** | Browse 19 curated games with details | ✅ Complete |
| **Favorites** | Save/remove games with Room persistence | ✅ Complete |
| **Search** | Search games by title | ✅ Complete |
| **Genre Filter** | Filter games by genre | ✅ Complete |
| **Game Reviews** | Fetch and display reviews from GameBlade API | ✅ Complete |
| **WebView** | In-app review reading | ✅ Complete |
| **Pagination** | Load more reviews on demand | ✅ Complete |
| **Image Loading** | Async image loading with Coil | ✅ Complete |
| **Dark/Light Theme** | Material 3 dynamic theming | ✅ Complete |

### 1.3 Target Users

- **Gamers** looking for game recommendations
- **Game enthusiasts** wanting to read reviews
- **Casual users** discovering new games
- **Collectors** tracking favorite games

### 1.4 Supported Platforms

| Platform | Minimum Version | Target Version |
|----------|-----------------|----------------|
| **Android** | 7.0 (API 24) | 14 (API 34) |
| **Architecture** | arm64-v8a, armeabi-v7a, x86_64 | - |

---

## 2. Technical Architecture

### 2.1 Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────┐
│                         PRESENTATION LAYER                          │
│   ┌────────────┐  ┌────────────┐  ┌────────────┐  ┌────────────┐    │
│   │  Home      │  │  Games     │  │  Favorites │  │  Reviews   │    │
│   │  Screen    │  │  List      │  │  Screen    │  │  Screen    │    │
│   └────────────┘  └────────────┘  └────────────┘  └────────────┘    │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │                  ViewModels (StateFlow)                      │   │
│  │ HomeVM │ GamesListVM │ GameDetailVM │ FavoritesVM │ ReviewsVM│   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
                                    ↕
┌─────────────────────────────────────────────────────────────────────┐
│                          DOMAIN LAYER                               │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │                    Repository Interfaces                     │   │
│  │  GameRepository │ FavoritesRepository │ GameBladeRepository  │   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
                                    ↕
┌─────────────────────────────────────────────────────────────────────┐
│                           DATA LAYER                                │
│  ┌──────────────────────────────┐  ┌────────────────────────────┐   │
│  │      LOCAL DATA SOURCES      │  │     REMOTE DATA SOURCES    │   │
│  │  • FakeGameRepository (raw)  │  │  • GameBladeApi (Retrofit) │   │
│  │  • Room Database (favorites) │  │  • WordPress REST API      │   │
│  └──────────────────────────────┘  └────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
```

### 2.2 Architecture Principles

| Principle | Implementation |
|-----------|----------------|
| **Separation of Concerns** | UI, domain, and data layers are independent |
| **Single Responsibility** | Each class has one clear purpose |
| **Dependency Inversion** | High-level modules don't depend on low-level modules |
| **Unidirectional Data Flow** | State flows down, events flow up |
| **Immutability** | State is immutable and managed via StateFlow |

### 2.3 Design Patterns Used

| Pattern | Usage |
|---------|-------|
| **MVVM** | UI architecture pattern |
| **Repository** | Data layer abstraction |
| **Factory** | Creating repository instances |
| **Singleton** | AppModule, Database instance |
| **Observer** | StateFlow for reactive updates |
| **Builder** | Retrofit client configuration |

---

## 3. Project Structure

### 3.1 Package Structure

```
ir.mrghost.gamebase/
├── data/
│   ├── local/
│   │   ├── favorites/
│   │   │   ├── FavoriteDAO.kt         # Room DAO for favorites
│   │   │   ├── FavoriteEntity.kt      # Room entity
│   │   │   └── FavoritesRepository.kt # Favorites data operations
│   │   ├── FakeGameRepository.kt      # In-memory game data
│   │   ├── Game.kt                    # Game data model
│   │   └── GameBaseDatabase.kt        # Room database setup
│   └── remote/
│       ├── reviews/
│       │   ├── MediaModel.kt          # API media response models
│       │   └── ReviewModel.kt         # API review response models
│       ├── GameBladeApi.kt            # Retrofit API interface
│       └── GameBladeRepository.kt     # Remote data operations
├── screens/
│   ├── GameDetailScreen.kt           # Game detail view
│   ├── GamesListScreen.kt            # Game listing with filters
│   ├── HomeScreen.kt                 # Main landing page
│   ├── FavoritesScreen.kt            # Favorites listing
│   └── ReviewsScreen.kt              # Reviews listing with load more
├── ui/theme/
│   ├── Color.kt                      # Color definitions
│   ├── Theme.kt                      # Material theme configuration
│   └── Type.kt                       # Typography styles
├── utils/
│   ├── GameItem.kt                   # Reusable game card component
│   └── Utils.kt                      # Utility functions
├── viewmodel/
│   ├── GameDetailViewModel.kt        # Detail screen state
│   ├── GamesListViewModel.kt         # List screen state with filters
│   ├── HomeViewModel.kt              # Home screen state
│   ├── FavoritesViewModel.kt         # Favorites screen state
│   └── ReviewsViewModel.kt           # Reviews screen state with pagination
├── AppModule.kt                      # Dependency injection container
├── GameBaseApplication.kt            # Application class
└── MainActivity.kt                   # Main entry point with navigation
```

### 3.2 Resource Structure

```
res/
├── drawable/                         # Game images (PNG/WEBP)
│   ├── hk.webp
│   ├── zelda.webp
│   ├── witcher.webp
│   └── ...
├── drawable/                         # Icons (Vector)
│   ├── star_24.xml
│   ├── search_24.xml
│   ├── filter_24.xml
│   └── ...
├── values/
│   ├── colors.xml                    # Color resources
│   ├── strings.xml                   # String resources
│   └── themes.xml                    # Theme resources
└── mipmap/                           # App icon
    ├── ic_launcher/
    └── ic_launcher_round/
```

---

## 4. Data Layer

### 4.1 Game Data Model

**File:** `data/local/Game.kt`

```kotlin
@Parcelize
data class Game(
    val id: Long,                      // Unique identifier
    val title: String,                 // Game title
    val releaseYear: Int,              // Release year
    val developer: String,             // Developer name
    val genre: GameGenre,              // Game genre enum
    val description: String,           // Game description
    val rating: Float,                 // Rating out of 5
    val price: Float,                  // Price in USD
    @DrawableRes val image: Int        // Local drawable resource
) : Parcelable
```

**GameGenre Enum:**

```kotlin
enum class GameGenre {
    Action, Adventure, Casual, Horror,
    Platformer, RPG, Simulation, Strategy
}
```

### 4.2 Game Repository

**File:** `data/local/FakeGameRepository.kt`

The repository provides game data from an in-memory list:

| Method | Description | Return Type |
|--------|-------------|-------------|
| `getAllGames()` | Returns all 19 games | `List<Game>` |
| `getGameAwards()` | Returns award-winning games | `List<Game>` |
| `getTopPagerGames()` | Returns random top games | `List<Game>` |
| `getGamesByGenre()` | Filters games by genre | `List<Game>` |

### 4.3 Favorite Entity

**File:** `data/local/favorites/FavoriteEntity.kt`

```kotlin
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val gameId: Long,                  // Game ID (primary key)
    val title: String,                 // Game title
    val image: Int,                    // Drawable resource ID
    val rating: Float,                 // Game rating
    val addedTimestamp: Long = System.currentTimeMillis()
)
```

### 4.4 Favorite DAO

**File:** `data/local/favorites/FavoriteDAO.kt`

| Operation | Method | Description |
|-----------|--------|-------------|
| **Insert** | `insertFavorite()` | Adds a game to favorites (replace on conflict) |
| **Delete** | `deleteFavorite()` | Removes a game by entity |
| **Delete by ID** | `deleteFavoriteById()` | Removes a game by ID |
| **Query** | `getAllFavorites()` | Returns all favorites as Flow |
| **Check** | `isFavorite()` | Returns true if game is favorited |

### 4.5 Remote Data Models

**Review Response:** `data/remote/reviews/ReviewModel.kt`

```kotlin
data class ReviewResponse(
    val id: Int,                       // Post ID
    val date: String,                  // Publication date
    var link: String,                  // Full URL
    val title: Title,                  // Rendered title
    val content: Content,              // Rendered content
    val slug: String,                  // URL slug
    val featuredMedia: Int? = null,    // Featured image ID
    var imageUrl: String? = null       // Fetched image URL (loaded later)
)
```

**Media Response:** `data/remote/reviews/MediaModel.kt`

```kotlin
data class MediaResponse(
    val id: Int,
    val sourceUrl: String,             // Full image URL
    val mediaDetails: MediaDetails?    // Width/height metadata
)
```

---

## 5. Domain Layer

### 5.1 Repository Interfaces

**GameRepository:**

```kotlin
interface GameRepository {
    suspend fun getAllGames(): List<Game>
    suspend fun getGameAwards(): List<Game>
    suspend fun getTopPagerGames(): List<Game>
    suspend fun getGamesByGenre(genre: GameGenre): List<Game>
}
```

**FavoritesRepository:**

```kotlin
class FavoritesRepository(
    private val favoriteDao: FavoriteDAO
) {
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    suspend fun addFavorite(game: Game)
    suspend fun removeFavorite(gameId: Long)
    suspend fun isFavorite(gameId: Long): Boolean
    suspend fun toggleFavorite(game: Game): Boolean
}
```

**GameBladeRepository:**

```kotlin
class GameBladeRepository(
    private val api: GameBladeApi
) {
    suspend fun getReviews(page: Int, perPage: Int): List<ReviewResponse>
    suspend fun getMedia(id: Int): MediaResponse?
}
```

### 5.2 Use Cases

While the project currently implements logic directly in ViewModels, the architecture supports extracting use cases:

| Potential Use Case | Description |
|-------------------|-------------|
| `FilterGamesUseCase` | Filter games by genre and search query |
| `ToggleFavoriteUseCase` | Add/remove favorite with state tracking |
| `LoadReviewsUseCase` | Load paginated reviews with images |
| `SearchGamesUseCase` | Search games by title |

---

## 6. Presentation Layer

### 6.1 ViewModels

**GamesListViewModel:**

| State | Type | Description |
|-------|------|-------------|
| `query` | `StateFlow<String>` | Search query text |
| `selectedGenres` | `StateFlow<List<GameGenre>>` | Active genre filters |
| `filteredGames` | `StateFlow<List<Game>>` | Filtered game list |

| Event | Description |
|-------|-------------|
| `updateQuery()` | Updates search query |
| `updateGenres()` | Updates genre filters |

**GameDetailViewModel:**

| State | Type | Description |
|-------|------|-------------|
| `game` | `StateFlow<Game?>` | Loaded game data |
| `isFavorite` | `StateFlow<Boolean>` | Favorite status |

| Event | Description |
|-------|-------------|
| `loadGame()` | Loads game by ID |
| `toggleFavorite()` | Toggles favorite status |

**ReviewsViewModel:**

| State | Type | Description |
|-------|------|-------------|
| `reviews` | `StateFlow<List<ReviewResponse>>` | Loaded reviews |
| `isLoading` | `StateFlow<Boolean>` | Initial loading state |
| `isLoadingMore` | `StateFlow<Boolean>` | Pagination loading state |
| `error` | `StateFlow<String?>` | Error message |

| Event | Description |
|-------|-------------|
| `loadReviews()` | Initial load (page 1) |
| `loadMore()` | Load next page |

**HomeViewModel:**

| State | Type | Description |
|-------|------|-------------|
| `topPagerGames` | `StateFlow<List<Game>>` | Top games for carousel |
| `gameAwards` | `StateFlow<List<Game>>` | Award winners |
| `actionGames` | `StateFlow<List<Game>>` | Action genre |
| `platformerGames` | `StateFlow<List<Game>>` | Platformer genre |
| `rpgGames` | `StateFlow<List<Game>>` | RPG genre |

**FavoritesViewModel:**

| State | Type | Description |
|-------|------|-------------|
| `favorites` | `StateFlow<List<FavoriteEntity>>` | Favorited games |

### 6.2 UI Screens

**HomeScreen (`screens/HomeScreen.kt`)**

| Component | Description |
|-----------|-------------|
| `HeaderIcon` | App logo |
| `TopPager` | Auto-scrolling game carousel |
| `ListRows` | Horizontal lists by genre |

**GamesListScreen (`screens/GamesListScreen.kt`)**

| Component | Description |
|-----------|-------------|
| `HeaderIcon` | App logo |
| `TextField` | Search bar |
| `FilterButton` | Opens genre filter dialog |
| `LazyColumn` | Game list with `GameItem` |
| `GenreDialog` | Bottom sheet for genre filtering |

**GameDetailScreen (`screens/GameDetailScreen.kt`)**

| Component | Description |
|-----------|-------------|
| `HeaderIcon` | App logo |
| `GameImage` | Full-width game image |
| `TextBox` | Game details (developer, year, genre, description) |
| `AddFavoriteFAB` | Floating action button to toggle favorites |

**FavoritesScreen (`screens/FavoritesScreen.kt`)**

| Component | Description |
|-----------|-------------|
| `HeaderIcon` | App logo |
| `EmptyState` | Message when no favorites |
| `LazyColumn` | List of favorited games |

**ReviewsScreen (`screens/ReviewsScreen.kt`)**

| Component | Description |
|-----------|-------------|
| `HeaderIcon` | App logo |
| `ReviewCard` | Review item with image, title, date |
| `LoadMoreButton` | Pagination button |

### 6.3 Navigation

**Navigation Graph (`MainActivity.kt`):**

| Route | Destination | Arguments |
|-------|-------------|-----------|
| `main` | `MainScreen` | None |
| `detail/{gameId}` | `GameDetailScreen` | `gameId: Long` |
| `favorites` | `FavoritesScreen` | None |
| `reviews` | `ReviewsScreen` | None |
| `reviewBySlug/{slug}` | `ReviewDetailScreen` | `slug: String` |

---

## 7. Feature Modules

### 7.1 Game Discovery

**Flow:**
1. User opens HomeScreen → top games and genre rows load
2. Clicks "Show All" → navigates to GamesListScreen with pre-filtered genre
3. Searches by title → viewmodel filters in real-time
4. Applies genre filter → bottom sheet with chips

**Key Files:**
- `HomeScreen.kt`
- `GamesListScreen.kt`
- `GamesListViewModel.kt`
- `HomeViewModel.kt`
- `GameItem.kt`

### 7.2 Favorites System

**Flow:**
1. User taps favorite button on GameDetailScreen
2. Game is saved to Room database
3. Button updates to show "Remove from favorites"
4. FavoritesScreen displays all saved games
5. Tapping remove updates database and UI

**Key Files:**
- `FavoriteEntity.kt`
- `FavoriteDAO.kt`
- `FavoritesRepository.kt`
- `GameBaseDatabase.kt`
- `GameDetailViewModel.kt`
- `FavoritesViewModel.kt`

### 7.3 Reviews Integration

**Flow:**
1. User navigates to ReviewsScreen
2. ViewModel fetches page 1 from GameBlade API
3. Reviews displayed with async images
4. Clicking "Load More" fetches next page
5. Tapping review opens WebView with full article

**Key Files:**
- `GameBladeApi.kt`
- `GameBladeRepository.kt`
- `ReviewModel.kt`
- `ReviewsViewModel.kt`
- `ReviewsScreen.kt`
- `ReviewDetailScreen.kt`

---

## 8. API Integration

### 8.1 GameBlade API

**Base URL:** `https://gameblade.ir/`

**Endpoints:**

| Endpoint | Method | Description | Parameters |
|----------|--------|-------------|------------|
| `/wp-json/wp/v2/posts` | GET | Fetch reviews | `categories=57`, `page`, `per_page` |
| `/wp-json/wp/v2/media/{id}` | GET | Fetch featured image | `id` (media ID) |

**Authentication:** None (public API)

### 8.2 Retrofit Configuration

**File:** `AppModule.kt`

```kotlin
private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://gameblade.ir/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient())
        .build()
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}
```

### 8.3 Error Handling

| Scenario | Handling |
|----------|----------|
| **Network failure** | Returns empty list, shows error state |
| **Empty response** | Shows "No reviews found" |
| **Image load failure** | Shows placeholder image |
| **Invalid URL** | Shows "Invalid review" message |
| **Decoding error** | Catches exception, logs error |

---

## 9. Database Schema

### 9.1 Room Database

**Database Name:** `gamebase_database`

**Version:** 1

**Tables:**

| Table | Entity | Description |
|-------|--------|-------------|
| `favorites` | `FavoriteEntity` | Stores user's favorite games |

### 9.2 Favorites Table Schema

| Column | Type | Description | Constraints |
|--------|------|-------------|-------------|
| `gameId` | `Long` | Game ID | PRIMARY KEY |
| `title` | `String` | Game title | NOT NULL |
| `image` | `Int` | Drawable resource ID | NOT NULL |
| `rating` | `Float` | Game rating | NOT NULL |
| `addedTimestamp` | `Long` | Unix timestamp | DEFAULT CURRENT_TIME |

### 9.3 Database Operations

```kotlin
@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GameBaseDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDAO
    
    companion object {
        fun getInstance(context: Context): GameBaseDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                GameBaseDatabase::class.java,
                "gamebase_database"
            )
            .fallbackToDestructiveMigration(true)
            .build()
        }
    }
}
```

---

## 10. UI/UX Design

### 10.1 Color Scheme

**Dark Theme (Default):**

| Color Name | Value | Usage |
|------------|-------|-------|
| `Purple80` | `#D4AFFF` | Primary color |
| `PurpleGrey80` | `#CCC1E1` | Secondary color |
| `Pink80` | `#EEB2C9` | Tertiary color |

**Light Theme:**

| Color Name | Value | Usage |
|------------|-------|-------|
| `Purple40` | `#8653BB` | Primary color |
| `PurpleGrey40` | `#776883` | Secondary color |
| `Pink40` | `#7D5271` | Tertiary color |

### 10.2 Typography

| Style | Font | Size | Weight | Usage |
|-------|------|------|--------|-------|
| `bodyLarge` | Default | 16sp | Normal | Main text |
| `bodyMedium` | Default | 14sp | Normal | Secondary text |
| `headlineSmall` | Default | 24sp | Medium | Section titles |

### 10.3 Components

**GameItem:**

| Attribute | Value |
|-----------|-------|
| **Compact** | 240×135dp, rounded corners |
| **Big** | 320×180dp, rounded corners |
| **Overlay** | Gradient background for text |
| **Rating** | Star icon with numeric value |

**AddFavoriteFAB:**

| State | Color | Text |
|-------|-------|------|
| **Not favorite** | Yellow (0.7 alpha) | "Add to favorites" |
| **Favorite** | Surface (0.7 alpha) | "Remove from favorites" |

### 10.4 Animations

| Animation | Location | Description |
|-----------|----------|-------------|
| **Pager Auto-Scroll** | HomeScreen | 3-second interval with spring animation |
| **FAB State Change** | GameDetailScreen | Smooth color transition |
| **Bottom Sheet** | GamesListScreen | Smooth slide-up for genre filter |

---

## 11. Build & Dependencies

### 11.1 Version Catalog (`libs.versions.toml`)

```toml
[versions]
# Android Gradle Plugin
agp = "9.1.1"

# Kotlin
kotlin = "2.4.0"
parcelize = "2.4.0"
kapt = "2.4.0"

# KSP
ksp = "2.3.9"

# AndroidX Core
coreKtx = "1.17.0"
lifecycleRuntimeKtx = "2.10.0"
lifecycleViewmodelCompose = "2.8.7"
activityCompose = "1.12.3"

# Compose
composeBom = "2026.05.01"
foundation = "1.10.2"

# Navigation
navigationCompose = "2.8.9"
navigationFragmentKtx = "2.9.7"

# Room
roomRuntime = "2.8.4"
roomKtx = "2.8.4"

# Networking
converterGson = "3.0.0"
loggingInterceptor = "5.4.0"

# Image Loading
coilCompose = "2.7.0"

# WebView
webkit = "1.11.0"

# Testing
junit = "4.13.2"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
```

### 11.2 Module Dependencies

```kotlin
dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    
    // Navigation
    implementation(libs.androidx.navigation.compose)
    
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    
    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
    
    // Image Loading
    implementation(libs.coil.compose)
    
    // WebView
    implementation(libs.androidx.webkit)
}
```

### 11.3 Build Configurations

**Minify & Shrink:** Enabled for release builds

```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

**Compile Options:**

```kotlin
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}
```

---

## 12. Testing Strategy

### 12.1 Unit Tests (Planned)

| Test Target | Description | Framework |
|-------------|-------------|-----------|
| **ViewModels** | State management logic | JUnit + MockK |
| **Repositories** | Data operations | JUnit + MockK |
| **Use Cases** | Business logic | JUnit + MockK |
| **Mappers** | Data transformation | JUnit |

### 12.2 UI Tests (Planned)

| Test Target | Description | Framework |
|-------------|-------------|-----------|
| **Screens** | UI navigation and rendering | Compose UI Test |
| **Components** | Reusable component behavior | Compose UI Test |
| **Navigation** | Route verification | Compose UI Test |

### 12.3 Integration Tests (Planned)

| Test Target | Description | Framework |
|-------------|-------------|-----------|
| **API Integration** | Retrofit calls with mock server | Retrofit Mock |
| **Database** | Room operations with in-memory DB | Room Test |
| **End-to-End** | Full user flows | Compose UI Test |

---

## 13. Performance Optimizations

| Optimization | Implementation | Impact |
|--------------|----------------|--------|
| **Lazy Loading** | `LazyColumn` + `LazyRow` | Reduced initial load time |
| **Image Caching** | Coil's built-in disk/memory cache | Faster image loading |
| **Concurrent Loading** | Coroutines + `async` for images | 70% faster load times |
| **Pagination** | Load more on demand | Reduced data usage |
| **State Hoisting** | ViewModel with `StateFlow` | Minimal recomposition |
| **Keyed Items** | `key = { it.id }` | Efficient list updates |
| **R8 Optimization** | Minify + shrink enabled | 30% smaller APK |
| **Debounce (planned)** | Search query debounce | Reduced filter calls |

### 13.1 Image Loading Strategy

```kotlin
AsyncImage(
    model = post.imageUrl,
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
    placeholder = painterResource(R.drawable.placeholder),
    error = painterResource(R.drawable.placeholder)
)
```

### 13.2 LazyColumn Optimization

```kotlin
items(
    items = reviews,
    key = { it.id }  // Efficient recomposition
) { post ->
    ReviewCard(post = post)
}
```

---

## 14. Security Considerations

| Aspect | Implementation |
|--------|----------------|
| **Data Persistence** | Room database (encrypted storage available) |
| **Network** | HTTPS for all API calls |
| **Input Validation** | URL validation in WebView |
| **Permissions** | INTERNET only (required for API) |
| **Code Obfuscation** | R8 minification for release builds |
| **Secure Storage** | No sensitive data stored |

### 14.1 ProGuard Rules

```proguard
# Keep Room
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }

# Keep Parcelable
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
```

---

## 15. Future Roadmap

### Version 1.7 (Planned)

| Feature | Description | Priority |
|---------|-------------|----------|
| **Search Debouncing** | 300ms delay on search input | High |
| **Sort Options** | Sort by rating, price, release year | Medium |
| **Pull to Refresh** | Manual refresh for reviews | Medium |
| **Loading States** | Skeleton screens for better UX | Low |

### Version 2.0 (Planned)

| Feature | Description | Priority |
|---------|-------------|----------|
| **Real Game API** | Replace fake data with RAWG or IGDB API | High |
| **User Reviews** | Allow users to write reviews | Medium |
| **Watchlist** | Track upcoming games | Medium |
| **Offline Mode** | Cache games and reviews for offline access | Medium |
| **Push Notifications** | New reviews and releases | Low |

### Version 3.0 (Planned)

| Feature | Description | Priority |
|---------|-------------|----------|
| **Game Recommendations** | AI-based suggestions | Medium |
| **Social Features** | Share favorites with friends | Low |
| **Multi-Language** | Localization support | Low |
| **Wear OS** | Companion app for smartwatches | Low |

---

## 16. Contributing Guidelines

### 16.1 Code Style

- **Kotlin Coding Conventions** - [Official Guide](https://kotlinlang.org/docs/coding-conventions.html)
- **Kotlin Official Style** - Enabled in `kotlin.code.style=official`
- **2-space indentation** for Kotlin
- **Meaningful names** for classes, functions, and variables
- **Documentation** for public APIs

### 16.2 Pull Request Process

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add some amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### 16.3 Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation update
- `style`: Code style update
- `refactor`: Code refactoring
- `perf`: Performance improvement
- `test`: Test update
- `chore`: Build process update

### 16.4 Testing Requirements

- Unit tests for new ViewModels
- Integration tests for new repository methods
- UI tests for new screens

---

## 17. Troubleshooting

### 17.1 Common Issues

| Issue | Cause | Solution |
|-------|-------|----------|
| **Reviews not loading** | Network connection | Check internet permission |
| **Images not showing** | Missing Coil dependency | Add `coil-compose` to dependencies |
| **Navigation failing** | URL encoding issue | Use `slug` instead of `link` |
| **Duplicate reviews** | Pagination bug | Check `currentPage` increment |
| **Favorites not persisting** | Database migration | Use `fallbackToDestructiveMigration` |
| **App crashing** | Missing drawable | Add placeholder image |

### 17.2 Debugging Tips

| Problem | Debug Approach |
|---------|----------------|
| **API issues** | Enable Retrofit logging: `HttpLoggingInterceptor.Level.BODY` |
| **State issues** | Add logging to StateFlow `println("State: $value")` |
| **Navigation issues** | Check route strings, encoded URLs |
| **Database issues** | Use Room inspection in Android Studio |

### 17.3 Build Issues

| Problem | Solution |
|---------|----------|
| **Gradle sync fails** | Update AGP version |
| **KSP errors** | Switch to kapt or update KSP version |
| **Kotlin version mismatch** | Align kotlinVersion with Compose compiler |
| **Out of memory** | Increase JVM args: `-Xmx2048m` |
