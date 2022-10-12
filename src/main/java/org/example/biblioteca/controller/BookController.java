package org.example.biblioteca.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.biblioteca.entities.Book;
import org.example.biblioteca.repository.BookRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/books")

public class BookController {
  private final Logger log = LoggerFactory.getLogger(BookController.class);
  private BookRepository bookRepository;

  @Value("${app.bienvenida}")
  String bienvenida;

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @GetMapping("/hola")
  public String hola() {
    System.getenv().forEach(
            (key, value) -> System.out.println(key + " - " + value)
    );
    return "Controlador book - " + bienvenida;
  }

  /**
   * Selección de todos los libros registrados en BD.
   * @return Listado de libros.
   */
  @GetMapping("")
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  /**
   * Retorna un libro según el id del libro a consultar.
   * @param id Código del libro a obtener.
   * @return Datos deli libro.
   */
  @ApiOperation("Obtiene un libro según su id.")
  @GetMapping("/{id}")
  public ResponseEntity<Book> finById(@ApiParam("id del libro.") @PathVariable Long id) {
    Optional<Book> bookOptional = bookRepository.findById(id);
    if (bookOptional.isPresent())
      return ResponseEntity.ok(bookOptional.get());
    else
      return ResponseEntity.notFound().build();
  }

  /**
   * Crea un libro.
   * @param book Datos del libro a crear.
   * @param headers Datos del header.
   * @return El libro insertado.
   */
  @PostMapping("")
  public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {
    System.out.println("Headers de la petición:" + headers.get("User-Agent"));
    if (book.getId() != null) {
      log.warn("No se puede crear un libro con id.");
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(bookRepository.save(book));
  }

  /**
   * Actualiza los datos de un libro.
   * @param book datos del libro a actualizar.
   * @return El libro actualizado.
   */
  @PutMapping()
  public ResponseEntity<Book> update(@RequestBody Book book) {
    if (book.getId() == null) {
      log.warn("No se puede actualizar un libro sin id.");
      return ResponseEntity.badRequest().build();
    }
    if (!bookRepository.existsById(book.getId())) {
      log.warn("El libro seleccionado no existe.");
      return ResponseEntity.notFound().build();
    }
    Book resultado = bookRepository.save(book);
    return ResponseEntity.ok(resultado);
  }

  /**
   * Elimina un libro según id.
   * @param id id del libro a eliminar.
   * @return Vacio si elimina el libro
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Book> deleteById(@PathVariable Long id) {
    if (!bookRepository.existsById(id)) {
      log.warn("El libro seleccionado no existe.");
      return ResponseEntity.notFound().build();
    }
    bookRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Elimina todos los libros.
   * @return Vacio si elimina los libros.
   */
  @ApiIgnore      // ignora el método para que no aparezca en la documentación con swagger
  @DeleteMapping("")
  public ResponseEntity<Book> deleteAll() {
    log.info("Se eliminó todos los libros.");

    bookRepository.deleteAll();
    return ResponseEntity.noContent().build();
  }
}
