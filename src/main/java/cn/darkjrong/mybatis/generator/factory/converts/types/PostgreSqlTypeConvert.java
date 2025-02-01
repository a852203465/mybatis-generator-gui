package cn.darkjrong.mybatis.generator.factory.converts.types;

import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import static cn.darkjrong.mybatis.generator.common.enums.ColumnType.*;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.contains;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.containsAny;

/**
 * PostgreSQL 字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class PostgreSqlTypeConvert implements TypeConvert {
    public static final PostgreSqlTypeConvert INSTANCE = new PostgreSqlTypeConvert();

    @Override
    public ColumnType processTypeConvert(DateType dateType, String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "text", "json", "enum").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(contains("int").then(INTEGER))
                .test(containsAny("date", "time").then(t -> toDateType(dateType, t)))
                .test(contains("bit").then(BOOLEAN))
                .test(containsAny("decimal", "numeric").then(BIG_DECIMAL))
                .test(contains("bytea").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .test(contains("boolean").then(BOOLEAN))
                .or(STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param dateType 时间类型
     * @param type   类型
     * @return 返回对应的列类型
     */
    public static ColumnType toDateType(DateType dateType, String type) {
        switch (dateType) {
            case SQL_PACK:
                switch (type) {
                    case "date":
                        return ColumnType.DATE_SQL;
                    case "time":
                        return ColumnType.TIME;
                    default:
                        return ColumnType.TIMESTAMP;
                }
            case TIME_PACK:
                switch (type) {
                    case "date":
                        return ColumnType.LOCAL_DATE;
                    case "time":
                        return ColumnType.LOCAL_TIME;
                    default:
                        return ColumnType.LOCAL_DATE_TIME;
                }
            default:
                return ColumnType.DATE;
        }
    }
}

