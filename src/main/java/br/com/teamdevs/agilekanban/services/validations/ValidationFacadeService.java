package br.com.teamdevs.agilekanban.services.validations;

import org.springframework.stereotype.Service;

import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.services.validations.uservalidations.UserValidationPropertiesService;

@Service
public class ValidationFacadeService {
    public ValidationFacadeService() {}

    public void validateUser(User user) {
        UserValidationPropertiesService.validate(user);
    }
}
