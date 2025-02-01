package cn.darkjrong.mybatis.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 表字段类型
 *
 * @author Rong.Jia
 * @date 2025/01/28
 */
@Getter
@AllArgsConstructor
public enum ColumnType {
    // 基本类型
    BASE_BYTE("byte", byte.class.getName()),
    BASE_SHORT("short", short.class.getName()),
    BASE_CHAR("char", char.class.getName()),
    BASE_INT("int", int.class.getName()),
    BASE_LONG("long", long.class.getName()),
    BASE_FLOAT("float", float.class.getName()),
    BASE_DOUBLE("double", double.class.getName()),
    BASE_BOOLEAN("boolean", boolean.class.getName()),

    // 包装类型
    BYTE("Byte", Byte.class.getName()),
    SHORT("Short", Short.class.getName()),
    CHARACTER("Character", Character.class.getName()),
    INTEGER("Integer", Integer.class.getName()),
    LONG("Long", Long.class.getName()),
    FLOAT("Float", Float.class.getName()),
    DOUBLE("Double", Double.class.getName()),
    BOOLEAN("Boolean", Boolean.class.getName()),
    STRING("String", String.class.getName()),

    // sql 包下数据类型
    DATE_SQL("Date", "java.sql.Date"),
    TIME("Time", "java.sql.Time"),
    TIMESTAMP("Timestamp", "java.sql.Timestamp"),
    BLOB("Blob", "java.sql.Blob"),
    CLOB("Clob", "java.sql.Clob"),

    // java8 新时间类型
    LOCAL_DATE("LocalDate", "java.time.LocalDate"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime"),
    YEAR("Year", "java.time.Year"),
    YEAR_MONTH("YearMonth", "java.time.YearMonth"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime"),
    INSTANT("Instant", "java.time.Instant"),

    // 其他杂类
    MAP("Map", "java.util.Map"),
    BYTE_ARRAY("byte[]", null),
    OBJECT("Object", null),
    DATE("Date", "java.util.Date"),
    BIG_INTEGER("BigInteger", "java.math.BigInteger"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal");

    /**
     * 类型
     */
    private final String type;

    /**
     * 包路径
     */
    private final String pkg;

}

