package com.workshop.back.Controller;

import com.workshop.back.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String email;
    private String name;
    private String nickname;
    private List<Post> posts;
    private String img;

}
