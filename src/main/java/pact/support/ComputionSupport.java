package pact.support;

import pact.annotation.WeakConstraint;

public final class ComputionSupport {

    @WeakConstraint
    public static boolean notNegative(int value) {
        return value >= 0;
    }

    @WeakConstraint
    public static boolean notPositive(int value) {
        return value <= 0;
    }

    @WeakConstraint
    public static boolean notInRange(int min, int max, int value) {
        return value < min || value > max;
    }


    /**
     * 如果 value > 1，目的：减一后全位是一，
     * 例如：16（1000） ->  15(0111)
     */
    @WeakConstraint
    public static int expansionUp(int value) {
        value = value > 1 ? value - 1 : value;
        // step 1: 将value的所有位数填写成1,例如:
        // 100 -> 111
        // 1001001 -> 1111111
        value |= value >>> 1;
        value |= value >>> 2;
        value |= value >>> 4;
        value |= value >>> 8;
        value |= value >>> 16;

        // step 2: 翻倍致 value * 2 < result
        return (value + 1) << 1;
    }
}
