# 🎮 GameBase

**A modern Android game discovery app built with Jetpack Compose.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-2026.05.01-brightgreen.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Room](https://img.shields.io/badge/Room-2.8.4-orange.svg?style=flat&logo=sqlite)](https://developer.android.com/jetpack/androidx/releases/room)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)

---

## 📱 About

**GameBase** is a clean, modern Android application that serves as a comprehensive game discovery platform. Browse a curated collection of games, read expert reviews, and save your favorites – all in one place. Built with **Kotlin** and **Jetpack Compose**, it showcases modern Android development best practices.

### ✨ Key Features

| Feature | Description |
|---------|-------------|
| **📚 Game Library** | Browse a rich collection of games with details like title, genre, rating, developer, and description |
| **❤️ Favorites** | Save games to your personal favorites list, stored locally using **Room** database for persistence |
| **🔍 Search & Filter** | Easily find games by name or filter them by genre (Action, RPG, Strategy, etc.) |
| **📰 Game Reviews** | Read the latest game reviews from **GameBlade**, fetched via the WordPress REST API |
| **🌐 In-App WebView** | Read full review articles without leaving the app with a seamless browsing experience |
| **🏠 Home Screen** | Quick access to top games, latest award winners, and curated lists by genre |
| **🌙 Dynamic Theme** | Supports Material 3 dynamic theming (Android 12+) |

---

## 🏗️ Architecture

This project follows a modern, scalable architecture based on **Clean Architecture** principles:

```
┌─────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                     │
│              (Compose UI + ViewModels + StateFlow)          │
│  • Screens(Home, GamesList, GameDetail, Favorites, Reviews) │
│  • ViewModels with StateFlow for reactive UI                │
└─────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────┐
│                       DOMAIN LAYER                          │
│                    (Repository Interfaces)                  │
│  • GameRepository (Games data source)                       │
│  • FavoritesRepository (Local favorites)                    │
└─────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────┐
│                       DATA LAYER                            │
│  • Local: Room Database (Favorites) + FakeGameRepository    │
│  • Remote: Retrofit + GameBlade API (Reviews)               │
└─────────────────────────────────────────────────────────────┘
```

**Key architectural decisions:**
- **MVVM** pattern for separation of concerns
- **Repository pattern** for data abstraction
- **StateFlow** for reactive state management
- **Manual DI** via `AppModule` (scalable to Dagger/Hilt)

---

## 📚 Documentation

For complete technical documentation, including architecture, API integration, database schema, and development guide, see:

**[📖 Full Documentation (DOCUMENTATION.md)](Documentation.md)**

---

## 🛠️ Tech Stack

### Core
| Technology | Version | Purpose |
|------------|---------|---------|
| **Kotlin** | 2.4.0 | Primary language |
| **Jetpack Compose** | 2026.05.01 | UI framework |
| **Android Gradle Plugin** | 9.1.1 | Build system |
| **Min SDK** | 24 (Android 7.0) | Minimum supported Android version |
| **Target SDK** | 36 (Android 14) | Target Android version |

### Libraries
| Library | Version | Purpose |
|---------|---------|---------|
| **Room** | 2.8.4 | Local database for favorites |
| **Retrofit** | 2.11.0 | HTTP client for API calls |
| **Coil** | 2.7.0 | Image loading from URLs |
| **Navigation Compose** | 2.8.9 | Type-safe navigation |
| **Gson** | 3.0.0 | JSON serialization/deserialization |
| **OkHttp Logging** | 4.12.0 | Network logging for debugging |

### Architecture Components
- **Lifecycle ViewModel** – `2.8.7`
- **Parcelize** – Kotlin Android Extensions

---

### Environment Configuration

No API keys are required for the current setup. The app uses:
- **Local fake data** for games
- **Room database** for favorites
- **GameBlade public API** for reviews (no authentication)

---

## 📂 Project Structure

```
Game-Base/
├── app/
│   └── src/main/java/ir/mrghost/gamebase/
│       ├── data/
│       │   ├── local/                    # Local data sources
│       │   │   ├── favorites/            # Favorites (Room)
│       │   │   │   ├── FavoriteDAO.kt
│       │   │   │   ├── FavoriteEntity.kt
│       │   │   │   └── FavoritesRepository.kt
│       │   │   ├── FakeGameRepository.kt # Fake game data
│       │   │   ├── Game.kt               # Game data class
│       │   │   └── GameBaseDatabase.kt   # Room database
│       │   └── remote/                   # Remote data sources
│       │       ├── reviews/              # API models
│       │       │   ├── MediaModel.kt
│       │       │   └── ReviewModel.kt
│       │       ├── GameBladeApi.kt       # Retrofit interface
│       │       └── GameBladeRepository.kt
│       ├── screens/                      # Compose UI Screens
│       │   ├── GameDetailScreen.kt
│       │   ├── GamesListScreen.kt
│       │   ├── HomeScreen.kt
│       │   ├── FavoritesScreen.kt
│       │   └── ReviewsScreen.kt
│       ├── ui/theme/                     # Material Theme
│       │   ├── Color.kt
│       │   ├── Theme.kt
│       │   └── Type.kt
│       ├── utils/                        # Utilities
│       │   ├── GameItem.kt
│       │   └── Utils.kt
│       ├── viewmodel/                    # ViewModels
│       │   ├── GameDetailViewModel.kt
│       │   ├── GamesListViewModel.kt
│       │   ├── HomeViewModel.kt
│       │   ├── ReviewsViewModel.kt
│       │   └── FavoritesViewModel.kt
│       ├── AppModule.kt                  # Dependency Injection
│       ├── GameBaseApplication.kt        # Application class
│       └── MainActivity.kt               # Main entry point
├── gradle/                               # Gradle wrapper
├── build.gradle.kts                      # Project-level build
├── settings.gradle.kts                   # Project settings
├── gradle.properties                     # Gradle properties
├── .gitignore                            # Git ignore rules
└── README.md                             # This file
```

---

## 🔧 Customization

### Adding New Games

To add new games, edit the `gameList` in `FakeGameRepository.kt`:

```kotlin
Game(
    id = 20,
    title = "Your Game Title",
    releaseYear = 2024,
    developer = "Developer Name",
    genre = GameGenre.Action,
    description = "Game description here...",
    rating = 4.8f,
    price = 59.99f,
    image = R.drawable.your_image
)
```

> **Note:** Make sure to add your game image to `res/drawable/` and update the `id` to be unique.

---

## 🔌 API Integration

### GameBlade API Endpoints

| Endpoint | Description | Parameters |
|----------|-------------|------------|
| `wp-json/wp/v2/posts` | Fetch game reviews | `categories=57`, `page`, `per_page` |
| `wp-json/wp/v2/media/{id}` | Fetch featured image | `id` (media ID) |

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch:
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit** your changes:
   ```bash
   git commit -m 'Add some amazing feature'
   ```
4. **Push** to the branch:
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

---

## 🐛 Known Issues

| Issue | Status | Workaround |
|-------|--------|------------|
| Review images slow to load | ✅ Fixed | Images load concurrently in background |
| Page 2+ reviews navigation | ✅ Fixed | Uses `slug` instead of URL for navigation |
| Duplicate items on load more | ✅ Fixed | Proper pagination with `currentPage` tracking |

---

## 🙏 Acknowledgements

- **[GameBlade](https://gameblade.ir/)** – For providing the excellent game review API and WordPress content
- **JetBrains** – For Kotlin and IntelliJ IDEA
- **Google** – For Android, Jetpack Compose, and the Android ecosystem
- **Open Source Community** – For all the amazing libraries that make development easier

---

## 📞 Contact

**Amir Mahdi Heidary**

- GitHub: [@MRGHOST2007](https://github.com/MRGHOST2007)
- Email: [amir.m.86h5346@gmail.com](mailto:amir.m.86h5346@gmail.com)
- Telegram: [MRGHOST2007](https://t.me/mrghost2007)

---

## ⭐ Show Your Support

If you found this project helpful or interesting, please consider:

- ⭐ Starring the repository on GitHub
- 🐛 Reporting issues or suggesting features
- 🔀 Contributing to the codebase

---

<div align="center">
  <sub>Built with ❤️ using Kotlin and Jetpack Compose</sub>
</div>

---

**GameBase** – A project by **Amir Mahdi Heidary**
