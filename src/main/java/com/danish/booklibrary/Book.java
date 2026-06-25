package com.danish.booklibrary;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;


    private String genre;
    private boolean readStatus = false;
    private LocalDate addedDate;


    @PrePersist
    protected void onCreate() {
        this.addedDate = LocalDate.now();
    }

    public Book() {
    }

    public Book(Long id, String title, String author, String genre, boolean readStatus, LocalDate addedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.readStatus = readStatus;
        this.addedDate = addedDate;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && readStatus == book.readStatus && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre) && Objects.equals(addedDate, book.addedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, readStatus, addedDate);
    }
}
