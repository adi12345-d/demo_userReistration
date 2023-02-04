package com.DemoMbi.demo_Mbi.Service;

import com.DemoMbi.demo_Mbi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class UserRegistarionImpl  implements UserRegistarion {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ImageUploadService imageUploadService;

    @Override
    public void processRegistration(RegistrationForm registrationForm) {
        // validate the form data
        validateForm(registrationForm);

        // save the user's information to the database
        User user = saveUserInformation(registrationForm);

        // send a confirmation email to the user
        sendConfirmationEmail(user);

        // upload the image to the server
        uploadImage(registrationForm.getImage(), user.getId());
    }

    private void validateForm(RegistrationForm registrationForm) {
        // check if the passwords match
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        // add other validation checks here
    }

    private User saveUserInformation(RegistrationForm registrationForm) {
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(registrationForm.getPassword());
        user.setPhoneNumber(registrationForm.getPhoneNumber());
        user.setAddress(registrationForm.getAddress());
        return userRepository.save(user);
    }

    private void sendConfirmationEmail(User user) {
        String subject = "Registration Confirmation";
        String message = "Thank you for registering, " + user.getUsername() + "!";
        emailService.sendEmail(user.getEmail(), subject, message);
    }

    private void uploadImage(MultipartFile image, Long userId) {
        imageUploadService.uploadImage(image, userId);
    }
}