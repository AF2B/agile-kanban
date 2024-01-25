package br.com.teamdevs.agilekanban.services.ValidationsController.UserValidations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.teamdevs.agilekanban.exception.CustomException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public final class UserValidationPropertiesService {
    private static final List<String> commonPasswords = Arrays.asList(
        "password", "123456", "12345678", "123456789", "qwerty",
        "abc123", "password1", "admin", "12345", "1234", "000000",
        "iloveyou", "123123", "monkey", "1234567", "letmein",
        "111111", "sunshine", "master", "princess"
    );

    /**
     * Valida se o e-mail está em lowercase e segue o formato padrão.
     * O e-mail deve conter um '@' seguido por um domínio.
     * Não deve haver pontos consecutivos.
     *
     * @param email O e-mail a ser validado.
     * @throws CustomException se o e-mail não estiver em lowercase ou não seguir o formato padrão.
     */
    protected static void validateEmail(String email) {
        if (!email.toLowerCase().equals(email)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Email must be in lowercase.");
        }
        if (!Pattern.matches("[^@]+@[^\\.]+(\\.[^\\.]+)+", email)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Invalid email format.");
        }
    }

    /**
     * Valida se a senha atende aos requisitos de complexidade.
     * A senha deve conter pelo menos um número, uma letra minúscula, uma letra maiúscula e um caractere especial.
     * Não deve ser uma senha comumente usada.
     *
     * @param password A senha a ser validada.
     * @throws CustomException se a senha for muito comum ou não atender aos requisitos de complexidade.
     */
    protected static void validatePassword(String password) {
        if (commonPasswords.contains(password)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Password is too common.");
        }
        if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$", password)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Password does not meet complexity requirements.");
        }
    }

    /**
     * Valida se o nome de usuário segue o formato adequado.
     * O nome de usuário deve começar com uma letra e não pode terminar nem conter caracteres especiais,
     * exceto sublinhado ('_'). Além disso, não pode conter mais de um caractere especial consecutivo.
     *
     * @param username O nome de usuário a ser validado.
     * @throws CustomException se o nome de usuário não seguir o formato adequado.
     */
    protected static void validateUsername(String username) {
        if (!Pattern.matches("^[A-Za-z](?!.*?[^\na-zA-Z0-9_]).*[^_]$", username)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Invalid username format.");
        }
    }

    public static void callValidateUserProperties(String email, String password, String username) {
        validateEmail(email);
        validatePassword(password);
        validateUsername(username);
    }

    public static void callValidateUsername(String username) {
        validateUsername(username);
    }

    public static void callValidatePassword(String password) {
        validatePassword(password);
    }

    public static void callValidateEmail(String email) {
        validateEmail(email);
    }
}
