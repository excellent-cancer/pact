package perishing.constraint.web.flux;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import perishing.constraint.web.ResponseCode;

/**
 * 定义服务器回复格式
 *
 * @author XyParaCrim
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFormat<T> {

    private ResponseFormat(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ResponseFormat(ResponseCode responseCode, T data) {
        this(responseCode.value(), responseCode.message(), data);
    }

    private ResponseFormat(ResponseCode responseCode, T data, String details) {
        this(responseCode.value(), responseCode.message(), data, details);
    }

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回码解释
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 额外信息
     */
    private String details = "";

    // 快捷方式获取回复对象

    /**
     * 通用成功回复格式
     *
     * @return 通用成功回复格式
     */
    public static ResponseFormat<String> success() {
        return success("");
    }

    /**
     * 通用成功回复格式，附带数据
     *
     * @return 通用成功回复格式
     */
    public static <T> ResponseFormat<T> success(T data) {
        return new ResponseFormat<>(ResponseCode.CommonResponseCode.SUCCESS, data);
    }

    /**
     * 系统错误回复格式
     *
     * @return 系统错误回复格式
     */
    public static ResponseFormat<String> error() {
        return new ResponseFormat<>(ResponseCode.CommonResponseCode.ERROR, "");
    }

    /**
     * 错误回复格式
     *
     * @param responseCode 返回码
     * @param details 细节
     * @return 错误回复格式
     */
    public static ResponseFormat<String> fail(ResponseCode responseCode, String details) {
        return new ResponseFormat<>(responseCode, "", details);
    }

    /**
     * 错误回复格式
     *
     * @param responseCode 返回码
     * @return 错误回复格式
     */
    public static ResponseFormat<String> fail(ResponseCode responseCode) {
        return new ResponseFormat<>(responseCode, "");
    }

}
