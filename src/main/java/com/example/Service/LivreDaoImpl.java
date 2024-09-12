package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.Dao.LivreDao;
import com.example.Model.Livre;
import com.example.Utils.DatabaseConnection;
import com.example.Utils.Utils;


public class LivreDaoImpl implements LivreDao {

    private static Connection con = DatabaseConnection.getConnection();

    
    @Override
    public boolean addLivre(Livre livre) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
    
        String type = "Livre";
    
        String query1 = "INSERT INTO Document (titre, auteur, datePublication, nombreDePages, type ,available) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        String query2 = "INSERT INTO Livre (document_id, isbn) VALUES (?, ?)";
    
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
    
        try {
            con.setAutoCommit(false);
    
            ps1 = con.prepareStatement(query1);
            if (livre.getTitre() == null || livre.getTitre().isEmpty()) {
                throw new IllegalArgumentException("Titre cannot be null or empty");
            }
            ps1.setString(1, livre.getTitre());
            ps1.setString(2, livre.getAuteur());
            ps1.setDate(3, new java.sql.Date(livre.getDatePublication().getTime()));
            ps1.setLong(4, livre.getNombresPages());
            ps1.setString(5, type);
            ps1.setInt(6, 1);
    
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                long documentId = rs1.getLong(1);
    
                ps2 = con.prepareStatement(query2);
                ps2.setLong(1, documentId);
                ps2.setString(2, Utils.generateISBN());
    
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
    public boolean deleteLivre(Long id) throws SQLException {
        String deleteLivreQuery = "DELETE FROM Livre WHERE document_id = ?";
        String deleteDocumentQuery = "DELETE FROM Document WHERE id = ?";

        PreparedStatement deleteLivreStmt = null;
        PreparedStatement deleteDocumentStmt = null;

        try {
            con.setAutoCommit(false);

            deleteLivreStmt = con.prepareStatement(deleteLivreQuery);
            deleteLivreStmt.setLong(1, id);
            int n1 = deleteLivreStmt.executeUpdate();

            if(n1 ==1){
                deleteDocumentStmt = con.prepareStatement(deleteDocumentQuery);
                deleteDocumentStmt.setLong(1, id);
                int n2 = deleteDocumentStmt.executeUpdate();
                if(n2 ==2){
                    con.commit();
                    System.out.println("Deleted");
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }


        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (deleteLivreStmt != null) {
                deleteLivreStmt.close();
            }
            if (deleteDocumentStmt != null) {
                deleteDocumentStmt.close();
            }
            con.setAutoCommit(true);
        }
    }


    @Override
    public Livre getLivreById(Long id) throws SQLException {
        Livre livre = new Livre();
    
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Livre L, Document D WHERE L.document_id = D.id AND D.id = ?   ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
            
                livre.setId(rs.getLong("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setDatePublication(rs.getDate("datePublication"));
                livre.setNombresPages(rs.getInt("nombreDePages"));
                livre.setType(rs.getString("type"));
                livre.setIsbn(rs.getString("isbn"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return livre; 
    }


    @Override
    public boolean updateLivre(Livre livre) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        
        System.out.println(livre.getIsbn() + " " +  livre.getNombresPages());
        
        String query1 = "UPDATE Document SET titre = ?, auteur = ?, datePublication = ?, nombreDePages = ?, type = ? WHERE id = ?";
        String query2 = "UPDATE Livre SET isbn = ? WHERE document_id = ?";
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            con.setAutoCommit(false);
        
            ps1 = con.prepareStatement(query1);
            ps1.setString(1, livre.getTitre());
            ps1.setString(2, livre.getAuteur());
            ps1.setDate(3, new java.sql.Date(livre.getDatePublication().getTime()));
            ps1.setLong(4, (long)livre.getNombresPages());
            ps1.setString(5, livre.getType());
            ps1.setLong(6, livre.getId()); 
        
            int n1 = ps1.executeUpdate();
            
            if (n1 > 0) {
                ps2 = con.prepareStatement(query2);
                ps2.setString(1, livre.getIsbn());
                ps2.setLong(2, livre.getId()); 
        
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


}
