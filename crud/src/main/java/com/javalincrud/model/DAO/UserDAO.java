package com.javalincrud.model.DAO;

import com.javalincrud.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User getUserById(int id) throws SQLException;

    User getUserByUsernamePass(String username, String pass) throws SQLException;

    User getUserByUniqueUsername(String username) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    void deleteUserById(int id) throws SQLException;

    void updateUser(User user) throws SQLException;

    int createUser(String username, String password) throws SQLException;

}