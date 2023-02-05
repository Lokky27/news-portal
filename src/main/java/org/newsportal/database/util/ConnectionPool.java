package org.newsportal.database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final String URL = "jdbc:postgresql://localhost:5432/news_portal_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "buputi2789";
    private static final int MAX_CONNECTIONS = 10;
    private static final int MIN_CONNECTIONS = 5;

    private static ConnectionPool connectionPool;
    private BlockingQueue<Connection> blockingQueue = new ArrayBlockingQueue<Connection>(MAX_CONNECTIONS);


    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < MIN_CONNECTIONS; i++) {
            try {
                blockingQueue.put(createConnection());
            } catch (InterruptedException | SQLException e) {
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

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public synchronized Connection getConnection() {

        return null;
    }


}
