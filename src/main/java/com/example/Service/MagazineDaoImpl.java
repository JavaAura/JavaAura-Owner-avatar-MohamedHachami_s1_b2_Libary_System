package com.example.Service;

import com.example.Dao.MagazineDao;
import com.example.Model.Magazine;
import com.example.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MagazineDaoImpl implements MagazineDao {

    private static Connection con = DatabaseConnection.getConnection();

    

        @Override
    public boolean addMagazine(Magazine magazine) throws SQLException {
    if (con == null) {
        throw new SQLException("Database connection is not initialized.");
    }

    String type = "Magazine";

    String query1 = "INSERT  INTO Document (titre, auteur, datePublication, nombreDePages, type,available) VALUES (?, ?, ?, ?, ? ,?)  RETURNING id";
    String query2 = "INSERT INTO Magazine (document_id, numero) VALUES (?, ?) ";

    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;


    try {
            con.setAutoCommit(false);
            ps1 = con.prepareStatement(query1);
            ps1.setString(1, magazine.getTitre());
            ps1.setString(2, magazine.getAuteur());
            ps1.setDate(3, new java.sql.Date(magazine.getDatePublication().getTime()));
            ps1.setLong(4, magazine.getNombresPages());
            ps1.setString(5, type);
            ps1.setInt(6, 1);

            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                long documentId = rs1.getLong(1); 
    
                ps2 = con.prepareStatement(query2);
                ps2.setLong(1, documentId);
                ps2.setString(2, magazine.getNumero());
    
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
    public boolean deleteMagazine(Long id) throws SQLException {
        String deleteMagazineQuery = "DELETE FROM Magazine WHERE document_id = ?";
        String deleteDocumentQuery = "DELETE FROM Document WHERE id = ?";

        PreparedStatement deleteMagazineStmt = null;
        PreparedStatement deleteDocumentStmt = null;

        try {
            con.setAutoCommit(false);

            deleteMagazineStmt = con.prepareStatement(deleteMagazineQuery);
            deleteMagazineStmt.setLong(1, id);
            int n1 = deleteMagazineStmt.executeUpdate();

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
            if (deleteMagazineStmt != null) {
                deleteMagazineStmt.close();
            }
            if (deleteDocumentStmt != null) {
                deleteDocumentStmt.close();
            }
            con.setAutoCommit(true);
        }
    }



    @Override
    public Magazine getMagazineById(Long id) throws SQLException {
        Magazine magazine = new Magazine();
    
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Magazine M, Document D WHERE M.document_id = D.id AND D.id = ?   ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
            
                magazine.setId(rs.getLong("id"));
                magazine.setTitre(rs.getString("titre"));
                magazine.setAuteur(rs.getString("auteur"));
                magazine.setDatePublication(rs.getDate("datePublication"));
                magazine.setNombresPages(rs.getInt("nombreDePages"));
                magazine.setType(rs.getString("type"));
                magazine.setNumero(rs.getString("numero"));

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
        return magazine; 
    }



    @Override
    public boolean updateMagazine(Magazine magazine) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        
        
        String query1 = "UPDATE Document SET titre = ?, auteur = ?, datePublication = ?, nombreDePages = ?, type = ? WHERE id = ?";
        String query2 = "UPDATE Magazine SET numero = ? WHERE document_id = ?";
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            con.setAutoCommit(false);
        
            ps1 = con.prepareStatement(query1);
            ps1.setString(1, magazine.getTitre());
            ps1.setString(2, magazine.getAuteur());
            ps1.setDate(3, new java.sql.Date(magazine.getDatePublication().getTime()));
            ps1.setLong(4, magazine.getNombresPages());
            ps1.setString(5, magazine.getType());
            ps1.setLong(6, magazine.getId()); 
        
            int n1 = ps1.executeUpdate();
            
            if (n1 > 0) {
                ps2 = con.prepareStatement(query2);
                ps2.setString(1, magazine.getNumero());
                ps2.setLong(2, magazine.getId()); 
        
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
