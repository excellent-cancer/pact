package perishing.constraint.concurrent;

import perishing.constraint.note.Unqualified;
import perishing.constraint.treasure.chest.FunctionsTreasureChest;

/**
 * @author XyParaCrim
 */
@Unqualified
public final class IgnoredSupport {

    public static Runnable collectIgnored(FunctionsTreasureChest.RunnableWithException<InterruptedException> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (InterruptedException ignored) {
            }
        };
    }

}
