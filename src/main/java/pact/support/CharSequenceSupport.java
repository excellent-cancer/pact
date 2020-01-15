package pact.support;

import java.util.Objects;

/**
 * @author XyParaCrim
 */
public final class CharSequenceSupport {
    public static void repeatWhiteSpace(StringBuilder builder, int count) {
        while (count-- > 0) {
            builder.append(' ');
        }
    }

    public static String pascal(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        if (str.isBlank()) {
            throw new IllegalArgumentException();
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String requireNonBlank(String string) {
        Objects.requireNonNull(string);
        if (string.isBlank()) {
            throw new IllegalArgumentException();
        }
        return string;
    }
}
