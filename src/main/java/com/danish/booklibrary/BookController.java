package com.danish.booklibrary;


import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String genre){

        if (genre != null && !genre.isBlank()){
            return bookService.getBookByGenre(genre);
        }
        return bookService.getAllBooks();
    }

    @GetMapping("{id}")
    public Book getBooksById(@PathVariable Long id){
        return bookService.getBook(id);
    }


    @PostMapping
    public void addBook(@RequestBody Book book){
        bookService.insertBook(book);
    }


    @DeleteMapping("{id}")
    public void removeBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book book){
        bookService.setBook(id, book);
        return ResponseEntity.ok("Updated successfully");
    };

    @PatchMapping("{id}/read")
    public ResponseEntity<String> markBookAsRead(@PathVariable Long id){
        bookService.markAsRead(id);
        return ResponseEntity.ok("Book marked as read");

    }


}
