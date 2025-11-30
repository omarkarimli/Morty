# Morty

Morty is a modern Android application showcasing the Rick and Morty API. It demonstrates modern Android development practices, utilizing Jetpack Compose, Hilt, Ktor, and Dynamic Feature Modules.

## 🏗 Tech Stack & Libraries

The project leverages the latest Android technologies:

*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **Architecture Pattern**: MVI (Model-View-Intent)
*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
    *   **Material 3**: For modern UI components and styling.
    *   **Navigation Compose**: For handling navigation between screens.
    *   **Coil**: For asynchronous image loading.
    *   **Compose Shimmer**: For loading state animations.
*   **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
*   **Networking**: [Ktor Client](https://ktor.io/docs/client-create-new-application.html)
    *   Engine: OkHttp
    *   Serialization: Kotlinx Serialization
*   **Asynchrony**: Kotlin Coroutines & Flow
*   **Modularization**:
    *   Multi-module architecture (`:app`, `:network`, `:dynamicfeature`)
    *   **Play Feature Delivery**: Support for dynamic feature modules.
*   **Build System**: Gradle (Kotlin DSL) with Version Catalogs (`libs.versions.toml`).

## 📂 Project Structure

The project is organized into the following modules:

*   **:app**: The main application module. It contains the core UI features (Characters, Episodes), DI setup, and navigation logic.
    *   Feature packages: `allcharacters`, `characterdetails`, `allepisodes`, `episode`.
*   **:network**: A pure Kotlin/Android library module responsible for all network operations. It encapsulates the Ktor client, data models (`RemoteEpisode`, `Episode`), and API definitions.
*   **:dynamicfeature**: A dynamic feature module designed to be downloaded on demand (using Play Feature Delivery).

## 📱 Features

*   **Character List**: Browse a list of characters from the Rick and Morty universe.
*   **Character Details**: View detailed information about specific characters.
*   **Episode Explorer**: Browse and view episode information.
*   **Dynamic Feature**: content delivered via the dynamic feature module.

## 🛠 Architecture

The app follows the recommended modern Android architecture guidelines:
*   **MVI (Model-View-Intent)**: The app implements the MVI pattern where Intents (Events) are processed to produce a new immutable State, ensuring a Unidirectional Data Flow (UDF).
*   **Modular Architecture**: The codebase is split into feature and data modules (`:app`, `:network`, `:dynamicfeature`) to ensure separation of concerns, scalability, and faster build times.
*   **Dependency Injection**: Hilt is used to inject repositories and network clients into ViewModels.
