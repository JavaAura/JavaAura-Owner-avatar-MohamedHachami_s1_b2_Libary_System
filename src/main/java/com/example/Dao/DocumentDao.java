package com.example.Dao;

import java.util.List;
import com.example.Model.*;
import java.sql.SQLException;


public interface DocumentDao {
        List<Document> getDocumentByName(String name) throws SQLException;
        boolean emprunterDocuemnt(Long id) throws SQLException;
        List<Document> allDocuments() throws SQLException;
        Document getDocumentById(Long id) throws SQLException;
        boolean returnDocuement(Long id) throws SQLException;
        boolean addLivre(Livre livre) throws SQLException;
        boolean addMagazine(Magazine magazine) throws SQLException;
        boolean addJournalScientifique(JournalScientifique journalScientifique) throws SQLException;
        boolean addTheseUniversitaire(TheseUniversitaire theseUniversitaire) throws SQLException;



}
