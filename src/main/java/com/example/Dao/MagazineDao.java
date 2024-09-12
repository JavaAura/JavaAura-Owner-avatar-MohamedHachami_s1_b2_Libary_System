package com.example.Dao;

import com.example.Model.Magazine;
import java.sql.SQLException;

public interface MagazineDao {
    boolean addMagazine(Magazine magazine) throws SQLException;
    boolean deleteMagazine(Long id) throws SQLException;
    Magazine getMagazineById(Long id) throws SQLException;
    boolean updateMagazine(Magazine magazine) throws SQLException;



}
