package perishing.constraint.web;

/**
 * 处理业务时发生的异常且包含想要传递出去错误信息的异常
 *
 * @author XyParaCrim
 */
public class KnownBusinessException extends RuntimeException {

    private final ResponseCode responseCode;

    public KnownBusinessException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public KnownBusinessException(ResponseCode responseCode, String message, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
    }

    public KnownBusinessException(ResponseCode responseCode, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
    }

}
