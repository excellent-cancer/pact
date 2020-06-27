package perishing.constraint.treasure.chest;

import lombok.NonNull;

/**
 * 提供对于字符串的常用功能
 *
 * @author XyParaCrim
 */
public final class StringTreasureChest {

    /**
     * 判断字符串是否有内容
     *
     * @param str 判断的字符串
     * @return 是否有内容
     */
    public static boolean hasText(String str) {
        if (str != null && !str.isEmpty()) {
            int strLen = str.length();
            for (int i = 0; i < strLen; i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    public static String suffix(@NonNull String str, @NonNull String suffix) {
        return str + "." + suffix;
    }

    public static String normalize(String str) {
        return str == null ? "" : str;
    }

}
