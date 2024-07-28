package com.workshop.back.Controller;

import com.workshop.back.Service.AuthenticationService;
import com.workshop.back.Service.StorageService;
import com.workshop.back.Service.UserService;
import com.workshop.back.users.User;
import com.workshop.back.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class UserController {
    @Autowired
    private final AuthenticationService authservice;
    private final UserService userService;
    private final StorageService storageService;
    private final UserRepository repo;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authservice.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authservice.login(request));
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody AuthenticationRequest request){
      return authservice.changePassword(request);
    }



    @GetMapping("/profile/{email}")
    public ResponseEntity<?> profile(@PathVariable String email) throws IOException {
        String encodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8.toString());
        Optional<User> Optuser=repo.findUtilisateurByEmail(encodedEmail);
        if(Optuser.isPresent()){
            User user =Optuser.get();
            byte[] image=storageService.downloadImageFromFileSystem(user.getProfileImage());
        return ResponseEntity.ok().contentType(MediaType.valueOf("image/png"))
                    .body(image);
    } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")  ;}



    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("image") MultipartFile file, @RequestParam("email") String email) throws IOException {
        Optional<User> optUser =repo.findUtilisateurByEmail(email);
        if(optUser.isPresent()){
        User user=optUser.get();
        user.setProfileImage(storageService.uploadImageToFileSystem(file));
        repo.save(user);
        return "la photo de profile a ete modifier";}
        else{

        return "";}
    }
    @GetMapping("/info/{email}")
    public  Profile info(@PathVariable String email) throws UnsupportedEncodingException {
        String encodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8.toString());
        Profile profile
                =new Profile();
            Optional<User> optUser =repo.findUtilisateurByEmail(encodedEmail);
            if(optUser.isPresent()){
                User user=optUser.get();

               profile.setEmail(encodedEmail);
               profile.setName(user.getName());
               profile.setNickname(user.getLastName());
                profile.setImg(user.getProfileImage());
            }
            return profile;
    }



    @GetMapping("/searchUsersByEmail")
    public List<User> searchUsersByEmail(@RequestParam String email) {
        List<User> users = userService.getTop5UsersByEmail(email);

        // Set user.Follower to null for each user
        for (User user : users) {
            user.setFollowers(null);
            user.setFollowing(  null);
        }

        return users;
    }


}
