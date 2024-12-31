package com.library_mng.LibraryManagement.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Name;


}
