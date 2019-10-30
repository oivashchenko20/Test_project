package com.example.domain;

import javax.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String text;
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User author;

    private String filename;

    public Message() {
    }

    public Message(String text, User user, String title) {
        this.text = text;
        this.author = user;
        this.title = title;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title != null ? title.toString() : "<none>";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
