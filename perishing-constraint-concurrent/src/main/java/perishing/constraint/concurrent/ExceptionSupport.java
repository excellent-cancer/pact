package perishing.constraint.concurrent;

import perishing.constraint.note.Unqualified;

import java.io.IOException;

/**
 * Exception Utilities.
 *
 * @author XyParaCrim
 */
@Unqualified
public final class ExceptionSupport {

    /**
     * Used to collect ignored exceptions.
     *
     * @param exception Ignored exception
     */

    public static void collectIgnored(Exception exception) {
        // TODO 如何设置忽略异常地处理方式在不同的项目依赖当中
    }

    public static void closeWithExceptionHanding(AutoCloseable autoCloseComponent) {
        try {
            autoCloseComponent.close();
        } catch (final IOException | RuntimeException e) {
            collectIgnored(e);
        } catch (Exception e) {
            // TODO 正确处理
            throw new RuntimeException(e);
        }
    }
}