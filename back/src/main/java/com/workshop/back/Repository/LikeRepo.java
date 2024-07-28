package com.workshop.back.Repository;

import com.workshop.back.Entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Likes,Long> {
}
