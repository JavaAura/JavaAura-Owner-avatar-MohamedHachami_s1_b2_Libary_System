package com.example.Dao;

import java.util.List;
import com.example.Model.*;
import java.sql.SQLException;


public interface DocumentDao {
        List<Document> getDocumentByName(String name) throws SQLException;
        List<Document> allDocuments() throws SQLException;
        Document getDocumentById(Long id) throws SQLException;
        List<Document> availableDocuments() throws SQLException;
        boolean isAvailable(Long id) throws SQLException;








}
