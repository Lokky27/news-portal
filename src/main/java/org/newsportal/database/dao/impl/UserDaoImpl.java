package org.newsportal.database.dao.impl;

import org.newsportal.database.dao.entity.User;
import org.newsportal.database.dao.UserDao;
import org.newsportal.database.dao.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users";
    public static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
    public static final String FIND_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username=?";

    private Connection connection;

    public UserDaoImpl() {
        try {
            connection = ConnectionPool.getConnectionPool().getConnection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_QUERY);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        PreparedStatement preparedStatement;
        User user = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(id,
                        resultSet.getString("username"),
                        resultSet.getString("password"));
            }
            connection.commit();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByUsername(String userName) {
        PreparedStatement preparedStatement;
        User user = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_USERNAME_QUERY);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                user = new User(resultSet.getLong("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"));
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        String addUserQuery = "INSERT INTO users VALUES(?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(addUserQuery);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;
    }

    @Override
    public User updateUserById(Long id, User user) {

        return user;

    }

    @Override
    public void deleteUserById(Long id) {
        String deleteUserByIdQuery = "DELETE FROM users WHERE id=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(deleteUserByIdQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
