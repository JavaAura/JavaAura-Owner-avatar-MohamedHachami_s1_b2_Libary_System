package com.example.Service;

import com.example.Dao.TheseUniversitaireDao;
import com.example.Model.TheseUniversitaire;
import com.example.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TheseUniversitaireDaoImpl implements TheseUniversitaireDao {

    private static Connection con = DatabaseConnection.getConnection();

    
    @Override
    public boolean addTheseUniversitaire(TheseUniversitaire theseUniversitaire) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
    
        String type = "TheseUniversitaire";
    
        String query1 = "INSERT  INTO Document (titre, auteur, datePublication, nombreDePages, type,available) VALUES (?, ?, ?, ?, ? ,?)  RETURNING id";
        String query2 = "INSERT INTO TheseUniversitaire (document_id, université,domaine) VALUES (?, ? ,?) ";
    
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
    
    
        try {
                con.setAutoCommit(false);
                ps1 = con.prepareStatement(query1);
                ps1.setString(1, theseUniversitaire.getTitre());
                ps1.setString(2, theseUniversitaire.getAuteur());
                ps1.setDate(3, new java.sql.Date(theseUniversitaire.getDatePublication().getTime()));
                ps1.setLong(4, theseUniversitaire.getNombresPages());
                ps1.setString(5, type);
                ps1.setInt(6, 1);
    
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    long documentId = rs1.getLong(1); 
        
                    ps2 = con.prepareStatement(query2);
                    ps2.setLong(1, documentId);
                    ps2.setString(2, theseUniversitaire.getUniversite());
                    ps2.setString(3, theseUniversitaire.getDomaine());

        
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
        }
        finally {
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
    public boolean deleteTheseUniversitaire(Long id) throws SQLException {
        String deleteThèseUniversitaireQuery = "DELETE FROM TheseUniversitaire WHERE document_id = ?";
        String deleteDocumentQuery = "DELETE FROM Document WHERE id = ?";

        PreparedStatement deleteThèseUniversitaireStmt = null;
        PreparedStatement deleteDocumentStmt = null;

        try {
            con.setAutoCommit(false);

            deleteThèseUniversitaireStmt = con.prepareStatement(deleteThèseUniversitaireQuery);
            deleteThèseUniversitaireStmt.setLong(1, id);
            int n1 = deleteThèseUniversitaireStmt.executeUpdate();

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
            if (deleteThèseUniversitaireStmt != null) {
                deleteThèseUniversitaireStmt.close();
            }
            if (deleteDocumentStmt != null) {
                deleteDocumentStmt.close();
            }
            con.setAutoCommit(true);
        }
    }


    @Override
    public TheseUniversitaire getTheseUniversitaireById(Long id) throws SQLException {
       TheseUniversitaire theseUniversitaire = new TheseUniversitaire();
    
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM TheseUniversitaire T, Document D WHERE T.document_id = D.id AND D.id = ?   ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
            
                theseUniversitaire.setId(rs.getLong("id"));
                theseUniversitaire.setTitre(rs.getString("titre"));
                theseUniversitaire.setAuteur(rs.getString("auteur"));
                theseUniversitaire.setDatePublication(rs.getDate("datePublication"));
                theseUniversitaire.setNombresPages(rs.getInt("nombreDePages"));
                theseUniversitaire.setType(rs.getString("type"));
                theseUniversitaire.setUniversite(rs.getString("université"));
                theseUniversitaire.setDomaine(rs.getString("domaine"));


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
        return theseUniversitaire; 
    }


    @Override
    public boolean updateTheseUniversitaire(TheseUniversitaire theseUniversitaire) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        
        
        String query1 = "UPDATE Document SET titre = ?, auteur = ?, datePublication = ?, nombreDePages = ?, type = ? WHERE id = ?";
        String query2 = "UPDATE TheseUniversitaire SET université = ? , domaine = ? WHERE document_id = ?";
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            con.setAutoCommit(false);
        
            ps1 = con.prepareStatement(query1);
            ps1.setString(1, theseUniversitaire.getTitre());
            ps1.setString(2, theseUniversitaire.getAuteur());
            ps1.setDate(3, new java.sql.Date(theseUniversitaire.getDatePublication().getTime()));
            ps1.setLong(4, theseUniversitaire.getNombresPages());
            ps1.setString(5, theseUniversitaire.getType());
            ps1.setLong(6, theseUniversitaire.getId()); 
        
            int n1 = ps1.executeUpdate();
            
            if (n1 > 0) {
                ps2 = con.prepareStatement(query2);
                ps2.setString(1, theseUniversitaire.getUniversite());
                ps2.setString(2, theseUniversitaire.getDomaine());
                ps2.setLong(3, theseUniversitaire.getId()); 

        
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
