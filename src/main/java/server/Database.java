package server;

import java.sql.*;
import java.util.Date;

public final class Database {

    private static Database instance;

    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/webchat?currentSchema=public", "postgres","11011971");
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String login, String password, String nickname) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO users (login, password, nickname) VALUES (?, ?, ?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nickname);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String sender, String receiver, String message) {
        try {
            Date date = new Date();
            preparedStatement = connection.prepareStatement("INSERT INTO messages (sender, receiver, date, message) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, receiver);
            preparedStatement.setString(3, date.toString());
            preparedStatement.setString(4, message);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getNickname(String login, String password) {
        try {
            preparedStatement = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ? ");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            String nickname = null;
            while (resultSet.next()) {
                nickname = resultSet.getString("nickname");
            }
            return nickname;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean getField(String field, String value) {
        try {
            preparedStatement = connection.prepareStatement("SELECT " + field + " FROM users WHERE " + field + " = ?");
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean bool = false;
            while (resultSet.next()) {
                bool = true;
            }
            return bool;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void disconnect() {
        try {
            connection.close();
            System.out.println("Disconnected to database!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}