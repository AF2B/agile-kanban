package br.com.teamdevs.agilekanban.services.ValidationsController.ProjectValidations;

import java.util.function.Predicate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.teamdevs.agilekanban.exception.CustomException;

@Service
public class ProjectValidationPropertiesService {
    protected static void validateName(String name) {
        Predicate<String> startsWithLetter = s -> Character.isLetter(s.charAt(0));
        Predicate<String> containsOnlyValidCharacters = s -> s.matches("[\\p{L} ._]+");
        Predicate<String> doesNotEndWithSpace = s -> !s.endsWith(" ");

        boolean isValid = startsWithLetter.and(containsOnlyValidCharacters).and(doesNotEndWithSpace).test(name);

        if (!isValid) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "O nome do projeto é inválido.");
        }
    }

    public static void callValidateName(String name) {
        validateName(name);
    }
}
