package com.workshop.back.Service;

import com.workshop.back.users.User;
import com.workshop.back.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    public int Follow(String user, String profile) {
        Optional<User> Optuser = userRepository.findUtilisateurByEmail(user);
        Optional<User> Optprofile = userRepository.findUtilisateurByEmail(profile);

        if (Optuser.isPresent() && Optprofile.isPresent()) {
            User user1 = Optuser.get();
            User profile1 = Optprofile.get();

            // Check if the user is already following the profile
            if (user1.getFollowing().contains(profile1)) {
                user1.getFollowing().remove(profile1);
                profile1.getFollowers().remove(user1);
            } else {
                user1.getFollowing().add(profile1);
                profile1.getFollowers().add(user1);
            }

            userRepository.save(user1);
            userRepository.save(profile1);
            return profile1.getFollowers().size();
        }
        return 0;

    }
        public boolean isFollowing(String user, String profile){
            Optional<User> Optuser = userRepository.findUtilisateurByEmail(user);
            Optional<User> Optprofile = userRepository.findUtilisateurByEmail(profile);

            if (Optuser.isPresent() && Optprofile.isPresent()) {
                User user1 = Optuser.get();
                User profile1 = Optprofile.get();

                // Check if the user is already following the profile
                if (user1.getFollowing().contains(profile1)) {
                    return true;
                }
                    }else {
                return false;
            }
        return false;
} }
