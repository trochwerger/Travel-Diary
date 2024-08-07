# Travel Diary Application

The **Travel Diary** application is a personal journaling tool designed to help users document and manage their travel experiences. It allows users to create, view, update, and delete journal entries, manage user accounts, and maintain session information. The app uses file-based storage for persisting journal entries and user data.

## Features

- **User Authentication**
  - Register new users
  - Authenticate existing users
  - Session management for tracking the current user

- **Journal Management**
  - Create new journal entries with title, date, content, and optional image
  - View journal entries specific to the logged-in user
  - Update existing journal entries
  - Delete journal entries

- **Data Storage**
  - Journal entries and user information are stored in plain text files for simplicity
  - Entries are managed with a list in memory and written to `entries.txt`
  - User data is managed with a map in memory and written to `users.txt`

## Setup

1. **Clone the repository:**

   ```sh
   git clone https://github.com/yourusername/travel-diary.git
   ```

2. **Navigate to the project directory:**

   ```sh
   cd travel-diary
   ```

3. **Compile and run the application:**

   You can run this app on [IntelliJ Idea](https://www.jetbrains.com/idea/download/?section=mac)

## Authors

- [Aryan Khurana](https://github.com/AryanK1511)
- [Athul Anilkumar](https://github.com/AthulA003)
- [Tomas Rochwerger](https://github.com/trochwerger)