# BookTrack - JavaFX Library Management Application

![BookTrack](https://github.com/JavaAura/MohamedHachami_s1_b2_Libary_System/blob/main/screenshots/screen_login.png)
![BookTrack](https://github.com/JavaAura/MohamedHachami_s1_b2_Libary_System/blob/main/screenshots/screen_docs.png)
![BookTrack](https://github.com/JavaAura/MohamedHachami_s1_b2_Libary_System/blob/main/screenshots/screen_edit.png)

## Project Context

This project aims to automate the tasks of a municipal library through a JavaFX application. The application allows for the management of document inventory, users, borrowing, and reservations.

## Key Features

### Document Management
- **Add, Update, Delete Documents**: Allows you to add new documents, update existing ones, or remove obsolete documents.
- **View All Documents**: Displays the complete list of documents available in the library.
- **Search for Documents**: Find specific documents based on various criteria.

### User Management
- **Add, Update, Delete Users**: Manage library user information, including adding new users, modifying existing details, and deleting users.
- **View All Users**: Access a list of all registered users.
- **Search for Users**: Find specific users based on their details.

### Borrowing Management
- **Borrow a Document**: Allows users to borrow documents, with specific borrowing limits (3 for students, 10 for professors).
- **Return a Document**: Manage the return of borrowed documents.

### Reservation Management
- **Reserve a Document**: Allows users to reserve documents for future use.
- **Cancel a Reservation**: Cancel an existing reservation if necessary.

### Exit the Application
- Allows for a clean exit from the application.

## Login

The application includes a login interface that requires users to authenticate before accessing management features.

## Application Structure

- **Views (FXML files)**: Contains FXML files for defining the application's user interface.
- **Model (Entities)**: Contains classes representing the application's entities, such as documents and users.
- **DAO (Data Access Objects)**: Provides interfaces for CRUD (Create, Read, Update, Delete) operations on documents, users, borrowings, and reservations.
- **Service (DAO Implementations)**: Implements DAO interfaces for interacting with the database.

## Environnement Java
- **Java JDK 8** ou version supérieure.
- **PostgreSQL** Database used to store data.
- **JavaFX** Framework for creating desktop application.
- **Maven** Project management and build automation tool.




### 1. Cloner le dépôt
Clonez le dépôt dans votre machine locale.

```bash
git clone https://github.com/votre-depot/gestion-documents.git
cd gestion-documents
```

### 2. Ensure that Maven is installed on your machine.

### 3.Configurer database.properties en .env
Change the .env.example to .env and add your databases credentials

### 4.Build the project with Maven
```bash
mvn clean install
```

### 5.Build the project with Maven

```bash
mvn javafx:run
```


