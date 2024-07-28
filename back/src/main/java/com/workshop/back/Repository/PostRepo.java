package com.workshop.back.Repository;

import com.workshop.back.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {


    @Query("SELECT p FROM Post p WHERE p.email IN (SELECT f.email FROM User u JOIN u.following f WHERE u.email = :userEmail)")
    List<Post> findPostsByFollowing(@Param("userEmail") String userEmail);

    @Query("SELECT p FROM Post p  WHERE p.email = :ownerEmail")
    List<Post> findPostsByOwnerEmail(String ownerEmail);
}
