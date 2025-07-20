# 📰 News XML App - Clean Architecture (MVVM + Coroutines + Hilt + Room)

A simple news reader Android application built with modern Android development practices, using **Clean Architecture**, **Coroutines**, **Hilt**, and **Room**. It fetches news data from a remote XML feed and displays it in a user-friendly UI.

---

## 📦 Features

- Fetches latest news using Retrofit (XML Parser)
- Displays news using RecyclerView
- Caches articles locally using Room
- Clean Architecture with proper separation of concerns
- Hilt for Dependency Injection
- Kotlin Coroutines + Flow for async/reactive handling
- ViewModel & StateFlow for UI state management
- Unit tests for Repository, Use Case, ViewModel

---

## 🧱 Architecture Overview

### Layered Structure

```
presentation/
    └── ui (Activity, ViewModel, Adapter, UiState)
domain/
    └── model, usecase (interfaces, business logic)
data/
    └── repository, mappers, local (Room), remote (Retrofit)
di/
    └── Modules for App, Repository, Adapter
```

### Clean Architecture Layers

| Layer            | Responsibility                            | Components                              |
|------------------|--------------------------------------------|-----------------------------------------|
| **Presentation** | UI logic, state handling                  | ViewModel, UiState, Activity            |
| **Domain**       | Business rules                            | UseCase, Domain Models                  |
| **Data**         | Data sources and transformations          | Repository, DTOs, Room, Retrofit, Mappers |

---

## 🧪 Testing Strategy

| Layer            | Component            | Test Type          | Tools                        |
|------------------|----------------------|--------------------|------------------------------|
| **Domain**       | Use cases            | Unit tests         | JUnit, Mockito               |
| **Data**         | Repository + Mappers | Unit + Integration | JUnit, Room, Coroutines      |
| **Presentation** | ViewModel            | Unit tests         | JUnit, Turbine               |
| **UI**           | Activity/Fragment    | UI/E2E tests       | Espresso (Optional)          |
| **Network**      | API interface        | Integration tests  | MockWebServer (Optional)     |

---

## 🧪 Sample Tests

- ✅ `DefaultNewsRepositoryTest` - Mocks DAO and Network, validates flow emissions and database inserts
- ✅ `DefaultNewsUseCaseTest` - Validates interaction with repository and result flow
- ✅ `NewsViewModelTest` - Mocks UseCase, verifies state emissions with Turbine

---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Async**: Coroutines + Flow
- **Network**: Retrofit + SimpleXmlConverter
- **Persistence**: Room
- **Testing**: JUnit, Mockito, Turbine, kotlinx-coroutines-test

---

## 🚀 Getting Started

1. Clone the repo

```bash
git clone https://github.com/aroranubhav/News-Xml-Clean-Architecture.git
```

2. Open in Android Studio
3. Build & Run

---

## 📂 Project Modules

- `di/` – Hilt modules (AppModule, RepositoryModule, AdapterModule)
- `data/` – DTOs, Mappers, DAOs, NetworkService
- `domain/` – UseCase interface + implementation, domain models
- `presentation/` – ViewModel, UiState, XML UI

---

## 🤝 Contributions

Feel free to fork or raise issues/PRs. This project aims to be a clean reference for news-style apps.

---

## 📄 License

This project is open-sourced under the MIT License.