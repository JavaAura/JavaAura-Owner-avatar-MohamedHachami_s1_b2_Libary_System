package com.example.Dao;

import java.sql.SQLException;

public interface Empruntable {
    Boolean emprunter(Long documentId , Long userId) throws SQLException;
    Boolean Return(Long  documentId, Long userId)throws SQLException;
    Boolean validEmprunt(Long documentId , Long userId) throws SQLException;

}
