package com.example.Service;

import java.sql.SQLException;
import java.util.List;

import com.example.Dao.DocumentDao;
import com.example.Model.Document;
import com.example.Model.Livre;
import com.example.Model.Magazine;

public class DocumentDaoImpl implements DocumentDao {

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLivre'");
    }

    @Override
    public boolean addMagazine(Magazine magazine) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMagazine'");
    }
    // private 
}
