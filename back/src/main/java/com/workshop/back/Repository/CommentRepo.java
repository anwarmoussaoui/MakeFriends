package com.workshop.back.Repository;

import com.workshop.back.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Long> {

}
