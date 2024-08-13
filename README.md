# Important: please use the develop branch as reference.

# Muzz Chat App

## Android Exercise

### Introduction

The goal of this exercise is to follow the Muzz chat design and replicate the chat functionality of the original app.

### Tech Stack

The solution was implemented using the latest tools provided by Android. The most relevant are:

- **Jetpack Compose**
- **Compose Navigation**
- **Kotlin Coroutines**
- **Hilt DI**
- **Room Database**

### Package Structure

- **Data** - Holds all the data layer-related items.
  - **Database** - Room DB and DAOs.
  - **Model** - Room entities and other models.
  - **Repository** - Repositories connected to the data source.
- **Usecase** - Use cases providing aggregation/manipulation of data from multiple sources.
- **DI** - Hilt dependency injection modules.
- **Features** - Each screen is treated as a different feature, containing the Composable Screen (the View) and the ViewModel.
- **UI** - Holds UI-related items.
  - **CommonUI** - Holds common views that are reused in the project.
  - **Theme** - Holds the app's main theming items.
  - **Navigation** - Compose navigation-related items like the destinations holder and the NavHost.

### Architecture

**MVVM** is the proposed architecture for the tech stack. The project is divided into the Data, Business, and Presentation layers to achieve a clean code structure.

#### Data Layer

- **Room Database**: Acts as the single source of truth for chat messages.
- **ChatMessageDAO**: An interface defining methods for accessing the database, such as retrieving chat messages and inserting new ones.
- **ChatRepository**: Interfaces with `ChatMessageDAO` to retrieve raw chat message data and provide methods for data insertion. It exposes data as a `Flow`, allowing subscribers to react to updates in real-time.
- **UserRepository**: Mocks the list of users.

#### Business Layer

- **ChatUseCase**: Acts as an intermediary between the data layer and the presentation layer. It combines data from `ChatRepository` and `UserRepository` to produce an aggregated model (e.g., `ChatMessageWithUserInfo`). In more complex apps, this is where data transformations and business logic would be applied.

#### Presentation Layer

- **ViewModel**:
  - The `ChatViewModel` subscribes to the `ChatUseCase`, receiving live data flows.
  - It processes the raw data into a format suitable for display (e.g., mapping to displayable items).
  - The ViewModel holds the state in a `State` object, which is observed by the UI.
- **Compose Screens (Views)**:
  - The UI components (Composable functions) observe the `State` object from the ViewModel.
  - When the state changes, the UI is automatically recomposed to reflect the new data.

### Data Flow - Entities and Components

#### Data Models

- **ChatMessage**:
  - Fields: `id` (auto-generated), `authorId`, `timestamp`, `message`.
- **User**:
  - Fields: `id`, `name`, etc. (This model is not persisted but used for enriching `ChatMessage`).
- **ChatMessageWithUserInfo**:
  - Combines `ChatMessage` with `User` data.

#### Repositories

- **ChatRepository**:
  - Interrogates `ChatMessage` data (e.g., from a local database or remote source).
  - Provides ways of updating the database with new messages.
- **UserRepository**:
  - Provides user information based on `authorId`.

#### Use Cases

- **ChatUseCase**:
  - Combines data from `ChatRepository` and `UserRepository` to produce `ChatMessageWithUserInfo`.

#### ViewModel

- **ChatViewModel**:
  - Observes changes from `ChatUseCase`.
  - Maps `ChatMessageWithUserInfo` to displayable items:
    - `DisplayableChatItemWithSmallSpacing`
    - `DisplayableChatItemWithLargeSpacing`
    - `TimestampChatItem`
  - Notifies the repository when a new message is sent.

#### UI Layer

- **Composable Functions**:
  - Render the displayable items based on their type in a LazyList.

### UI Structure

The UI is presented in the main activity, as a single screen given by the default route of the navigation host. It consists of three items in the form of composable functions:

1. **Chat Header**: A box displaying simple UI elements.
2. **Chat Screen**: A LazyList loading the chat items provided by the ViewModel.
   - Reuses the `ChatBubble` common views.
3. **Chat Box**:
   - Disabled when empty with proper UI.
   - Enabled when the user starts typing, such as the send button.

### App Behavior

For now, the app lets the user type messages in the chat box and send them while the chat box is not empty. The messages are then inserted into the database, randomly representing one of the users to simulate a conversation. To see any results, type multiple messages of various lengths, and they will randomly be assigned either to the sender or the receiver. The app calculates the timestamp difference between the items as required and displays them accordingly.

### Possible Improvements

- State management can be lifted into a common base for the ViewModel to propagate errors, loading states, and overall app events.
- Better user management mechanism, including persistent user storage.
- A more intuitive way of switching between chat users instead of randomly assigning messages.
- Improved UI and theming resources. Since the project didn’t contain any established design features like colors, sizes, fonts, etc., I used default ones and hardcoded approximations for spacing, distances, and text sizes.

### Conclusion

I loved working on this small project. Maybe someday I’ll see how your chat screen is implemented. Hope you enjoy it! ☺️
