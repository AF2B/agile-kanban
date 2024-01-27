package br.com.teamdevs.agilekanban.services.validations.taskvalidation;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public final class TaskValidationPropertiesService {
    private TaskValidationPropertiesService() {
        throw new IllegalStateException("Utility class");
    }

    private static final Predicate<String> isValidTitlePredicate = 
        title -> title != null && !title.trim().isEmpty() && title.matches("^[A-Za-z][A-Za-z0-9 _*!?.]*$");

    public static void validate(String title) {
        isValidTitlePredicate.test(title);
    }
}
