package br.com.teamdevs.agilekanban.services.validations.membervalidations;

import org.springframework.stereotype.Service;

import br.com.teamdevs.agilekanban.enums.Role;

@Service
public final class MemberValidationPropertiesService {
    private MemberValidationPropertiesService() {
        throw new IllegalStateException("Classe utilitaria.");
    }

    public static void validate(Enum<Role> role) {}
}
