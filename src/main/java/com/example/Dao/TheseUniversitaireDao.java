package com.example.Dao;

import com.example.Model.TheseUniversitaire;


import java.sql.SQLException;

public interface TheseUniversitaireDao {
    boolean addTheseUniversitaire(TheseUniversitaire theseUniversitaire) throws SQLException;
    boolean deleteTheseUniversitaire(Long id) throws SQLException;
    TheseUniversitaire getTheseUniversitaireById(Long id) throws SQLException;
    boolean updateTheseUniversitaire(TheseUniversitaire theseUniversitaire) throws SQLException;

    
}
