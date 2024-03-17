package com.javalincrud.model.DAO;

import com.javalincrud.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User findUserById(int id) throws SQLException;

    User findUserByUsernamePass(String username, String pass) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    void deleteUserById(int id) throws SQLException;

    void updateUser(User user) throws SQLException;

    int createUser(User user) throws SQLException;

}