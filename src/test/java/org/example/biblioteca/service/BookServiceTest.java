package org.example.biblioteca.service;

import org.example.biblioteca.entities.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

  @Test
  void calcularPrecioEnvio() {
    Book book = new Book(1L,"Los rios profundos", "Arguedas",280, 75.90, LocalDate.of(1980,10,21), false);
    BookService bookService = new BookService();

    double precio = bookService.calcularPrecioEnvio(book);
    assertTrue(precio > 0);
    assertEquals(78.89, precio);
  }
}