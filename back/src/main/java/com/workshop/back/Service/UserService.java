package com.workshop.back.Service;

import com.workshop.back.users.User;
import com.workshop.back.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.awt.*;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getTop5UsersByEmail(String email) {
        Pageable pageable = PageRequest.of(0, 5);
        return userRepository.findTop5ByEmail(email, pageable);
    }

}
