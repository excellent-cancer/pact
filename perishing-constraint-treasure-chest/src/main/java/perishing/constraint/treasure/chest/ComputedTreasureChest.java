package perishing.constraint.treasure.chest;

import perishing.constraint.note.PatternRemark;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 提供对于数学、二进制运行、逻辑运行的常用功能
 *
 * @author XyParaCrim
 */
@PatternRemark.Utilities
public final class ComputedTreasureChest {

    // 解释方法：非常简单的方法，意为方便阅读代码

    /**
     * 判断一个整数是否是一个非负数
     *
     * @param value 判断值
     * @return 一个整数是否是一个非负数
     */
    public static boolean notNegative(int value) {
        return value >= 0;
    }

    /**
     * 判断一个整数是否一个非正数整数
     *
     * @param value 判断值
     * @return 一个整数是否一个非正数整数
     */
    public static boolean notPositive(int value) {
        return value <= 0;
    }

    /**
     * 判断一个整数是否不在指定范围中
     *
     * @param min   下界
     * @param max   上界
     * @param value 判断值
     * @return 一个整数是否不在指定范围中
     */
    public static boolean notBetween(int min, int max, int value) {
        return value < min || value > max;
    }

    // 二进制运算

    /**
     * 将正整数各位补齐1，例如1000 -> 1111，1010 -> 1111
     *
     * @param value 需要补齐的正整数
     * @return 各位补齐1的正整数
     */
    public static int fillOneBit(int value) {
        value |= value >>> 1;
        value |= value >>> 2;
        value |= value >>> 4;
        value |= value >>> 8;
        value |= value >>> 16;

        return value;
    }

    // 随机数

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
