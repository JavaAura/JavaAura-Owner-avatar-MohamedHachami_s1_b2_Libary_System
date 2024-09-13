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
    public boolean emprunterDocuemnt(Long id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'emprunterDocuemnt'");
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
        return null;
       
    }

    @Override
    public boolean returnDocuement(Long id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnDocuement'");
    }
    







    
}
