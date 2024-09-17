package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.Dao.DocumentDao;
import com.example.Model.Document;
import com.example.Model.JournalScientifique;
import com.example.Model.Livre;
import com.example.Model.Magazine;
import com.example.Model.TheseUniversitaire;
import com.example.Utils.DatabaseConnection;

public class DocumentDaoImpl implements DocumentDao {

    private static Connection con = DatabaseConnection.getConnection();

    @Override
    public List<Document> getDocumentByName(String name) throws SQLException {
        List<Document> documents = new ArrayList<>();
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Document WHERE LOWER(titre) LIKE LOWER(?) ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setString(1, "%"+name+"%");
            rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
            if( type.equals("Magazine")){
                // System.out.println(rs.getString("titre")+" "+type);
                // Magazine magazine = new Magazine(rs.getString("titre"),rs.getString("auteur"), rs.getDate("datePublication"), rs.getInt("nombreDePages"), "Magazine", 0L);
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
                // System.out.println(rs.getString("titre")+" "+type);
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
                // System.out.println(rs.getString("titre")+" "+type);
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
                // System.out.println(rs.getString("titre")+" "+type);
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

        return documents; 
    }

   

    @Override
    public List<Document> allDocuments() throws SQLException {
         List<Document> documents = new ArrayList<>();
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Document  ";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query1);

            while (rs.next()) {
                String type = rs.getString("type");
            if( type.equals("Magazine")){
                // System.out.println(rs.getString("titre")+" "+type);
                // Magazine magazine = new Magazine(rs.getString("titre"),rs.getString("auteur"), rs.getDate("datePublication"), rs.getInt("nombreDePages"), "Magazine", 0L);
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
                // System.out.println(rs.getString("titre")+" "+type);
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
                // System.out.println(rs.getString("titre")+" "+type);
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
                // System.out.println(rs.getString("titre")+" "+type);
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
        } 

        return documents;    
    }

    @Override
    public Document getDocumentById(Long id) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }

        String query = "SELECT * FROM Document WHERE id = ?";
        Document document = null;
    
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("type");
                    if( type.equals("Magazine")){
                        document = new Magazine();
                        document.setId(rs.getLong("id"));
                        document.setTitre(rs.getString("titre"));
                        document.setAuteur(rs.getString("auteur"));
                        document.setDatePublication(rs.getDate("datePublication"));
                        document.setNombresPages(rs.getInt("nombreDePages"));
                        document.setType(type);
                    }
                    else if(type.equals("Livre")) {
                        document = new Livre();
                        document.setId(rs.getLong("id"));
                        document.setTitre(rs.getString("titre"));
                        document.setAuteur(rs.getString("auteur"));
                        document.setDatePublication(rs.getDate("datePublication"));
                        document.setNombresPages(rs.getInt("nombreDePages"));
                        document.setType(type);
        
                    }
                    else if(type.equals("JournalScientifique")) {
                        document = new JournalScientifique();
                        document.setId(rs.getLong("id"));
                        document.setTitre(rs.getString("titre"));
                        document.setAuteur(rs.getString("auteur"));
                        document.setDatePublication(rs.getDate("datePublication"));
                        document.setNombresPages(rs.getInt("nombreDePages"));
                        document.setType(type);
                            
        
                    }
                    else if(type.equals("TheseUniversitaire")) {
                        document = new TheseUniversitaire();
                        document.setId(rs.getLong("id"));
                        document.setTitre(rs.getString("titre"));
                        document.setAuteur(rs.getString("auteur"));
                        document.setDatePublication(rs.getDate("datePublication"));
                        document.setNombresPages(rs.getInt("nombreDePages"));
                        document.setType(type);
        
                            
        
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
        return document; 
        
    }

 
    @Override
    public List<Document> availableDocuments() throws SQLException {
        List<Document> documents = new ArrayList<>();
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Document WHERE available = 1  ";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query1);

            while (rs.next()) {
                String type = rs.getString("type");
            if( type.equals("Magazine")){
                // System.out.println(rs.getString("titre")+" "+type);
                // Magazine magazine = new Magazine(rs.getString("titre"),rs.getString("auteur"), rs.getDate("datePublication"), rs.getInt("nombreDePages"), "Magazine", 0L);
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
                // System.out.println(rs.getString("titre")+" "+type);
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
                // System.out.println(rs.getString("titre")+" "+type);
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
                // System.out.println(rs.getString("titre")+" "+type);
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
        } 

        return documents;    
    }

    @Override
    public boolean isAvailable(Long id) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }

        String query = "SELECT available FROM Document WHERE id = ?";

        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = con.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("available");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 

    }
    
    
}
