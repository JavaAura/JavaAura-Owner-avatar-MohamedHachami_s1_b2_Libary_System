package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.Dao.JournalScientifiqueDao;
import com.example.Model.JournalScientifique;
import com.example.Utils.DatabaseConnection;

public class JournalScientifiqueDaoImpl implements JournalScientifiqueDao {
    
    private static Connection con = DatabaseConnection.getConnection();

    
        @Override
    public boolean addJournalScientifique(JournalScientifique journalScientifique) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
    
        String type = "JournalScientifique";
    
        String query1 = "INSERT  INTO Document (titre, auteur, datePublication, nombreDePages, type,available) VALUES (?, ?, ?, ?, ? ,?)  RETURNING id";
        String query2 = "INSERT INTO JournalScientifique (document_id, domaineRecherche) VALUES (?, ?) ";
    
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
    
    
        try {
                con.setAutoCommit(false);
                ps1 = con.prepareStatement(query1);
                ps1.setString(1, journalScientifique.getTitre());
                ps1.setString(2, journalScientifique.getAuteur());
                ps1.setDate(3, new java.sql.Date(journalScientifique.getDatePublication().getTime()));
                ps1.setLong(4, journalScientifique.getNombresPages());
                ps1.setString(5, type);
                ps1.setInt(6, 1);
    
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    long documentId = rs1.getLong(1); 
        
                    ps2 = con.prepareStatement(query2);
                    ps2.setLong(1, documentId);
                    ps2.setString(2, journalScientifique.getdomaineRecherche());
        
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
    public boolean deleteJournalScientifiqueDao(Long id) throws SQLException {
        String deleteJournalScientifiqueQuery = "DELETE FROM JournalScientifique WHERE document_id = ?";
        String deleteDocumentQuery = "DELETE FROM Document WHERE id = ?";

        PreparedStatement deleteJournalScientifiqueStmt = null;
        PreparedStatement deleteDocumentStmt = null;

        try {
            con.setAutoCommit(false);

            deleteJournalScientifiqueStmt = con.prepareStatement(deleteJournalScientifiqueQuery);
            deleteJournalScientifiqueStmt.setLong(1, id);
            int n1 = deleteJournalScientifiqueStmt.executeUpdate();

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
            if (deleteJournalScientifiqueStmt != null) {
                deleteJournalScientifiqueStmt.close();
            }
            if (deleteDocumentStmt != null) {
                deleteDocumentStmt.close();
            }
            con.setAutoCommit(true);
        }   
    }


    @Override
    public JournalScientifique getJournalScientifiqueById(Long id) throws SQLException {
        JournalScientifique jScientifique = new JournalScientifique();
    
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM JournalScientifique J, Document D WHERE J.document_id = D.id AND D.id = ?   ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
            
                jScientifique.setId(rs.getLong("id"));
                jScientifique.setTitre(rs.getString("titre"));
                jScientifique.setAuteur(rs.getString("auteur"));
                jScientifique.setDatePublication(rs.getDate("datePublication"));
                jScientifique.setNombresPages(rs.getInt("nombreDePages"));
                jScientifique.setType(rs.getString("type"));
                jScientifique.setdomaineRecherche(rs.getString("domainerecherche"));

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
        return jScientifique; 
    }


    @Override
    public boolean updateJournalScientifiqueDao(JournalScientifique journalScientifique) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        
        
        String query1 = "UPDATE Document SET titre = ?, auteur = ?, datePublication = ?, nombreDePages = ?, type = ? WHERE id = ?";
        String query2 = "UPDATE JournalScientifique SET domaineRecherche = ? WHERE document_id = ?";
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            con.setAutoCommit(false);
        
            ps1 = con.prepareStatement(query1);
            ps1.setString(1, journalScientifique.getTitre());
            ps1.setString(2, journalScientifique.getAuteur());
            ps1.setDate(3, new java.sql.Date(journalScientifique.getDatePublication().getTime()));
            ps1.setLong(4, journalScientifique.getNombresPages());
            ps1.setString(5, journalScientifique.getType());
            ps1.setLong(6, journalScientifique.getId()); 
        
            int n1 = ps1.executeUpdate();
            
            if (n1 > 0) {
                ps2 = con.prepareStatement(query2);
                ps2.setString(1, journalScientifique.getdomaineRecherche());
                ps2.setLong(2, journalScientifique.getId()); 
        
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
