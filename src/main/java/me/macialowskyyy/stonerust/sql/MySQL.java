package me.macialowskyyy.stonerust.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private Boolean ssl;

    private Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect(String hosta, String porta, String databasea, String usernamea, String passworda, Boolean ssla) throws ClassNotFoundException, SQLException {
        host = hosta;
        port = porta;
        database = databasea;
        username = usernamea;
        password = passworda;
        ssl = ssla;
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://"+ host +":3306/" +  database +"?"
                    + "user=" + username + "&password=" + password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
