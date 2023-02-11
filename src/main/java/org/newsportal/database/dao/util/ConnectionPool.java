package org.newsportal.database.dao.util;

import org.newsportal.database.dao.entity.User;
import org.newsportal.database.dao.UserDao;
import org.newsportal.database.dao.impl.UserDaoImpl;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final int MAX_CONNECTIONS = 10;
    private static final String PROPERTIES_FILENAME = "src/main/resources/db.properties";


    private static ConnectionPool connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private BlockingQueue<Connection> blockingQueue = new ArrayBlockingQueue<Connection>(MAX_CONNECTIONS);


    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                blockingQueue.put(createConnection());
            } catch (InterruptedException | SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConnectionPool getConnectionPool() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    private Connection createConnection() throws IOException, SQLException {
        FileReader reader = new FileReader(PROPERTIES_FILENAME);
        Properties properties = new Properties();
        properties.load(reader);
        return DriverManager.getConnection(properties.getProperty("url"),
                                            properties.getProperty("username"),
                                            properties.getProperty("password"));
    }

    public synchronized Connection getConnection() throws InterruptedException {
//        if (blockingQueue.isEmpty()) {
//            if (usedConnections.size() < MAX_CONNECTIONS) {
//                try {
//                    blockingQueue.put(createConnection());
//                } catch (InterruptedException | IOException | SQLException e) {
//                    System.err.println("Maximum pool size reached. No available connections");
//                }
//            }
//        }
//        Connection connection = blockingQueue.remove();
//        usedConnections.add(connection);
        return blockingQueue.take();
    }

    public synchronized boolean releaseConnection(Connection connection) {
        blockingQueue.add(connection);
        return usedConnections.remove(connection);
    }

    public int getConnectionPoolSize() {
        return blockingQueue.size();
    }

    public static void main(String[] args) {
        User user1 = new User(2L, "Johny", "qwerty12345");
        User user2 = new User(3L, "Peter", "12345678");
        User user3 = new User(4L, "Pope", "holyJesus");

        UserDao userDao = new UserDaoImpl();
        userDao.createUser(user1);
        userDao.createUser(user2);
        userDao.createUser(user3);

        User user = userDao.findByUsername("vlokky");
        userDao.deleteUserById(4L);
        userDao.findAll().forEach(System.out::println);
        System.out.printf("User %s with id: %d\n", user.getUsername(), user.getId());
        System.out.println("Users in storage: " + userDao.findAll().size());

    }

}
