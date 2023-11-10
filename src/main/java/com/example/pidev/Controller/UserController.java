package com.example.pidev.Controller;


import com.example.pidev.Repository.OtpRepository;
import com.example.pidev.Repository.RoleRepository;
import com.example.pidev.Repository.UserRepository;
import com.example.pidev.Service.UserServiceImpl;
import com.example.pidev.entity.Role;
import com.example.pidev.entity.Role1;
import com.example.pidev.entity.User;
import com.example.pidev.entity.otp;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@RestController
public class UserController implements UserDetails {

    @Autowired
    UserServiceImpl userService;



    @Autowired
    UserRepository userRepository;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    RoleRepository roleRepository;




    @PostConstruct //lors de l'execution
    public void initRoleAndUser() {
        userService.initRolesAndUser();
    }


    @GetMapping(path = "/ImgUsers/{userName}")
    public byte[] getPhoto(@PathVariable("userName") String userName) throws Exception {
        return userService.getPhoto(userName);
    }


    @GetMapping("/users") // afficher tout les utilisateurs
    public List<User> retrieveAllUsers() {
        return userService.retrieveAllUsers();
    }

    @DeleteMapping("/delete/{userName}")
    public String deleteUser(@PathVariable String userName) {
        return userService.deleteUser(userName);
    }




    @GetMapping("/list")//affichage+pagination
    public Page<User> showPage(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String role) {
        PageRequest pageRequest = PageRequest.of(page, 2);
        if (role != null && !role.isEmpty()) {
            Role role1 = roleRepository.findRoleByRoleName(role);
            return userRepository.findByRole(role1, pageRequest);
        }
        return userRepository.findAll(pageRequest);

    }




    @PostMapping({"/registerNewUser"})
    public ResponseEntity<Map<String, String>> registerNewUser(@RequestBody User user) throws JsonProcessingException {
        return userService.registerNewUser(user);
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<String> activateAccount(@PathVariable String token, HttpServletResponse response) {

        try {
            String oldToken = userService.updateToken(token);
            if (oldToken == null) {
                response.sendRedirect("http://localhost:4200/auth/login");
                return null; // return null to prevent ResponseEntity from being returned
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while activating account");
        }
    }




    @PutMapping("/updateUser/{userName}")
    public User updateUser(@RequestBody User user, @PathVariable("userName") String userName) {
        return userService.updateUser(user, userName);
    }

    @GetMapping(path = "/getUser/{userName}")
    public User getUserByUsername(@PathVariable("userName") String userName) throws Exception {
        return userService.GetUserByUsername(userName);
    }


    @GetMapping("/usernames")
    public List<String> getAllUsernames() {
        List<User> users = userRepository.findAll();
        List<String> usernames = new ArrayList<>();
        for (User user : users) {
            usernames.add(user.getUserName());
        }
        return usernames;
    }




    @GetMapping(path= "/test" )
    public Map<String,Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken)
    {
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }

    // forget and reset password

    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        //   Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        Optional<User> optionalUser = userRepository.findByEmail(email);
        System.out.println(optionalUser);
        System.out.println("optionalUser");
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist with this email");
        }

        Optional<otp> optionalOtp = otpRepository.findByEmail(email);

        otp otpp;
        if (optionalOtp.isPresent()) {
            otpp = optionalOtp.get();
        } else {
            int generatedOtp = new Random().nextInt(9000) + 1000;
            otpp = new otp(email, generatedOtp);

            try {
                sendMail(email, String.valueOf(generatedOtp));
                otpRepository.save(otpp);
            } catch (MessagingException | UnsupportedEncodingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP email");
            }
        }

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            Optional<otp> expiredOtp = otpRepository.findByEmail(email);
            if (expiredOtp.isPresent() && expiredOtp.get().equals(otpp)) {
                otpRepository.delete(otpp);
            }
        }, 2, TimeUnit.MINUTES);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ResponseEntity.ok("OTP sent. Please check your email to reset your password.");

    }

    @Scheduled(fixedDelay = 120000) // run every 2 minutes
    public void deleteExpiredOtps() {
        List<otp> expiredOtps = otpRepository.findByCreatedAtBefore(LocalDateTime.now().minusMinutes(2));
        otpRepository.deleteAll(expiredOtps);
    }

    public void sendMail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("mohamedyahya.jday@esprit.tn", "Winder Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>" + "<p>put this otp in the website to change your password:</p>" + "<p>\"" + link + "\"</p>" + "<br>" + "<p>Ignore this email if you do remember your password, " + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @PostMapping("/otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        int otp = Integer.parseInt(request.get("otp"));

        try {
            otp otpObject = otpRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
            if (otpObject.getOtp() == otp) {
                otpRepository.delete(otpObject);
                Map<String, Object> response = new HashMap<>();
                response.put("message", "OTP verified successfully");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Invalid OTP");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "No OTP found for this email");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/reset_password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, Object> payload) {
        //  Optional<User> user = Optional.ofNullable(userRepository.findByEmail(payload.get("email").toString()));
        Optional<User> user = userRepository.findByEmail(payload.get("email").toString());
        System.out.println(user);
        System.out.println(payload.get("password"));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            userService.updatePassword(user.get(), payload.get("password").toString());

            Map<String, String> response = new HashMap<>();
            response.put("message", "ok");
            return ResponseEntity.ok().body(response);
        }
    }

    // end of forget and reset password


    // pie chart count users by role
    @GetMapping("/count-by-role")
    public Map<Role1, Number> countUsersByRole() {
        List<Object[]> results = userRepository.countUsersByRole();
        Map<Role1, Number> countByRole = new HashMap<>();
        for (Object[] result : results) {
            String roleStr = (String) result[0];
            Role1 role = Role1.valueOf(roleStr);
            Number count = (Number) result[1];
            countByRole.put(role, count);
        }
        return countByRole;
    }
































    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
