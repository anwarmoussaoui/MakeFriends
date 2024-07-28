package com.workshop.back.Controller;

import com.workshop.back.Service.ProfileService;
import com.workshop.back.Service.UserService;
import com.workshop.back.users.User;
import com.workshop.back.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final UserRepository userRepository;
    @GetMapping("/{email}")
    public int GetFollowers(@PathVariable String email) throws UnsupportedEncodingException {
        String encodedEmail =  URLDecoder.decode(email, StandardCharsets.UTF_8.toString());

        Optional<User> OPTuser=userRepository.findUtilisateurByEmail(encodedEmail);
        if(OPTuser.isPresent()){
            return OPTuser.get().getNumFollowers();
        }
        else {
            return 0;
        }
    }
    @PostMapping ("/follow/{user}/{profile}")
    public int follow(@PathVariable String user,@PathVariable String profile) throws UnsupportedEncodingException {
        String encodedEmail = URLDecoder.decode(user, StandardCharsets.UTF_8.toString());
        String encodedProfile = URLDecoder.decode(profile, StandardCharsets.UTF_8.toString());
        return profileService.Follow(encodedEmail,encodedProfile);
    }
    @GetMapping("/isFollowing/{user}/{profile}")
    public boolean isFollowing(@PathVariable String user,@PathVariable String profile) throws UnsupportedEncodingException {
        String encodedEmail = URLDecoder.decode(user, StandardCharsets.UTF_8.toString());
        System.out.println(user);
        String encodedProfile = URLDecoder.decode(profile, StandardCharsets.UTF_8.toString());
        return profileService.isFollowing(encodedEmail,encodedProfile);
    }
}
