# Morty
<img src="https://github.com/user-attachments/assets/334d0c63-a62e-4b24-b32c-cb93aa8b218f" alt="intro" height="512" style="margin: 24px;">

Morty is a modern Android application showcasing the Rick and Morty API. It demonstrates modern Android development practices, utilizing Jetpack Compose, Koin, Ktor, and Dynamic Feature Modules.

## 🏗 Tech Stack & Libraries

The project leverages the latest Android technologies:

*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **Architecture Pattern**: MVI (Model-View-Intent)
*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
    *   **Material 3**: For modern UI components and styling.
    *   **Navigation Compose**: For handling navigation between screens.
    *   **Coil**: For asynchronous image loading.
    *   **Compose Shimmer**: For loading state animations.
*   **Dependency Injection**: [Koin](https://insert-koin.io/)
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
*   **Dependency Injection**: Koin is used to inject repositories and network clients into ViewModels.

## 📷 Screens

<img src="https://github.com/user-attachments/assets/82c36624-c0c5-4089-98af-a80004eee4b5" alt="1" height="256" style="margin: 24px;">
<img src="https://github.com/user-attachments/assets/ad82ac42-a35a-4e11-8d2c-81ee94cb2a52" alt="2" height="256" style="margin: 24px;">
<img src="https://github.com/user-attachments/assets/7f3df9fd-81f0-46cc-b12e-422579735c56" alt="3" height="256" style="margin: 24px;">
<img src="https://github.com/user-attachments/assets/c27235ba-991e-4c43-b367-77e9b76014c7" alt="4" height="256" style="margin: 24px;">
<img src="https://github.com/user-attachments/assets/77330d0d-d8a8-44ae-84ba-adaa58288d0d" alt="5" height="256" style="margin: 24px;">
<img src="https://github.com/user-attachments/assets/76834bb5-e9db-48d7-a4b0-20a30707a205" alt="6" height="256" style="margin: 24px;">
