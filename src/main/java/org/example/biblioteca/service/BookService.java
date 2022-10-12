package org.example.biblioteca.service;

import org.example.biblioteca.entities.Book;

public class BookService {
  public double calcularPrecioEnvio(Book book) {
    double precio = book.getPrice();
    if (book.getPages() > 300) {
      precio += 5;
    }
    precio+=2.99;
    return precio;
  }
}
