package perishing.constraint.jdbc;

/**
 * Sql语句的帮助方法
 *
 * @author XyParaCrim
 */
public final class SqlSupport {

    /**
     * 打印的参数字符串的最大长度
     */
    private final static int MAX_PARAM_LENGTH = 50;

    /**
     * 记录的最大SQL长度
     */
    private final static int MAX_SQL_LENGTH = 200;


    /**
     * 返回限制长度之后的SQL语句
     *
     *
     * @param sql 原始SQL语句
     */
    public static String limitSQLLength(String sql) {
        if (sql == null || sql.length() == 0) {
            return "";
        }
        if (sql.length() > MAX_SQL_LENGTH) {
            return sql.substring(0, MAX_SQL_LENGTH);
        } else {
            return sql;
        }
    }


    /**
     * 替换SQL 中? 所对应的值, 只保留前50个字符
     *
     * @param sql     sql语句
     * @param valueOf ?对应的值
     */
    private String replaceValue(String sql, String valueOf) {
        //超过50个字符只取前50个
        if (valueOf != null && valueOf.length() > MAX_PARAM_LENGTH) {
            valueOf = valueOf.substring(0, MAX_PARAM_LENGTH);
        }
        sql = sql.replaceFirst("\\?", valueOf);
        return sql;
    }

    /**
     * 美化sql
     *
     * @param sql sql语句
     */
    public static String beautifySql(String sql) {
        // 输入sql字符串空判断
        if (sql == null || sql.length() == 0) {
            return "";
        }


        return sql.replaceAll("[\\s\n ]+", "  ");
    }


}
