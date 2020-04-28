package perishing.constraint.concurrent.flow;

final class Constants {

    // 状态值位移运算

    /**
     * 表示低29位用于表示worker数量
     */
    static final int COUNT_BITS = Integer.SIZE - 3;

    /**
     * 低29位为1，高3为0，为了获取低29位的数据
     */
    static final int COUNT_MAST = (1 << COUNT_BITS) - 1;

}
