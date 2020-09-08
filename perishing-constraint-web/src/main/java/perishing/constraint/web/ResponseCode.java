package perishing.constraint.web;

import lombok.RequiredArgsConstructor;

/**
 * 服务器返回客户端的返回码
 *
 * @author XyParaCrim
 */
public interface ResponseCode {

    /**
     * 返回码的值
     *
     * @return 返回码的值
     */
    int value();

    /**
     * 返回码解释
     *
     * @return 返回码解释
     */
    String message();

    /**
     * 定义一些通用的返回码
     */
    @RequiredArgsConstructor
    enum CommonResponseCode implements ResponseCode {

        /**
         * 成功状态
         */
        SUCCESS(0, "Successful operation"),

        /**
         * 错误状态，未知且严重的错误
         */
        ERROR(-1, "System error"),

        /**
         * 已知错误状态，且系统可以继续运行
         */
        ERROR_INTERNAL(-10, "Internal error"),

        /**
         * 错误请求：缺失请求参数
         */
        ERROR_MISS_REQUEST_PARAMETER(-11, "Bad request: missing request parameters");

        private final int value;

        private final String message;

        @Override
        public int value() {
            return value;
        }

        @Override
        public String message() {
            return message;
        }

    }

}
