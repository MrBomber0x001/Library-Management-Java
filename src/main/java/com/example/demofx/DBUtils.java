package com.example.demofx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.DCount;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;

public class DBUtils {


    //@changeScene -> changes the scene to LoggedIn Dashboard after signing up or logging in.
    public static void changeScene(ActionEvent event, String fxmlFile, String title, Integer userId, String username, String email, ObservableList<Book> list){
        Parent root = null;
        if(username != null && email != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load(); // we're passing data between different scenes.
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(userId, username, email, list);
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

    public static void changeSceneToAddingBooks(){

    }


    public static void signupUser(ActionEvent event, String username, String email, String password, String gender) {
        PreparedStatement psCheckUserExistes = null;
        PreparedStatement prepared = null;
        Connection connection = connectDb();
        ResultSet result = null;
        try{

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
                 prepared = connection.prepareStatement("SELECT user_id FROM users where username = ?");
                 prepared.setString(1, username);
                 result = prepared.executeQuery();
                 while(result.next()){
                     int user_id = result.getInt("user_id");
                     // change scene after signing up to the login
                     changeScene(event, "logged-in.fxml", "Welcome", user_id, username, email, null);
                 }
             }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(result, prepared, psCheckUserExistes, connection);
        }
    }

    public void admin_getAllUsers(){
        Connection connect = connectDb();
        PreparedStatement prepared = null;
        ResultSet result = null;
        try{
            prepared = connect.prepareStatement("");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void admin_deleteUser(){
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javafx_demo", "root", "password");
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
            assert connection != null;
            prepared = connection.prepareStatement("SELECT user_id, password, email FROM users WHERE username = ?");
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
                    int user_id = result.getInt("user_id");
                    if(retrievedPassword.equals(password)){
                        ObservableList<Book> list = getBookList(user_id);
                        changeScene(event, "logged-in.fxml","Welcome",user_id, username, retrivedEmail, list);
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

    public static ObservableList<Book> getBookList(Integer user_id) {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        PreparedStatement prepared = null;
        Connection connection = DBUtils.connectDb();
        ResultSet result = null;
        Book book;
        try{
            prepared = connection.prepareStatement("SELECT * FROM books where user_id = ?");
            prepared.setInt(1, user_id);
            result = prepared.executeQuery();
            while(result.next()){
                int book_id = result.getInt("id");
                String title = result.getString("title");
                String author_name = result.getString("author_name");
                String status = result.getString("status");
                book = new Book(book_id, title, author_name, status, user_id);
                bookList.add(book);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }


    public static void updateBook(Book book){
        Connection connection = connectDb();
        PreparedStatement prepared;
        try{
            prepared = connection.prepareStatement("UPDATE books SET title = ?, author_name = ?, status = ? WHERE id = ?");
            prepared.setString(1, book.getTitle());
            prepared.setString(2, book.getAuthor_name());
            prepared.setString(3, book.getStatus());
            prepared.setInt(4, book.getId());
            prepared.executeUpdate();
            System.out.println("updated");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void deleteBook(Book book){
        Connection connection = connectDb();
        PreparedStatement prepared;
        try{
            prepared = connection.prepareStatement("DELETE FROM books WHERE id = ?");
            prepared.setInt(1, book.getId());
            prepared.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }



    public static int getTotalBooks(Integer userId){
        Connection connection = connectDb();
        PreparedStatement prepared;
        ResultSet result;
        int count = 0;
        try {
            prepared = connection.prepareStatement("SELECT COUNT(id) FROM books WHERE user_id = ? ");
            prepared.setInt(1, userId);
            result = prepared.executeQuery();
            while(result.next()){
                count += result.getInt("COUNT(id)");
            }
            return count;
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void admin_getUsersCount() {
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

}
