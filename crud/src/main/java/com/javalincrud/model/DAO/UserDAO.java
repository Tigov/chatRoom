package com.javalincrud.model.DAO;

import com.javalincrud.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User getUserByUsernamePass(String username, String pass) throws SQLException;

    User getUserByUniqueUsername(String username) throws SQLException;

    List<User> getAllUsers() throws SQLException;


    int createUser(String username, String password) throws SQLException;

}