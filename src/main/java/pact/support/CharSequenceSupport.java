package pact.support;

/**
 * @author XyParaCrim
 */
public final class CharSequenceSupport {
    public static void repeatWhiteSpace(StringBuilder builder, int count) {
        while (count-- > 0) {
            builder.append(' ');
        }
    }
}
