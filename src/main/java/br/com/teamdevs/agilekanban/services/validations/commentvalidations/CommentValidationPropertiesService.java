package br.com.teamdevs.agilekanban.services.validations.commentvalidations;

import java.util.Set;
import java.util.function.Predicate;

public final class CommentValidationPropertiesService {
    private CommentValidationPropertiesService() {
        throw new IllegalStateException("This is a utility class and cannot be instantiated");
    }

    private static final String VALID_TEXT_REGEX = "^[\\w\\s.,;:?!'\"-]*$";
    private static final Set<String> OFFENSIVE_WORDS = Set.of("palavra1", "palavra2", "palavra3");
    private static final Predicate<String> isValidTextPredicate = 
        text -> text != null && text.matches(VALID_TEXT_REGEX) && OFFENSIVE_WORDS.stream().noneMatch(text::contains);

    public static void validate(String text) {
        isValidTextPredicate.test(text);
    }
}