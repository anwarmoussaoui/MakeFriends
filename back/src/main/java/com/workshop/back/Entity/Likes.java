package com.workshop.back.Entity;

import com.workshop.back.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    private String user; // The user who liked the post

    // Constructors, getters, setters, etc.

    // Constructors, getters, setters, etc.
}
