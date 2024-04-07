package com.javalincrud.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javalincrud.model.User;
import com.javalincrud.model.DAO.UserDAO;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getUserById(int id) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        User foundUser = null;
        String sql = "SELECT * FROM users WHERE users.userId = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int userId = rs.getInt("userId");
            String username = rs.getString("username");
            String password = rs.getString("password");
            foundUser = new User(userId, username, password);
        }
        return foundUser;
    }

    @Override
    public User getUserByUsernamePass(String username, String pass) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        User foundUser = null;
        String sql = "SELECT * FROM users WHERE users.username = ? AND users.password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int userId = rs.getInt("userId");
            String foundUsername = rs.getString("username");
            String foundPassword = rs.getString("password");
            foundUser = new User(userId, foundUsername, foundPassword);
        }
        return foundUser;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        Connection con = DatabaseCon.getConnection();
        ArrayList<User> allUsers = new ArrayList<User>();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("userId");
            String username = rs.getString("username");
            String password = rs.getString("password");
            User foundUser = new User(userId, username, password);
            allUsers.add(foundUser);
        }
        return allUsers;
    }

    @Override
    public void deleteUserById(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUserById'");
    }

    @Override
    public void updateUser(User user) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public int createUser(String username, String password) throws SQLException {
        int userCreatedId = -1;
        Connection con = DatabaseCon.getConnection();
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            userCreatedId = rs.getInt(1);
        }
        return userCreatedId;
    }

    @Override
    public User getUserByUniqueUsername(String username) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        User foundUser = null;
        String sql = "SELECT * FROM users WHERE users.username = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int userId = rs.getInt("userId");
            String userName = rs.getString("username");
            String password = rs.getString("password");
            foundUser = new User(userId, userName, password);
        }
        return foundUser;
    }

}
