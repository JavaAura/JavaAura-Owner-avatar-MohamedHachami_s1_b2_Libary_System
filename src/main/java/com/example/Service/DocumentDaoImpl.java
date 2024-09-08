package com.example.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.Dao.DocumentDao;
import com.example.Model.Document;
import com.example.Model.Livre;
import com.example.Model.Magazine;
import com.example.Utils.DatabaseConnection;

public class DocumentDaoImpl implements DocumentDao {

     private static Connection con = DatabaseConnection.getConnection();

    @Override
    public List<Document> getDocumentByName(String name) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDocumentByName'");
    }

    @Override
    public boolean emprunterDocuemnt(Long id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'emprunterDocuemnt'");
    }

    @Override
    public List<Document> allDocuments() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'allDocuments'");
    }

    @Override
    public Document getDocumentById(Long id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDocumentById'");
    }

    @Override
    public boolean returnDocuement(Long id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnDocuement'");
    }

    @Override
public boolean addLivre(Livre livre) throws SQLException {
    if (con == null) {
        throw new SQLException("Database connection is not initialized.");
    }

    String type = "Livre";

    // Use RETURNING to get the inserted document_id
    String query1 = "INSERT INTO Document (titre, auteur, datePublication, nombreDePages, type ,available) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
    String query2 = "INSERT INTO Livre (document_id, isbn) VALUES (?, ?)";

    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;

    try {
        con.setAutoCommit(false);

        // Insert into Document table and get the returned id
        ps1 = con.prepareStatement(query1);
        ps1.setString(1, livre.getTitre());
        ps1.setString(2, livre.getAuteur());
        ps1.setDate(3, new java.sql.Date(livre.getDatePublication().getTime()));
        ps1.setLong(4, livre.getNombresPages());
        ps1.setString(5, type);
        ps1.setInt(6, 0);

        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) {
            long documentId = rs1.getLong(1); // Get the generated document_id

            // Insert into Livre table with the retrieved document_id
            ps2 = con.prepareStatement(query2);
            ps2.setLong(1, documentId);
            ps2.setLong(2, livre.getIsbn());

            int n2 = ps2.executeUpdate();
            if (n2 == 1) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } else {
            con.rollback();
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        return false;
    } finally {
        try {
            if (con != null) {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (ps1 != null) {
            try {
                ps1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (ps2 != null) {
            try {
                ps2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    @Override
    public boolean addMagazine(Magazine magazine) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMagazine'");
    }
    // private 
}
