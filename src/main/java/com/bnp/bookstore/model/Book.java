package com.bnp.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Book implements Serializable {

    @Id
    private String isbn;

    private String title;
    private String author;
    private Double price;

    private int stock;
}
