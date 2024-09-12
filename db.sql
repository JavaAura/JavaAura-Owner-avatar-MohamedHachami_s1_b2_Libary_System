

CREATE TABLE Document (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    datePublication DATE NOT NULL,
    nombreDePages BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    available INT NOT NULL
);

CREATE TABLE Livre (
    id SERIAL PRIMARY KEY,
    document_id INT UNIQUE, 
    isbn VARCHAR(13) NOT NULL UNIQUE,
    CONSTRAINT FK_Livre_Document FOREIGN KEY (document_id)
        REFERENCES Document(id) ON DELETE CASCADE
) INHERITS (Document);


CREATE TABLE Magazine (
    id SERIAL PRIMARY KEY,
    document_id INT UNIQUE, 
    numero BIGINT NOT NULL UNIQUE,
    CONSTRAINT FK_Magazine_Document FOREIGN KEY (document_id)
        REFERENCES Document(id) ON DELETE CASCADE
) INHERITS (Document);

CREATE TABLE JournalScientifique (
    id SERIAL PRIMARY KEY,
    document_id INT UNIQUE,
    domaineRecherche VARCHAR(255) NOT NULL,
    CONSTRAINT FK_JournalScientifique_Document FOREIGN KEY (document_id)
        REFERENCES Document(id) ON DELETE CASCADE
) INHERITS (Document);

CREATE TABLE ThèseUniversitaire (
    id SERIAL PRIMARY KEY,
    document_id INT UNIQUE,
    université VARCHAR(255) NOT NULL,
    domaine VARCHAR(255) NOT NULL,
    CONSTRAINT FK_ThèseUniversitaire_Document FOREIGN KEY (document_id)
        REFERENCES Document(id) ON DELETE CASCADE
) INHERITS (Document);


