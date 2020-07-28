package perishing.constraint.jdbc;

import lombok.Data;

/**
 * 表示分页的页码和个数
 *
 * @author XyParaCrim
 */
@Data
public class Page {

    private final int from;

    private final int count;

    private final int per;

    private final int page;
    
    public Page nullable() {
        return this == UNLIMITED ? null : this;
    }

    public boolean isUnlimited() {
        return from < 0 || count < 0;
    }

    public static Page newPage(int pages, int count) {
        return new Page((pages - 1) * count, count, pages, count);
    }

    public static Page newPage(String pages, String count) throws NumberFormatException {
        int parsedPages = Integer.parseInt(pages);
        int parsedCount = Integer.parseInt(count);

        return newPage(parsedPages, parsedCount);
    }

    private static final Page UNLIMITED = new Page(-1, -1, -1, -1);

    public static Page unlimited() {
        return UNLIMITED;
    }

}
