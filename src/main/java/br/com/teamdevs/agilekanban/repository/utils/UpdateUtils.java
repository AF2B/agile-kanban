package br.com.teamdevs.agilekanban.repository.utils;

import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public final class UpdateUtils {
    private UpdateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void updateIfPresent(@NonNull Update update, @NonNull String key, Object value) {
        Optional.ofNullable(value)
                .ifPresent(v -> update.set(key, v));
    }

    public static void updateEnumIfPresent(@NonNull Update update, @NonNull String key, Enum<?> value) {
        Optional.ofNullable(value)
                .ifPresent(v -> update.set(key, v.name()));
    }

    public static void updateDateIfPresent(@NonNull Update update, @NonNull String key, LocalDate date) {
        Optional.ofNullable(date)
                .ifPresent(d -> update.set(key, d));
    }

    public static void updateListIfPresent(@NonNull Update update, @NonNull String key, List<?> list) {
        Optional.ofNullable(list)
                .filter(l -> !l.isEmpty())
                .ifPresent(l -> update.set(key, l));
    }
}