-- connect ->  sudo -u postgres psq
-- show databases  -> \l
--- connect to db -> \c booktrack 


    CREATE TABLE Document (
        id  SERIAL PRIMARY KEY,
        titre VARCHAR(255) NOT NULL,
        auteur VARCHAR(255) NOT NULL,
        datePublication DATE NOT NULL,
        nombreDePages BIGINT NOT NULL,
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



-- <GridPane  fx:controller="com.example.controllers.DocumentController"
--           xmlns:fx="http://javafx.com/fxml" alignment="center"
--           hgap="10" vgap="10">
--     <padding><Insets top="40" right="40" bottom="40" left="40"/></padding>
--     <columnConstraints>
--         <ColumnConstraints minWidth="100" prefWidth="100"
--          maxWidth="Infinity" halignment="RIGHT">
--         </ColumnConstraints>
--         <ColumnConstraints minWidth="200" prefWidth="200"
--                            maxWidth="Infinity" hgrow="ALWAYS">
--         </ColumnConstraints>
--     </columnConstraints>

--      <!-- Add Header Label -->
--      <Label text="Add Document" GridPane.columnIndex="0"
--      GridPane.rowIndex="0" GridPane.columnSpan="2"
--      GridPane.rowSpan="1" GridPane.halignment="CENTER" >
--     <font>
--         <Font name="Arial" size="24" ></Font>
--     </font>
--     <GridPane.margin>
--         <Insets top="20" right="0" bottom="20" left="0"></Insets>
--     </GridPane.margin>
--     </Label>

--     <!-- Title -->
--     <Label fx:id="labelTitleField" text="Title : " GridPane.columnIndex="0" GridPane.rowIndex="1" wrapText="true" GridPane.halignment="LEFT"/>
--     <TextField fx:id="titleField" prefHeight="40" GridPane.columnIndex="1" GridPane.rowIndex="1"/>
--     <Label fx:id="validationTitleField" GridPane.columnIndex="1" GridPane.rowIndex="2" textFill="RED"/>


--     <!-- Auteur -->
--     <Label fx:id="labelAuthorField" text="Author : " GridPane.columnIndex="0" GridPane.rowIndex="3" wrapText="true" GridPane.halignment="LEFT"/>
--     <TextField fx:id="auteurhField" prefHeight="40" GridPane.columnIndex="1" GridPane.rowIndex="3"/>
--     <Label fx:id="validationAuteurhField" GridPane.columnIndex="1" GridPane.rowIndex="4" textFill="RED"/>

--     <!-- Date Publication -->
--     <Label fx:id="labelDatePublicationField" text="Date Publication : " GridPane.columnIndex="0" GridPane.rowIndex="5" wrapText="true" GridPane.halignment="LEFT"/>
--     <DatePicker fx:id="datePublicationField" prefHeight="40" GridPane.columnIndex="1" GridPane.rowIndex="5"/>
--     <Label fx:id="validationDatePublicationField" GridPane.columnIndex="1" GridPane.rowIndex="6" textFill="RED"/>

--     <!-- Nombre de page -->
--     <Label fx:id="labelNombrePageField" text="Nombre de page : " GridPane.columnIndex="0" GridPane.rowIndex="7" wrapText="true" GridPane.halignment="LEFT"/>
--     <TextField fx:id="pageCountField" prefHeight="40" GridPane.columnIndex="1" GridPane.rowIndex="7"/>
--     <Label fx:id="validationPageCountField" GridPane.columnIndex="1" GridPane.rowIndex="8" textFill="RED"/>

--     <!-- Document type -->
--     <Label fx:id="labelDocTypeField" text="Document type :" GridPane.columnIndex="0" GridPane.rowIndex="9" wrapText="true" GridPane.halignment="LEFT"/>
   
--     <HBox  fx:id="radioButtonList" alignment="CENTER" spacing="20.0" GridPane.columnIndex="1" GridPane.rowIndex="9">
--         <RadioButton fx:id="Livre" text="Livre">
--             <toggleGroup>
--                 <ToggleGroup fx:id="group"/>
--             </toggleGroup>
--         </RadioButton>
--         <RadioButton fx:id="Magazine" text="Magazine">
--             <toggleGroup>
--                 <fx:reference source="group"/>
--             </toggleGroup>
--         </RadioButton>
--         <RadioButton fx:id="JournalScientifique" text="JournalScientifique">
--             <toggleGroup>
--                 <fx:reference source="group"/>
--             </toggleGroup>
--         </RadioButton>
--         <RadioButton fx:id="TheseUniversitaire" text="ThèseUniversitaire">
--             <toggleGroup>
--                 <fx:reference source="group"/>
--             </toggleGroup>
--         </RadioButton>
--     </HBox>
--     <Label fx:id="validationDocumentType" GridPane.columnIndex="1" GridPane.rowIndex="10" textFill="RED"/>


--     <!-- Domaine Recherche -->
--     <TextField fx:id="domaineRechercheField" visible="false" GridPane.columnIndex="1" GridPane.rowIndex="11" />

--     <!-- Université -->
--     <TextField fx:id="universiteField" visible="false" GridPane.columnIndex="1" GridPane.rowIndex="12" />

--     <!-- Domaine -->
--     <TextField fx:id="domaineField" visible="false" GridPane.columnIndex="1" GridPane.rowIndex="13" />
   
--     <!-- domaineRecherche -->
--     <Label fx:id="labeldomaineRechercheField" visible="false" text="Research field : " GridPane.columnIndex="0" GridPane.rowIndex="1" wrapText="true" GridPane.halignment="LEFT"/>
--     <TextField fx:id="domaineRechercheField" visible="false" prefHeight="40" GridPane.columnIndex="1" GridPane.rowIndex="1"/>
--     <Label fx:id="validationdomaineRechercheField"   GridPane.columnIndex="1" GridPane.rowIndex="2" textFill="RED"/>

--     <!-- domaineRecherche -->


--      <!-- université -->
--         <Label fx:id="labelUniversity" visible="false" text="University : " GridPane.columnIndex="0" GridPane.rowIndex="1" wrapText="true" GridPane.halignment="LEFT"/>
--         <TextField fx:id="universityField" visible="false" prefHeight="40" GridPane.columnIndex="1" GridPane.rowIndex="1"/>
--         <Label fx:id="validationUniversityField"  GridPane.columnIndex="1" GridPane.rowIndex="2" textFill="RED"/>
--      <!-- université -->


--       <!-- domain -->
--     <Label fx:id="labeldomaineField" visible="false" text="Domain : " GridPane.columnIndex="0" GridPane.rowIndex="3" wrapText="true" GridPane.halignment="LEFT"/>
--     <TextField fx:id="domainField" visible="false" prefHeight="40" GridPane.columnIndex="1" GridPane.rowIndex="3"/>
--     <Label fx:id="validationDomainField" GridPane.columnIndex="1" GridPane.rowIndex="4" textFill="RED"/>
--       <!-- domain -->



--     <HBox alignment="CENTER" spacing="20.0" GridPane.columnIndex="0" GridPane.rowIndex="14" GridPane.columnSpan="2">
--         <Button fx:id="cancelButton" text="Cancel"
--                 prefWidth="100" prefHeight="40" cancelButton="true"
--                 style="-fx-background-color: #92969c;"
--                 onAction="#handleCancel"
--                 />
--         <Button fx:id="nextButton" text="Next"
--                 prefWidth="100" prefHeight="40" defaultButton="true"
--                 GridPane.columnIndex="0" GridPane.rowIndex="11"
--                 GridPane.columnSpan="2" GridPane.rowSpan="1"
--                 GridPane.halignment="CENTER" onAction="#next">
    
--         </Button>
--         <!-- <Button fx:id="submitButton" text="Submit"
--             visible="false"
--             prefWidth="100" prefHeight="40" defaultButton="true"
--             GridPane.columnIndex="0" GridPane.rowIndex="11"
--             GridPane.columnSpan="2" GridPane.rowSpan="1"
--             GridPane.halignment="CENTER" onAction="#submit">

--         </Button> -->
--     </HBox>


-- </GridPane>