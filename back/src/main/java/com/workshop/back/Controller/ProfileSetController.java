package com.workshop.back.Controller;

import com.workshop.back.Entity.ProfileSett;
import com.workshop.back.Service.StorageService;
import com.workshop.back.users.User;
import com.workshop.back.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting")

public class ProfileSetController {
    private final UserRepository userRepository;
    private final StorageService storageService;

    @GetMapping("/{email}")
    public ProfileSett profile(@PathVariable String email){
        ProfileSett profileSett=new ProfileSett();
        Optional<User> optUser=userRepository.findUtilisateurByEmail(email);
        if(optUser.isPresent()){
            User user=optUser.get();
            profileSett.setName(user.getName());
            profileSett.setNickName(user.getLastName());
            profileSett.setPricture(user.getProfileImage());
        }
        return profileSett;
    }
    @PostMapping("/{email}")
    public boolean profileEdit(@RequestParam String name, @RequestParam String nickname,
                               @RequestParam MultipartFile file,@PathVariable String email) throws IOException {
        Optional<User> optUser=userRepository.findUtilisateurByEmail(email);
        if(optUser.isPresent()){
            User user=optUser.get();
            user.setName(name);
            user.setLastName(nickname);
            user.setProfileImage(storageService.uploadImageToFileSystem(file));
            userRepository.save(user);
            return true;
        }
      return false;
    }

}
