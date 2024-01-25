package br.com.teamdevs.agilekanban.services.ValidationsController.ProjectValidations;

import org.springframework.stereotype.Service;

@Service
public class ProjectValidationPropertiesService {
    protected static void validateName(String name) {}

    public static void callValidateName(String name) {
        validateName(name);
    }
}
