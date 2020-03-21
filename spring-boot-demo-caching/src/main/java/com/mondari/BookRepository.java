package com.mondari;

public interface BookRepository {

    Book getByIsbn(String isbn);

}