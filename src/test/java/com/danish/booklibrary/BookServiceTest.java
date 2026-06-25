package com.danish.booklibrary;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;



@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock private BookRepository bookRepository;
    private BookService underTest;


    @BeforeEach
    void setUp(){
        underTest = new BookService(bookRepository);

    }


    @Test
    void canGetAllBooks() {
        underTest.getAllBooks();

        verify(bookRepository).findAll();
    }


    @Test
    void throwWhenIdNotFound(){
        Long id = 5L;

        assertThatThrownBy(()-> underTest.getBook(id))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book with id " + id + " not found");
    }

    @Test
    void getCorrectBookById(){
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setTitle("Clean code");

        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        Book result = underTest.getBook(id);
        assertThat(result).isEqualTo(book);

    }
    @Test
    void markAsRead() {
        Long id = 1L;
        Book book =new Book();
        book.setId(id);
        book.setReadStatus(false);

        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        underTest.markAsRead(id);

        assertThat(book.isReadStatus()).isTrue();
        verify(bookRepository).save(book);
    }
}