package com.example.Dao;

import com.example.Model.JournalScientifique;

import java.sql.SQLException;

public interface JournalScientifiqueDao {
    boolean addJournalScientifique(JournalScientifique journalScientifique) throws SQLException;
    boolean deleteJournalScientifiqueDao(Long id) throws SQLException;
    JournalScientifique getJournalScientifiqueById(Long id) throws SQLException;
    boolean updateJournalScientifiqueDao(JournalScientifique journalScientifique) throws SQLException;



}
