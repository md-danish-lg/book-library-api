package com.danish.booklibrary;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void insertBook(Book book) {
        bookRepository.save(book);
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(
                ()-> new BookNotFoundException(id));
    }


    public List<Book> getBookByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }

    public void deleteBook(Long id) {
        if(!(bookRepository.existsById(id))){
            throw new BookNotFoundException(id);
        }

        bookRepository.deleteById(id);
    }

    public void setBook(Long id, Book book) {
        if(!(bookRepository.existsById(id))){
            throw new BookNotFoundException(id);
        }

        book.setId(id);
        bookRepository.save(book);
    }


    public void markAsRead(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new BookNotFoundException(id));

        book.setReadStatus(true);
        bookRepository.save(book);
    }
}
