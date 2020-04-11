package perishing.constraint.language.support;

import org.jetbrains.annotations.NotNull;
import perishing.constraint.treasure.chest.TreasureChest;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 缓存各个线程的资源包
 */
class BundleCache {

    @SuppressWarnings("AlibabaThreadLocalShouldRemove")
    private static final ThreadLocal<Map<Locale, BundleCache>> LOCAL = ThreadLocal.withInitial(HashMap::new);

    private final Locale locale;

    private final HashMap<Class<?>, TranslationBundleHolder> bundles = new HashMap<>();

    /**
     * 构造当前线程缓存的方法，需要指定加载资源包的{@link Locale}
     *
     * @param locale 用于加载资源包
     */
    BundleCache(Locale locale) {
        this.locale = locale;
    }

    /**
     * 通过指定的{@link AbstractTranslationBundle}类对象获取资源包实例
     *
     * @param type {@link AbstractTranslationBundle}类对象
     * @param <T>  资源包类型
     * @return 资源包实例
     */
    @SuppressWarnings("unchecked")
    <T extends AbstractTranslationBundle> T get(Class<T> type) {
        return (T) bundles.computeIfAbsent(type, k -> GlobalBundleCache.lookupBundle(locale, type)).
                translationBundle();
    }

    /**
     * 获取当前线程的资源缓存，如果不存在，则会创建一个新的缓存
     *
     * @param locale 加载资源包的locale
     * @return 当前线程的资源包缓存
     */
    @NotNull
    static BundleCache currentBundleCache(Locale locale) {
        Map<Locale, BundleCache> cacheMap = LOCAL.get();

        return cacheMap.computeIfAbsent(locale, k -> new BundleCache(locale));
    }

    /**
     * 全局语言包缓存，当{@code LOCAL}没有缓存资源包时，则需要统一通过此缓存获取
     */
    private static class GlobalBundleCache {

        private static final Map<Locale, Map<Class<? extends AbstractTranslationBundle>, TranslationBundleHolder>> CACHED_BUNDLES = new WeakHashMap<>();

        /**
         * 通过指定{@code local}和{@code type}获取缓存的资源包。
         * 当创建{@link AbstractTranslationBundle}实例时，
         * 会调用其默认的构成函数
         *
         * @param locale 加载资源包的locale
         * @param type   资源包类对象
         * @param <T>    资源包类
         * @return 资源包实例
         */
        static synchronized <T extends AbstractTranslationBundle> TranslationBundleHolder lookupBundle(Locale locale, Class<T> type) {
            return CACHED_BUNDLES.
                    computeIfAbsent(locale, TreasureChest::defaultMap).
                    computeIfAbsent(type, k -> {
                        try {
                            // 必须使用默认的构造函数创建实例
                            AbstractTranslationBundle translationBundle = type.getConstructor().newInstance();

                            return new TranslationBundleHolder(locale, translationBundle);
                        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            throw new Error(e);
                        }
                    });
        }


    }

}
