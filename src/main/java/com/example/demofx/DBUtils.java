package com.example.demofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;

public class DBUtils {

    private Connection connect;
    private PreparedStatement prepared;
    private ResultSet result;

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, Integer age){
        Parent root = null;
        if(username != null && age != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load(); // we're passing data between different scenes.
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, age);
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
        try{
            connect = connectDb();
            prepared = connect.prepareStatement("SELECT COUNT(id) from users;");
            result = prepared.executeQuery();


        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void loginUser(String email, String password){
        try {

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void signupUser(String username, String email, String password) {
        try{
            //TODO: - check is user existed by email
            //TODO: - create user
             connect = connectDb();
             prepared = connect.prepareStatement("INSERT INTO users (username, email, password) VALUES (?,?,?);");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void getAllUsers(){
        try{
            connect = connectDb();
            prepared = connect.prepareStatement("");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteUser(){
        try{
            connect = connectDb();
            prepared = connect.prepareStatement("");
        } catch (SQLException e){
            e.printStackTrace();
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
}
