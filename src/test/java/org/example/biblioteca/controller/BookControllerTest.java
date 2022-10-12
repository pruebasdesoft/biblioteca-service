package org.example.biblioteca.controller;

import org.example.biblioteca.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

  private TestRestTemplate testRestTemplate;

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port + "/api/books");
    testRestTemplate = new TestRestTemplate(restTemplateBuilder);
  }

  @Test
  void hola() {
    ResponseEntity<String> response = testRestTemplate.getForEntity("/hola", String.class);
    assertEquals( HttpStatus.OK, response.getStatusCode());
    assertEquals( 200, response.getStatusCodeValue());
    assertEquals( "Hola book", response.getBody());
  }

  @Test
  void findAll() {
    ResponseEntity<Book[]> response = testRestTemplate.getForEntity("/", Book[].class);
    assertEquals( HttpStatus.OK, response.getStatusCode());
    assertEquals( 200, response.getStatusCodeValue());

    List<Book> books = Arrays.asList(response.getBody());
    System.out.println("Cantidad de libros: " + books.size());
  }

  @Test
  void finById() {
    ResponseEntity<Book> response = testRestTemplate.getForEntity("/1", Book.class);
    assertEquals( HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void create() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    String json = "{\"title\": \"Los escoleros\",\"author\": \"Arguedas\",\"pages\": 233,\"price\": 75.5,\"releaseDate\": \"2022-10-07\",\"online\": true}";

    HttpEntity<String> request = new HttpEntity<>(json, headers);
    ResponseEntity<Book> response = testRestTemplate.exchange("/", HttpMethod.POST, request, Book.class);
    Book result = response.getBody();

    assertEquals(1L, result.getId());

  }

}