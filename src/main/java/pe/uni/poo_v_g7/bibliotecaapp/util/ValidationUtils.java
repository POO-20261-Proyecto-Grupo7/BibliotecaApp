package pe.uni.poo_v_g7.bibliotecaapp.util;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static <T> T requireNonNull(
            T value,
            String message
    ) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }

    public static String requireNotBlank(
            String value,
            String message
    ) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }

    public static int requireNonNegative(
            int value,
            String message
    ) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }

    public static <N extends Number> N requireNonNegative(
            N value,
            String message
    ) {
        if (value.doubleValue() < 0) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }

    public static void requireTrue(
            boolean condition,
            String message
    ) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireFalse(
            boolean condition,
            String message
    ) {
        if (condition) {
            throw new IllegalArgumentException(message);
        }
    }
}