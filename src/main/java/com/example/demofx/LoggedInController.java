package com.example.demofx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    @FXML
    private Button btn_logout;
    @FXML
    private Label l_logout;
    @FXML
    private Label l_email;
    @FXML
    private Button btn_add_book;
    @FXML
    private Button btn_update_book;
    @FXML
    private Button btn_delete_book;
    @FXML
    private Label l_welcome;
    @FXML
    private Label l_id;

    @FXML
    private TableView table_view;

    @FXML
    private TextField tf_book_title;
    @FXML
    private TextField tf_book_author;
    @FXML
    private TextField tf_book_status;
    private static int user_id; // to make it permenent [used later for sql operations by user_id

    // Book operations
    @FXML
    private TableColumn<Book, Integer> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, String> colStatus;
    @Override
    public void initialize(URL location, ResourceBundle resources){

        showStudents();
        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "hello-view.fxml", "log in", null,null, null);
            }
        });


        btn_add_book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                l_welcome.setText("working!");
                showStudents();

            }
        });

        btn_delete_book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                l_welcome.setText("Working!!!!!");
            }
        });

        btn_update_book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                l_welcome.setText("Working!!!!!!!!!!!!!!!");
            }
        });
    }


    public void setUserInformation(int userId, String username, String email){
        l_logout.setText("Welcome" + username + "!");
        l_email.setText("Your email is:" +  email);
        l_id.setText("You Id: " + userId);
        user_id = userId;
    }


    public static void addbook(Book book){
        PreparedStatement psCheckUserExistes = null;
        PreparedStatement prepared = null;
        Connection connection = DBUtils.connectDb();


        ResultSet result = null;

        try{
            prepared = connection.prepareStatement("INSERT INTO books (title, author_name, status, user_id) VALUES (?,?,?,?);");
            prepared.setString(1, book.getTitle());
            prepared.setString(2, book.getAuthor_name());
            prepared.setString(3, book.getStatus());
            prepared.setInt(4, user_id);
            prepared.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void saveBook(){
        Book book = new Book(tf_book_title.getText(), tf_book_author.getText(), tf_book_status.getText());
        addbook(book);
    }

    public void deleteBook(){

    }

    public void updateBook(){

    }

    @FXML
    public void showStudents(){
        ObservableList<Book> list = getBookList();
        //Print to the console the list of books fetched per user
        for (int i = 0; i < list.size(); i++){
            System.out.println("book id: " + list.get(i).getId() + ", title: " + list.get(i).getTitle());
        }
        colId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author_name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
        table_view.setItems(list);
    }

    //@returns list of books from the databases based on user_id
    public ObservableList<Book> getBookList() {
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
}
