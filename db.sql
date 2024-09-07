    CREATE TABLE Document (
        id  SERIAL PRIMARY KEY,
        titre VARCHAR(255) NOT NULL,
        auteur VARCHAR(255) NOT NULL,
        datePublication DATE NOT NULL,
        nombreDePages INT NOT NULL,
        type VARCHAR(20) NOT NULL,available int NOT NULL 
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
    numero INT NOT NULL,
     CONSTRAINT FK_Magazine_Document FOREIGN KEY(document_id)
        REFERENCES Document(id) ON DELETE CASCADE,
    UNIQUE  (numero)
);