package com.example.Dao;

import com.example.Model.Document;


import java.sql.SQLException;
import java.util.*;

public interface Reservable {
    Boolean Reserve(Long documentId,Long userId) throws SQLException;
    Boolean cancelReservation(Long documentId, Long userId) throws SQLException;
    Boolean checkReservation(Long documentId) throws SQLException;
    List<Document> myReservations(Long userId) throws SQLException;
}
