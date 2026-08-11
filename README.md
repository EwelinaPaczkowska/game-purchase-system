# Game Purchase System

Academic Java project implementing a game purchase and library management system using object-oriented programming, UML and Swing.

## About the project

The application is a desktop system for managing games, players, developers, purchases and reviews.

The project was created as part of the MAS course and demonstrates different object-oriented modelling and implementation techniques.

## Features

The system allows users to:

- browse available games,
- manage players and their game libraries,
- purchase games for selected players,
- display purchased games,
- add and display game reviews,
- manage developers,
- distinguish between singleplayer and multiplayer games,
- manage games in development and released games,
- save and restore application data.

## Technologies

- Java
- Java Swing
- Object-Oriented Programming
- Java Serialization
- UML

No external libraries are required for the core application.

## Object-Oriented Concepts

The project demonstrates several object-oriented modelling concepts, including:

- associations,
- association classes,
- composition,
- inheritance,
- interfaces,
- overlapping inheritance,
- dynamic inheritance,
- object extent management.

For example, the relationship between `Player` and `Game` is represented using the `Purchase` association class, which stores the purchase date.

## Main Classes

- `Game` – represents a game available in the system
- `Player` – represents a player and their purchased games
- `Purchase` – represents a game purchase
- `Developer` – represents a game developer
- `IndependentDeveloper` – represents a developer who can also publish games
- `Publisher` – represents a game publisher
- `Review` – represents a review assigned to a game
- `Singleplayer` – stores singleplayer-specific game information
- `Multiplayer` – stores multiplayer-specific game information
- `GameInDevelopment` – represents a game currently in development
- `GameReleased` – represents a released game
- `ObjectPlus` – manages object extents and data persistence
- `PurchaseAdminFrame` – Swing-based graphical user interface

## Application GUI

The application provides an administrator interface for handling game purchases.

The administrator can:

1. select a player,
2. view games already owned by the player,
3. select a game from the available games,
4. view game details and reviews,
5. enter the purchase date,
6. assign the selected game to the player's library.

## Data Persistence

Application data is stored using Java object serialization.

The `ObjectPlus` class manages object extents and saves them to the `extent.txt` file.

If no saved extent is available when the application starts, sample data is created automatically.
