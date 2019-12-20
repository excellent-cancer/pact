package pact.etc;


/**
 * Designed for the detection configuration scenario(针对检测
 * 配置这个场景设计的).
 *
 * @author XyParaCrim
 */
public interface ConfigureCheckProcessing extends CheckProcessing {

    /**
     * Log successful detection configuration information in
     * a fixed format.
     *
     * @param description this's configuration info
     */
    default void reportConfigureFound(String description) {
        logger().info("check for ... {}", description);
        logger().info("found");
    }

    /**
     * Log successful detection configuration information in
     * a fixed format.
     *
     * @param description  this's configuration info
     * @param checkedValue checked value
     */
    default void reportConfigureFound(String description, Object checkedValue) {
        logger().info("check for ... {}", description);
        logger().info("found: {}", checkedValue);
    }

    /**
     * Log failed detection configuration information in
     * a fixed format.
     *
     * @param description this's configuration info
     */
    default void reportConfigureNotFound(String description) {
        logger().error("check for ... {}", description);
        logger().error("not found");
    }

    /**
     * Log failed detection configuration information in
     * a fixed format.
     *
     * @param description  this's configuration info
     * @param checkedValue checked value
     */
    default void reportConfigureNotFound(String description, Object checkedValue) {
        logger().error("check for ... {}", description);
        logger().error("not found: {}", checkedValue);
    }

    /**
     * Log error detection configuration information in
     * a fixed format.
     *
     * @param description this's configuration info
     */
    default void reportConfigureError(String description) {
        logger().error("check for ... {}", description);
        logger().error("error");
    }

    /**
     * Log error detection configuration information in
     * a fixed format.
     *
     * @param description  this's configuration info
     * @param checkedValue checked value
     */
    default void reportConfigureError(String description, Object checkedValue) {
        logger().error("check for ... {}", description);
        logger().error("error: {}", checkedValue);
    }
}
