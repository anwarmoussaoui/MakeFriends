package com.workshop.back.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
Optional<User> findUtilisateurByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    List<User> findTop5ByEmail(@Param("email") String email, Pageable pageable);

}
