package br.com.teamdevs.agilekanban.services.validations.attachmentvalidations;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public final class AttachmentValidationPropertiesService {
    private AttachmentValidationPropertiesService() {
        throw new IllegalStateException("Utility class");
    }

    private static final String VALID_FILENAME_REGEX = "^[\\w.-]+\\.[\\w]{1,5}$";
    private static final Predicate<String> isValidFilenamePredicate = 
        filename -> filename != null && filename.matches(VALID_FILENAME_REGEX);

    public boolean isValidFilename(String filename) {
        return isValidFilenamePredicate.test(filename);
    }
}
