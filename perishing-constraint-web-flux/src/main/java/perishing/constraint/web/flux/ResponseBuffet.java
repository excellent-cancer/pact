package perishing.constraint.web.flux;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.web.KnownBusinessException;
import perishing.constraint.web.ResponseCode;
import reactor.core.publisher.Mono;

/**
 * 回复自助，提供快速获取已构建的回复
 *
 * @author XyParaCrim
 */
public final class ResponseBuffet {

    /**
     * 转发已有的请求
     *
     * @param format 已有的请求
     * @return 回复方法
     */
    public static Mono<ServerResponse> forward(ResponseFormat<?> format) {
        return assemblyOkResponse(format);
    }

    /**
     * 成功操作回复
     *
     * @return 回复方法
     */
    public static Mono<ServerResponse> allRight() {
        return assemblyOkResponse(ResponseFormat.success());
    }

    /**
     * 成功操作回复
     *
     * @param data 数据
     * @return 回复方法
     */
    public static <T> Mono<ServerResponse> allRight(T data) {
        return assemblyOkResponse(ResponseFormat.success(data));
    }

    /**
     * 内部错误回复
     *
     * @param details 详情
     * @return 回复方法
     */
    public static Mono<ServerResponse> failByInternalError(String details) {
        ResponseFormat<String> format = ResponseFormat.fail(ResponseCode.
                CommonResponseCode.ERROR_INTERNAL, details);

        return assemblyBadResponse(format);
    }

    /**
     * 内部错误回复
     *
     * @return 回复方法
     */
    public static Mono<ServerResponse> failByInternalError() {
        ResponseFormat<String> format = ResponseFormat.fail(ResponseCode.
                CommonResponseCode.ERROR_INTERNAL);

        return assemblyBadResponse(format);
    }

    /**
     * 错误回复格式: 缺失请求参数
     *
     * @param parameters 参数名
     * @return 回复方法
     */
    public static Mono<ServerResponse> failByMissParameters(String... parameters) {
        ResponseFormat<String> format = ResponseFormat.fail(ResponseCode.
                CommonResponseCode.ERROR_MISS_REQUEST_PARAMETER, String.join(",", parameters));

        return assemblyBadResponse(format);
    }

    /**
     * 发生错误: 通过捕捉的异常
     *
     * @param e 业务已知异常
     * @return 回复方法
     */
    public static Mono<ServerResponse> failByKnownException(KnownBusinessException e) {
        ResponseFormat<String> format = ResponseFormat.fail(ResponseCode.CommonResponseCode.ERROR_INTERNAL, e.getMessage());

        return assemblyBadResponse(format);
    }


    /**
     * 装配一个成功回复
     *
     * @param response 回复载体
     * @param <T> 载体数据类型
     * @return 回复方法
     */
    private static <T> Mono<ServerResponse> assemblyOkResponse(ResponseFormat<T> response) {
        return ServerResponse.ok().
                contentType(MediaType.APPLICATION_JSON).
                body(BodyInserters.fromValue(response));
    }

    /**
     * 装配一个错误回复
     *
     * @param response 回复载体
     * @param <T> 载体数据类型
     * @return 回复方法
     */
    private static <T> Mono<ServerResponse> assemblyBadResponse(ResponseFormat<T> response) {
        return ServerResponse.badRequest().
                contentType(MediaType.APPLICATION_JSON).
                body(BodyInserters.fromValue(response));
    }

}
