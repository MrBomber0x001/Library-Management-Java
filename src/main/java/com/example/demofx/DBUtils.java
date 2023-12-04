package com.example.demofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

//    private Connection connect;
//    private PreparedStatement prepared;
//    private ResultSet result;

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String email){
        Parent root = null;
        if(username != null && email != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load(); // we're passing data between different scenes.
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, email);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
    public void getUsersCount(ActionEvent event, String username, String password, int age) {
        int countData = 0;
        Connection connection = connectDb();
        PreparedStatement prepared = null;
        ResultSet result = null;
        try{
            prepared = connection.prepareStatement("SELECT COUNT(id) from users;");
            result = prepared.executeQuery();


        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void signupUser(ActionEvent event, String username, String email, String password, String gender) {
        PreparedStatement psCheckUserExistes = null;
        PreparedStatement prepared = null;
        Connection connection = connectDb();
        ResultSet result = null;
        try{
            //TODO: - check is user existed by email

            //TODO: - create user
             psCheckUserExistes= connection.prepareStatement("SELECT * FROM users WHERE username = ?");
             psCheckUserExistes.setString(1, username);
             result = psCheckUserExistes.executeQuery();

             if(result.isBeforeFirst()){ // isBeforeSet() is used to check if result it empty or not
                 System.out.println("User already exists");
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setContentText("You cannot use this email");
                 alert.show();
             } else {
                 prepared = connection.prepareStatement("INSERT INTO users (username, email, password, gender) VALUES (?, ?, ?, ?);");
                 prepared.setString(1, username);
                 prepared.setString(2, email); //TODO: hash the password first
                 prepared.setString(3, password);
                 prepared.setString(4, gender);
                 prepared.executeUpdate(); // returns nothing

                 // change scene after signing up to the login
                 changeScene(event, "logged-in.fxml", "Welcome", username, email);
             }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(result, prepared, psCheckUserExistes, connection);
        }
    }

    public void getAllUsers(){
        Connection connect = connectDb();
        PreparedStatement prepared = null;
        ResultSet result = null;
        try{
            prepared = connect.prepareStatement("");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteUser(){
        Connection connection = connectDb();
        PreparedStatement prepared = null;
        PreparedStatement psCheckUsersExists = null;
        ResultSet result = null;
        try{
            prepared = connection.prepareStatement("");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void closeConnection(ResultSet result, PreparedStatement prepared, PreparedStatement psCheckUserExistes, Connection connection){
        // we've to close resultSet, preparedStatements and connection in that order [optional]
        if(result != null){
            try{
                // the close must be wrapped within try/catch
                result.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        if(psCheckUserExistes != null){
            try{
                psCheckUserExistes.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        if(prepared != null){
            try{
                prepared.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (connection != null){
            try{
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connectiing .....");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javafx_db", "root", "password");
            System.out.println("Database Connected");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = connectDb();
        PreparedStatement prepared = null;
        ResultSet result = null;

//        if(username.length() == 0 || password.length() == 0){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("You must provide all of the credentials before logging in!");
//            alert.show();
//        }


        try{
            prepared = connection.prepareStatement("SELECT password, email FROM users WHERE username = ?");
            prepared.setString(1, username);
            result = prepared.executeQuery();
            if(!result.isBeforeFirst()){
                System.out.println("User not found!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are not correct!");
                alert.show();
            } else {
                while(result.next()){
                    String retrievedPassword = result.getString("password");
                    String retrivedEmail = result.getString("email");
                    if(retrievedPassword.equals(password)){
                        changeScene(event, "logged-in.fxml","Welcome",username, retrivedEmail);
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are not correct!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(result, prepared, null, connection);
        }
    }
}
