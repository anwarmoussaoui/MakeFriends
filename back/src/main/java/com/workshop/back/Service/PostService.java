package com.workshop.back.Service;

import com.workshop.back.DTO.PostDTO;
import com.workshop.back.Entity.Comment;
import com.workshop.back.Entity.Likes;
import com.workshop.back.Entity.Post;
import com.workshop.back.Repository.CommentRepo;
import com.workshop.back.Repository.LikeRepo;
import com.workshop.back.Repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo repo;
    private final CommentRepo commentRepo;
    private final LikeRepo likeRepo;

    private final StorageService storageService;
    public Post createPost(PostDTO post,MultipartFile file) throws IOException {
        return repo.save(DTOToPost(post,file));
    }
    public List<Post>   HomePage (String userEmail){
        List<Post> posts = repo.findPostsByFollowing(userEmail);
        for (Post post : posts) {
            int likesCount = post.getLikes().size(); // Assuming getLikes() returns the list of likes
            post.setCountLikes(likesCount); // Set countLikes transient field in Post entity
        }
        return posts;
    }
    public  List<Post> ListOfPosts(String email){
        List<Post> posts = repo.findPostsByOwnerEmail(email);
        for (Post post : posts) {
            if(!post.getLikes().isEmpty()){
            int likesCount = post.getLikes().size(); // Assuming getLikes() returns the list of likes
            post.setCountLikes(likesCount);} // Set countLikes transient field in Post entity
        }
        return posts;
    }
    public Comment addComment(Long id, Comment comment) {
        Optional<Post> optionalPost = repo.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Add the comment to the post
            post.getComments().add(comment);// Set the owning post for the comment
            commentRepo.save(comment);
            // Save the updated post (which includes the new comment)
            repo.save(post);

            return comment;
        } else {
            return null;
        }
    }
    public int addLike(Long postId, Likes like) {
        Optional<Post> optionalPost = repo.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            String user = like.getUser(); // Assuming Likes entity has a User reference

            // Check if the user has already liked the post
            Optional<Likes> existingLike = post.getLikes().stream()
                    .filter(l -> l.getUser().equals(user))
                    .findFirst();

            if (existingLike.isPresent()) {
                // User has already liked the post, so remove the like
                post.getLikes().remove(existingLike.get());
                likeRepo.delete(existingLike.get());
            } else {
                // User hasn't liked the post, so add the like
                post.getLikes().add(like);
                likeRepo.save(like);
            }

            // Save the updated post (which includes the new or removed like)
            repo.save(post);

            return post.getCountLikes();
        } else {
            return 0;
        }
    }

    public PostDTO postToDto(Post post) throws IOException {
        PostDTO postDTO=new PostDTO();
        postDTO.setComments(post.getComments());
        postDTO.setContent(post.getContent());
        postDTO.setId(post.getId());
        postDTO.setEmail(postDTO.getEmail());
        postDTO.setCountLikes(post.getCountLikes());
        postDTO.setImgUrl(storageService.downloadImageFromFileSystem(post.getImgUrl()));
        return postDTO;
    }
    public Post DTOToPost(PostDTO post, MultipartFile file) throws IOException {
        Post postDTO=new Post();
        postDTO.setContent(post.getContent());
        postDTO.setEmail(post.getEmail());
        postDTO.setImgUrl(storageService.uploadImageToFileSystem(file));
        return postDTO;
    }
    }


