package com.example.Dao;

import java.sql.SQLException;
import java.util.List;

import com.example.Model.Professor;
import com.example.Model.Student;
import com.example.Model.Users;

public interface UserDao {
    boolean emailIsAlreadyExist(String email) throws SQLException;
    boolean addStudent(Student student) throws SQLException;
    boolean addProfessor(Professor professor) throws SQLException;
    List<Users> getListUsers() throws SQLException;
    List<Users> getUserByName(String name) throws SQLException;
    Student getStudentById(Long id)throws SQLException;
    Professor getProfessorById(Long id)throws SQLException;
    Boolean updateStudent(Student student) throws SQLException;
    Boolean updateProfessor(Professor professor) throws SQLException;





}
