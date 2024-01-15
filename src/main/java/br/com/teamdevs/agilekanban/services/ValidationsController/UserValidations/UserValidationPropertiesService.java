package br.com.teamdevs.agilekanban.services.ValidationsController.UserValidations;

import org.springframework.stereotype.Service;

@Service
public class UserValidationPropertiesService {
    private void validateEmail(String email) {}

    private void validatePassword(String password) {}

    private void validateUsername(String username) {}

    public void validateUserProperties(String email, String password, String username) {
        validateEmail(email);
        validatePassword(password);
        validateUsername(username);
    }
}
