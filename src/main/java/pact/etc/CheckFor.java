package pact.etc;

/**
 * Filters perform certain bootstrap prior execution of request by request handler.
 * @author XyParaCrim
 */
@FunctionalInterface
public interface CheckFor<T extends CheckProcessing> {

    /**
     * Evaluates this check-for on the given processing.
     *
     * @param processing A processing.
     * @return if is's passed
     */
    boolean check(T processing);
}
