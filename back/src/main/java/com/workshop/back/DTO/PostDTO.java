package com.workshop.back.DTO;

import com.workshop.back.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostDTO {
    private int id;
    private String email;
    private byte[] imgUrl;
    private List<Comment> Comments;
    private String content;

    private int countLikes;
}
