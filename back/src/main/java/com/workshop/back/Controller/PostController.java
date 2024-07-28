package com.workshop.back.Controller;

import com.workshop.back.DTO.PostDTO;
import com.workshop.back.Entity.Comment;
import com.workshop.back.Entity.Likes;
import com.workshop.back.Entity.Post;
import com.workshop.back.Service.PostService;
import com.workshop.back.users.User;
import com.workshop.back.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")

public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping("/add")
    public Post addPost(@RequestParam("content") String content , @RequestParam("file") MultipartFile file,
                        @RequestParam("email") String email) throws IOException {
        PostDTO postDTO =new PostDTO();
        postDTO.setContent(content);
        postDTO.setEmail(email);
        return postService.createPost(postDTO,file);
    }

    @GetMapping("/{email}")
    public List<Post> Profile(@PathVariable String email){
        return postService.ListOfPosts(email);
    }

    @GetMapping("/profile/{email}")
    public List<Post> Dashboard(@PathVariable String email){
        return postService.HomePage(email);
    }

    @PostMapping("/comment/{id}")
    public Comment addComment(@RequestBody Comment comment,@PathVariable Long id){
        Optional<User> user=userRepository.findUtilisateurByEmail(comment.getOwner());
        if(user.isPresent()){
            User user1=user.get();
            comment.setOwnerPic(user1.getProfileImage());
        }

        return postService.addComment(id,comment);
    }
    @PostMapping("/likes/{id}")
    public int addLike(@RequestBody Likes likes, @PathVariable Long id){
        return postService.addLike(id,likes);
    }
}
