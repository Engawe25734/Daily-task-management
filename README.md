# Daily-task-management

# Project Overview

Daily Task Manager is an Android application designed to help users organize their daily activities and manage tasks efficiently. The application allows users to register, log in, create tasks, edit existing tasks, delete tasks, search for tasks, and track task completion.

The goal of this project is to provide a simple and user-friendly mobile application that helps people stay organized and manage their daily responsibilities.

## Main Features

- User Registration:** Allows new users to create an account.
- User Login:** Allows registered users to access the application.
- Add Tasks:** Users can create tasks and enter task details.
- Edit Tasks:** Users can update existing task information.
- Delete Tasks:** Users can remove tasks they no longer need.
- Search Tasks:** Users can find specific tasks.
- Task Completion:** Users can mark tasks as completed.
- Local Data Storage:** Saves application information on the device.

## Application Activities

The application uses multiple connected Android activities:

1. **Login and Registration Activity:** Handles user registration and login.
2. **Main Activity:** Displays the task list and provides access to task management features.
3. **Task Details Activity:** Displays task information and allows users to edit or delete tasks.
4. **Add Task Activity:** Allows users to create new tasks and update existing ones.

## Security Features

Security is an important part of this application. The following measures help protect user information:

- Passwords are hashed with individual salts instead of being stored as plain text.
- Password validation helps prevent weak passwords.
- User input is validated before processing.
- Application data is stored locally using app-private storage.
- Internal activities are not exposed to other applications unnecessarily.

The application is designed for local device use. It does not currently provide server-based authentication or cloud synchronization.

## Technologies Used

- Android Studio
- Kotlin
- Android SDK
- XML Layouts
- Gradle
- Git and GitHub

## Installation and Setup

1. Download or clone the project from GitHub.
2. Open Android Studio.
3. Select **Open** and choose the Daily Task Manager project folder.
4. Allow Gradle to synchronize the project.
5. Connect an Android device or start an Android emulator.
6. Click the **Run** button to install and launch the application.

### Clone the Repository


git clone https://github.com/Engawe25734/Daily-task-management.git


## Testing

The application should be tested to verify that:

- Users can register and log in.
- Users can create new tasks.
- Existing tasks can be edited.
- Tasks can be deleted.
- Users can search for tasks.
- Tasks can be marked as completed.
- User input validation works correctly.

## Changes Since Week 4

The project was improved by adding and updating task management features. Users can now edit existing tasks, view task details, and delete tasks with a confirmation message. The application documentation was also improved, and security measures were reviewed and documented.

These changes make the application more complete, easier to use, and better prepared for a final portfolio presentation.

## GitHub Repository

https://github.com/Engawe25734/Daily-task-management

## Project Summary

Daily Task Manager demonstrates the development of an Android application using Kotlin and Android Studio. The project provides practical task management features, including adding, editing, deleting, searching, and completing tasks.

The project also demonstrates important software development concepts such as user interface design, activity navigation, local data storage, input validation, password security, testing, and version control using GitHub.

The overall goal is to provide users with a simple and reliable tool for managing daily responsibilities while demonstrating practical Android application development skills.
