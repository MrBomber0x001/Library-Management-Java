package com.example.demofx;

public class Book {
    private Integer id;
    private String title;
    private String author_name;
    private String status;
    private int user_id;
    public Book(Integer id, String title, String author_name, String status, Integer user_id){
        this.setId(id);
        this.setUser_id(user_id);
        this.setStatus(status);
        this.setAuthor_name(author_name);
        this.setTitle(title);
    }
    public Book(String title, String author_name, String status) {
        this.setId(id);
        this.setTitle(title);
        this.setAuthor_name(author_name);
        this.setStatus(status);
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer book_id) {
        this.id = book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
