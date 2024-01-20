package br.com.teamdevs.agilekanban.services.ValidationsController.UserValidations;

import org.springframework.stereotype.Service;

@Service
public class UserValidationPropertiesService {
    protected void validateEmail(String email) {}

    protected void validatePassword(String password) {}

    protected void validateUsername(String username) {}

    public void validateUserProperties(String email, String password, String username) {
        validateEmail(email);
        validatePassword(password);
        validateUsername(username);
    }
}
