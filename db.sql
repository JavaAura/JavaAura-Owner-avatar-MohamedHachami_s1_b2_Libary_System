

-- CREATE TABLE Document (
--     id SERIAL PRIMARY KEY,
--     titre VARCHAR(255) NOT NULL,
--     auteur VARCHAR(255) NOT NULL,
--     datePublication DATE NOT NULL,
--     nombreDePages BIGINT NOT NULL,
--     type VARCHAR(20) NOT NULL,
--     available INT NOT NULL
-- );


-- CREATE TABLE Livre (
--     id SERIAL PRIMARY KEY,
--     document_id INT UNIQUE, 
--     isbn VARCHAR(13) NOT NULL UNIQUE,
--     CONSTRAINT FK_Livre_Document FOREIGN KEY (document_id)
--         REFERENCES Document(id) ON DELETE CASCADE
-- ) INHERITS (Document);


-- CREATE TABLE Magazine (
--     id SERIAL PRIMARY KEY,
--     document_id INT UNIQUE, 
--     numero BIGINT NOT NULL UNIQUE,
--     CONSTRAINT FK_Magazine_Document FOREIGN KEY (document_id)
--         REFERENCES Document(id) ON DELETE CASCADE
-- ) INHERITS (Document);

-- CREATE TABLE JournalScientifique (
--     id SERIAL PRIMARY KEY,
--     document_id INT UNIQUE,
--     domaineRecherche VARCHAR(255) NOT NULL,
--     CONSTRAINT FK_JournalScientifique_Document FOREIGN KEY (document_id)
--         REFERENCES Document(id) ON DELETE CASCADE
-- ) INHERITS (Document);

-- CREATE TABLE ThèseUniversitaire (
--     id SERIAL PRIMARY KEY,
--     document_id INT UNIQUE,
--     université VARCHAR(255) NOT NULL,
--     domaine VARCHAR(255) NOT NULL,
--     CONSTRAINT FK_ThèseUniversitaire_Document FOREIGN KEY (document_id)
--         REFERENCES Document(id) ON DELETE CASCADE
-- ) INHERITS (Document);


-- connect ->  sudo -u postgres psq
-- show databases  -> \l
--- connect to db -> \c booktrack 


    CREATE TABLE Document (
        id  SERIAL PRIMARY KEY,
        titre VARCHAR(255) NOT NULL,
        auteur VARCHAR(255) NOT NULL,
        datePublication DATE NOT NULL,
        nombreDePages BIGINT NOT NULL,
        type VARCHAR(20) NOT NULL,
        available BOOLEAN DEFAULT TRUE
    );




CREATE TABLE Livre (
    id SERIAL  PRIMARY KEY,
    document_id INT,
    isbn VARCHAR(13) NOT NULL, 
    UNIQUE (isbn),
      CONSTRAINT FK_Livre_Document FOREIGN KEY(document_id)
        REFERENCES Document(id) ON DELETE CASCADE
);


CREATE TABLE Magazine (
    id SERIAL PRIMARY KEY,
    document_id INT, 
    numero BIGINT NOT NULL,
     CONSTRAINT FK_Magazine_Document FOREIGN KEY(document_id)
        REFERENCES Document(id) ON DELETE CASCADE,
    UNIQUE  (numero)
);

CREATE TABLE JournalScientifique (
    id SERIAL PRIMARY KEY,
    document_id INT UNIQUE,
    domaineRecherche VARCHAR(255) NOT NULL,
    CONSTRAINT FK_JournalScientifique_Document FOREIGN KEY (document_id)
        REFERENCES Document(id) ON DELETE CASCADE
) ;


CREATE TABLE TheseUniversitaire (
    id SERIAL PRIMARY KEY,
    document_id INT UNIQUE,
    université VARCHAR(255) NOT NULL,
    domaine VARCHAR(255) NOT NULL,
    CONSTRAINT FK_TheseUniversitaire_Document FOREIGN KEY(document_id)
        REFERENCES Document(id) ON DELETE CASCADE
) ;


CREATE TYPE user AS ENUM ('Student', 'Professor');


CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    email VARCHAR(255) NOT NULL,
    type_user VARCHAR(50) CHECK (type_user IN ('Professor', 'Student','Admin')) NOT NULL
) ;

CREATE TABLE Student (
    niveau_etude VARCHAR(255) NOT NULL

) INHERITS (Users);

CREATE TABLE Professor (
    specialite VARCHAR(255) NOT NULL
) INHERITS (Users);



INSERT INTO Student (name, email, type_user, niveau_etude)
VALUES ('Alice Dupont', 'alice.dupont@example.com', 'Student', 'Master Informatique');



INSERT INTO Professor (name, email, type_user, specialite)
VALUES ('Khalil', 'Khalil.mr@example.com', 'Professor', ' Informatique');

CREATE TABLE BorrowHistory (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,  -- Assuming Users.id is INTEGER
    document_id INTEGER NOT NULL,  -- Assuming Document.id is INTEGER
    borrow_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP,
    CONSTRAINT FK_DOC_BORROW FOREIGN KEY (document_id)
        REFERENCES Document(id) ON DELETE CASCADE,
    CONSTRAINT FK_USER_BORROW FOREIGN KEY (user_id)
        REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE reservation(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES Users(id),
    document_id INTEGER NOT NULL REFERENCES Document(id),
    date_reservation DATE NOT NULL
);



module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    
    requires io.github.cdimascio.dotenv;
    opens io.github.cdimascio.dotenv;

    opens com.example.controllers to javafx.fxml;
    opens com.example.Model to javafx.base;
    exports com.example;
}
