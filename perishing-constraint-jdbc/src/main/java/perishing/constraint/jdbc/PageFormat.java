package perishing.constraint.jdbc;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author XyParaCrim
 */
@Data
@Builder
public class PageFormat<T> {

    private final int total;

    private final int page;

    private final int count;

    private final T[] items;

    public static <T> PageFormat<T> fromPage(T[] items, Page page, int total) {
        return PageFormat.<T>builder().
                items(items).
                page(page.getPage()).
                count(page.getPer()).
                total(total).
                build();
    }
}
