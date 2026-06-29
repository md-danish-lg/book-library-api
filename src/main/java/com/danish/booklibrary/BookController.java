package com.danish.booklibrary;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookController {

    private final BookService bookService;
    private final AiService aiService;

    public BookController(BookService bookService, AiService aiService) {
        this.bookService = bookService;
        this.aiService = aiService;
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

    @GetMapping("{id}/summarize")
    public ResponseEntity<String> summarizeBook(@PathVariable Long id){
        Book book = bookService.getBook(id);
        String summary = aiService.summarize(book.getTitle() + " by " + book.getAuthor());
        return ResponseEntity.ok(summary);
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
