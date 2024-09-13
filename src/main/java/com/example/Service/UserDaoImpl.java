package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.Dao.UserDao;
import com.example.Model.Document;
import com.example.Model.JournalScientifique;
import com.example.Model.Livre;
import com.example.Model.Magazine;
import com.example.Model.Professor;
import com.example.Model.Student;
import com.example.Model.TheseUniversitaire;
import com.example.Model.Users;
import com.example.Utils.DatabaseConnection;

public class UserDaoImpl implements UserDao {

    private static Connection con = DatabaseConnection.getConnection();


    @Override
    public boolean emailIsAlreadyExist(String email) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
    
        String query = "SELECT * FROM Users U WHERE LOWER(U.email) = LOWER(?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
    
            if (rs.next()) { 
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Name: " + rs.getString("name"));
                return true; 
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
        return false; // Email does not exist
    }


    @Override
    public boolean addStudent(Student student) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "INSERT INTO Student (name, email, type_user, niveau_etude) VALUES (?, ?, ?, ?) ";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setString(1, student.getEmail());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getType());
            ps.setString(4, student.getNiveauEtude());

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
    public boolean addProfessor(Professor professor) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "INSERT INTO Professor (name, email, type_user, specialite) VALUES (?, ?, ?, ?) ";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setString(1, professor.getName());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getType_user());
            ps.setString(4, professor.getSpecialite());

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
    public List<Users> getListUsers() throws SQLException {
        List<Users> users = new ArrayList<>();
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Users  ";
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query1);

            while (rs.next()) {
                if(rs.getString("type_user").equals("Professor")){
                    Professor professor = new Professor();
                    professor.setId(rs.getLong("id"));
                    professor.setName(rs.getString("name"));
                    professor.setEmail(rs.getString("email"));
                    professor.setType_user(rs.getString("type_user"));
                    users.add(professor);
                }else{

                    Student student = new Student();
                    student.setId(rs.getLong("id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setType_user(rs.getString("type_user"));
                    users.add(student);
                }

            }
        
        
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return users; 
    }


    @Override
    public List<Users> getUserByName(String name) throws SQLException {
         List<Users> users = new ArrayList<>();
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Users WHERE LOWER(name) LIKE LOWER(?) ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setString(1, "%"+name+"%");
            rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type_user");
            if( type.equals("Student")){
                Student student = new Student();
                student.setId(rs.getLong("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setType_user(rs.getString("type_user"));
                users.add(student);
            }
            else if(type.equals("Professor")) {
                Professor professor = new Professor();
                professor.setId(rs.getLong("id"));
                professor.setName(rs.getString("name"));
                professor.setEmail(rs.getString("email"));
                professor.setType_user(rs.getString("type_user"));
                users.add(professor);

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

        return users; 
    }


    @Override
    public Student getStudentById(Long id) throws SQLException {
        Student student = new Student();
    
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Student  WHERE id = ?   ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
                student.setId(rs.getLong("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setType_user(rs.getString("type_user"));
                student.setNiveauEtude(rs.getString("niveau_etude"));


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
        return student; 
    }


    @Override
    public Professor getProfessorById(Long id) throws SQLException {
        Professor professor = new Professor();
    
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }


        String query1 = "SELECT * FROM Professor WHERE  id = ?   ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query1);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
                professor.setId(rs.getLong("id"));
                professor.setName(rs.getString("name"));
                professor.setEmail(rs.getString("email"));
                professor.setType_user(rs.getString("type_user"));
                professor.setSpecialite(rs.getString("specialite"));

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
        return professor; 
    }


    @Override
    public Boolean updateStudent(Student student) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String query = "UPDATE Student SET name = ? ,email = ? , niveau_etude = ? WHERE id = ?";

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getNiveauEtude());
            ps.setLong(4, student.getId());
            int n = ps.executeUpdate();
            return n == 1;
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
        
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        
        }
        
    }


    @Override
    public Boolean updateProfessor(Professor professor) throws SQLException {
        if (con == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        String query = "UPDATE Professor SET name = ? ,email = ? , specialite = ? WHERE id = ?";

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, professor.getName());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getSpecialite());
            ps.setLong(4, professor.getId());
            int n = ps.executeUpdate();
            return n == 1;
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
        
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        
        }
    }
    
}
