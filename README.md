# Daily Task Manager

A native Android application built with Kotlin that helps users organize daily tasks, track completion, search saved tasks, and receive due-date reminders.

## Project Overview

Daily Task Manager provides a simple place to create, review, update, and remove tasks. Users can register and log in on the device, manage task details, mark work complete, and search their task list.

## Main Features

- **Registration and login:** Create a local account and sign in with an email address and password.
- **Task management:** Add tasks with a name, description, and optional due date.
- **Edit and delete:** Open a task's details to update it or remove it.
- **Search:** Filter the task list by task text.
- **Completion tracking:** Mark tasks complete or incomplete.
- **Reminders:** Schedule notifications for incomplete tasks with due dates.
- **Password management:** Change the account password after verifying the current password.
- **Local persistence:** Keep account information and tasks on the device between app sessions.

## Connected Activities

The mobile app includes the following connected screens:

1. **LoginActivity / RegisterActivity** – account creation and sign-in.
2. **MainActivity** – task list, search, add task, completion status, password change, and logout.
3. **TaskDetailsActivity** – view task information and access edit or delete actions.
4. **AddTaskActivity** – shared form for creating a new task or editing an existing task.

The Android manifest registers these activities. LoginActivity is the launcher activity; the other application screens are not exported.

## Security Measures

- Passwords are not stored as plain text. The app stores a salted PBKDF2 password hash.
- A cryptographically secure random generator creates a unique salt for account registration.
- Password verification uses a constant-time byte comparison.
- Account creation validates email format, password length and character categories, and matching confirmation fields.
- Login checks required fields and displays a generic invalid-credentials message.
- Authentication values are stored in app-private SharedPreferences.
- Internal activities are declared non-exported in the Android manifest.
- The app requests notification permission on supported Android versions before posting reminders.

**Security scope:** This is a local-device coursework application with one local account. It does not provide server-side authentication, cloud synchronization, or protection against a compromised/rooted device. Do not use a real or reused password for a classroom demonstration.

## Technology

- Kotlin
- Android SDK (compile/target SDK 33; minimum SDK 24)
- AndroidX AppCompat and Activity Result APIs
- RecyclerView
- SharedPreferences
- Android AlarmManager and notifications
- Gradle Kotlin DSL

## Build and Run

1. Install Android Studio and a Java 17 JDK.
2. Clone this repository:
   `git clone https://github.com/Engawe25734/Daily-task-management.git`
3. Open the cloned project folder in Android Studio.
4. Allow Gradle to sync and install the Android SDK platform/build tools requested by the project.
5. Select an emulator or connected Android device and run the `app` configuration.

A GitHub Actions workflow is configured to run unit tests and assemble the debug APK for pushes to the project branches and pull requests targeting `main`.

## Final Presentation

A five-minute screen recording demonstrating the application and selected implementation details is required for the course submission. Add the actual recording URL to [PRESENTATION.md](PRESENTATION.md) and place the video link in the repository root before submitting.

## Changes Since Week 4

The final project includes task detail navigation and the completed add, edit, and delete workflow; improved task information display; password hashing and password-change support; task search and completion tracking; due-date reminders; repository documentation; and automated Android build verification.

## Project Summary

Daily Task Manager is a Kotlin-based Android application designed to help users organize everyday responsibilities. It supports local account registration and login, task creation, task editing and deletion, task search, completion tracking, and due-date reminders. The application demonstrates Android activity navigation, input validation, local data persistence, password security practices, and GitHub-based version control. The project is intended as a portfolio demonstration of a practical mobile productivity application.
