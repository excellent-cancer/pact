package perishing.constraint.web;

/**
 * 服务器返回客户端的返回码
 *
 * @author XyParaCrim
 */
public interface ResponseCode {

    /**
     * 返回码的值
     *
     * @return 返回返回码的值
     */
    int value();

    /**
     * 定义一些通用的返回码
     */
    enum CommonResponseCode implements ResponseCode {

        /**
         * 成功状态
         */
        SUCCESS(0),

        /**
         * 错误状态
         */
        ERROR(-1);

        private final int value;

        CommonResponseCode(int value) {
            this.value = value;
        }

        @Override
        public int value() {
            return value;
        }
    }

}
