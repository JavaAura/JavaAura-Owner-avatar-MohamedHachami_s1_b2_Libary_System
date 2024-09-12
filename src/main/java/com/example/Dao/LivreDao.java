package com.example.Dao;

import com.example.Model.Livre;

import java.sql.SQLException;
public interface LivreDao { 
    boolean addLivre(Livre livre) throws SQLException;
    boolean deleteLivre(Long id) throws SQLException;
    Livre getLivreById(Long id) throws SQLException;
    boolean updateLivre(Livre livre) throws SQLException;




}
