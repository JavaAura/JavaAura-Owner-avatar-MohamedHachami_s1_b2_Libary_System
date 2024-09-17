package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;


import com.example.Dao.Empruntable;
import com.example.Dao.Reservable;
import com.example.Model.Document;
import com.example.Model.JournalScientifique;
import com.example.Model.Livre;
import com.example.Model.Magazine;
import com.example.Model.TheseUniversitaire;
import com.example.Utils.DatabaseConnection;


public class BibiloDaoImpl implements Empruntable,Reservable {

    private static Connection con = DatabaseConnection.getConnection();


    @Override
    public Boolean Reserve(Long documentId, Long userId) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }  

        String query = "INSERT INTO reservation(user_id,document_id,date_reservation) VALUES (?,?,?)";
        PreparedStatement ps=null;
        try {
            ps = con.prepareStatement(query);
            ps.setLong(1, userId);
            ps.setLong(2, documentId);
            ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));


            int n = ps.executeUpdate();
            

            return n ==1;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false; 

    }

    @Override
    public Boolean cancelReservation(Long documentId, Long userId) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }  
        String query = "DELETE FROM reservation WHERE document_id = ? AND user_id = ?";
        PreparedStatement ps=null;

        try {
            ps = con.prepareStatement(query);
            ps.setLong(1, documentId);
            ps.setLong(2, userId);


            int n = ps.executeUpdate();
            

            return n >0;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false; 

    }

    @Override
    public Boolean emprunter(Long documentId, Long userId) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }        
        String updateDocumentQuery = "UPDATE Document SET available = 0 WHERE id = ?";
        String insertBorrowHistoryQuery = "INSERT INTO BorrowHistory (user_id, document_id, borrow_date) VALUES (?, ?, ?)";

        try {
            con.setAutoCommit(false);

            try (PreparedStatement updateDocumentStmt = con.prepareStatement(updateDocumentQuery)) {
                updateDocumentStmt.setLong(1, documentId);
                int rowsAffected = updateDocumentStmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Document not found or already borrowed.");
                }
            }

            try (PreparedStatement insertBorrowHistoryStmt = con.prepareStatement(insertBorrowHistoryQuery)) {
                insertBorrowHistoryStmt.setLong(1, userId);
                insertBorrowHistoryStmt.setLong(2, documentId);
                insertBorrowHistoryStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); 

                insertBorrowHistoryStmt.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
            throw e;

        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public Boolean Return(Long documentId, Long userId) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String query1 = "UPDATE BorrowHistory SET return_date = NOW() WHERE document_id = ? AND user_id = ? AND return_date IS NULL";
        String query2 = "UPDATE Document SET available = 1 WHERE id = ?";
        
        try {
            con.setAutoCommit(false);
            
            try (PreparedStatement ps1 = con.prepareStatement(query1)) {
                ps1.setLong(1, documentId);
                ps1.setLong(2, userId);
                int affectedRows1 = ps1.executeUpdate();
                
                if (affectedRows1 == 0) {
                    con.rollback();
                    return false;
                }
            }
    
            try (PreparedStatement ps2 = con.prepareStatement(query2)) {
                ps2.setLong(1, documentId);
                int affectedRows2 = ps2.executeUpdate();
                
                if (affectedRows2 == 0) {
                    con.rollback();
                    return false;
                }
            }
    
            con.commit();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback(); 
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public Boolean validEmprunt(Long documentId, Long userId) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String query = "SELECT COUNT(*) FROM BorrowHistory WHERE document_id = ? AND user_id = ? AND return_date IS NULL";
    
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, documentId);
            ps.setLong(2, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return false; 
    }

    @Override
    public Boolean checkReservation(Long documentId) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String query = "SELECT COUNT(*) FROM reservation WHERE document_id = ? ";
    
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, documentId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return false; 
    }

    @Override
    public List<Document> myReservations(Long userId) throws SQLException {
        List<Document> documents = new ArrayList<>();
    
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String query = "SELECT d.id, d.titre, d.type, d.auteur, d.available, d.nombreDePages ,d.datePublication " +
        "FROM Reservation r " +
        "JOIN Document d ON r.document_id = d.id " +
        "WHERE r.user_id = ?";

        PreparedStatement ps;
        ResultSet rs;
    
        try {
            ps = con.prepareStatement(query);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
    
            while (rs.next()) {
            String type = rs.getString("type");
            if( type.equals("Magazine")){
                Magazine magazine = new Magazine();
                magazine.setId(rs.getLong("id"));
                magazine.setTitre(rs.getString("titre"));
                magazine.setAuteur(rs.getString("auteur"));
                magazine.setDatePublication(rs.getDate("datePublication"));
                magazine.setNombresPages(rs.getInt("nombreDePages"));
                magazine.setType(type);
                documents.add(magazine);
            }
            else if(type.equals("Livre")) {
                Livre livre = new Livre();
                livre.setId(rs.getLong("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setDatePublication(rs.getDate("datePublication"));
                livre.setNombresPages(rs.getInt("nombreDePages"));
                livre.setType(type);
                documents.add(livre);
                    

            }
            else if(type.equals("JournalScientifique")) {
                JournalScientifique jScientifique = new JournalScientifique();
                jScientifique.setId(rs.getLong("id"));
                jScientifique.setTitre(rs.getString("titre"));
                jScientifique.setAuteur(rs.getString("auteur"));
                jScientifique.setDatePublication(rs.getDate("datePublication"));
                jScientifique.setNombresPages(rs.getInt("nombreDePages"));
                jScientifique.setType(type);
                documents.add(jScientifique);
                    

            }
            else if(type.equals("TheseUniversitaire")) {
                TheseUniversitaire theseUniversitaire = new TheseUniversitaire();
                theseUniversitaire.setId(rs.getLong("id"));
                theseUniversitaire.setTitre(rs.getString("titre"));
                theseUniversitaire.setAuteur(rs.getString("auteur"));
                theseUniversitaire.setDatePublication(rs.getDate("datePublication"));
                theseUniversitaire.setNombresPages(rs.getInt("nombreDePages"));
                theseUniversitaire.setType(type);

                documents.add(theseUniversitaire);
                    

            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving reserved documents.");
        } 
    
        return documents;
    }
    
}
