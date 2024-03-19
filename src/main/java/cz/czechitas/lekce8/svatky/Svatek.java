package cz.czechitas.lekce8.svatky;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 * Informace o svátku (jmeninách).
 */
public record Svatek(MonthDay den, String jmeno, Gender gender) {
    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("d. M.");

    public boolean jeZena () {
        return gender().equals(Gender.ZENA);
    };

    @Override
    public String toString() {
        return String.format("%s %s", den.format(DAY_FORMATTER), jmeno);
    }
}
