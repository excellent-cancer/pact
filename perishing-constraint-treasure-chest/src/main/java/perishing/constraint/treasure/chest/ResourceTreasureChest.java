package perishing.constraint.treasure.chest;

import perishing.constraint.note.PatternRemark;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 提供对于集合的常用功能
 */
@PatternRemark.Utilities
public final class ResourceTreasureChest {

    private static final String GIT_URL_PREFIX = "git:";

    /**
     * 返回指定的资源位置是否是一个URL
     *
     * @param resourceLocation 位置字符串
     * @return 位置是否符合URL
     */
    public static boolean isUrl(String resourceLocation) {
        try {
            new URL(resourceLocation);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }


    /**
     * 返回指定的资源位置是否是一个GIT URL
     *
     * @param resourceLocation 位置字符串
     * @return 位置是否符合GIT URL
     */
    public static boolean isGitUrl(String resourceLocation) {
        return isUrl(resourceLocation, GIT_URL_PREFIX);
    }

    private static boolean isUrl(String resourceLocation, String prefix) {
        return resourceLocation != null &&
                resourceLocation.startsWith(prefix) &&
                isUrl(resourceLocation);
    }

}
