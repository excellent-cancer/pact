package pact.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomSupport {

    public static final MethodHandle GET_PROBE;

    static {
        try {
            GET_PROBE = MethodHandles.lookup().findStatic(ThreadLocalRandom.class, "getProbe", MethodType.methodType(int.class));
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError();
        }
    }

    public static int probe() {
        try {
            int probe = (int) GET_PROBE.invoke();
            if (probe == 0) {
                ThreadLocalRandom.current();
                probe = (int) GET_PROBE.invoke();
            }
            return probe;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }


}
