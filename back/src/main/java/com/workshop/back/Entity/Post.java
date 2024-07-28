package com.workshop.back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String imgUrl;
    @OneToMany
    private List<Likes> likes;
    @OneToMany

    private List<Comment> Comments;
    private String content;
    @Transient
    private int countLikes;
    public int getCountLikes() {

        if (likes != null) {
            return likes.size();
        }
        return 0;
    }



}
