package com.example.pidev.Controller;

import com.example.pidev.Service.JwtService;
import com.example.pidev.Service.UserServiceImpl;
import com.example.pidev.entity.JwtRequest;
import com.example.pidev.entity.JwtResponse;
import com.example.pidev.entity.User;
import com.github.sarxos.webcam.Webcam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    JwtService jwtService;
    private Map<String, Integer> loginAttempts = new HashMap<>();
    private final int MAX_ATTEMPTS = 3;



    @PostMapping({"/authlogin"})
    public JwtResponse authwithfrauddetection(@RequestBody JwtRequest jwtRequest) throws Exception {

        String username = jwtRequest.getUserName();
        String password = jwtRequest.getPassword();

        User dbUser = userService.GetUserByUsername(username);

        if (dbUser == null) {
            System.out.println("Invalid username or password");
        }

        if (!dbUser.getPassword().equals(password)) {
            int attempts = loginAttempts.getOrDefault(username, 0);
            attempts++;
            loginAttempts.put(username, attempts);
            if (attempts == MAX_ATTEMPTS) {
                try {
                    takePhotoAndSave();
                    attempts = 0;
                    loginAttempts.clear();
                } catch (Exception e) {
                    System.out.println("Error taking photo: " + e.getMessage());
                }
            }
            System.out.println(attempts);
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            System.out.println("Invalid username or password ");


        }
        return jwtService.createJwtToken(jwtRequest);
    }





    private void takePhotoAndSave () {
        // Use webcam-capture library to take a photo
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        BufferedImage image = webcam.getImage();
        webcam.close();

        // Save the photo to disk
        try {
            ImageIO.write(image, "JPG", new File("photo2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



   /* @PostMapping("/testlogin")
    public ResponseEntity<?> login(@RequestBody User user) {

        String username = user.getUserName();
        String password = user.getPassword();

        User dbUser = userService.GetUserByUsername(username);

        if (dbUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        if (!dbUser.getPassword().equals(password)) {
            int attempts = loginAttempts.getOrDefault(username, 0);
            attempts++;
            loginAttempts.put(username, attempts);
            if (attempts == MAX_ATTEMPTS) {
                try {
                    takePhotoAndSave();
                    attempts = 0;
                    loginAttempts.clear();
                } catch (Exception e) {
                    System.out.println("Error taking photo: " + e.getMessage());
                }
            }
            System.out.println(attempts);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Reset login attempts if successful
        loginAttempts.put(username, 0);

        return ResponseEntity.ok("Successfully logged in!");
    }

*/




}
