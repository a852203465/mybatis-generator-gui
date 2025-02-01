package cn.darkjrong.mybatis.generator.factory.converts.types;

import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import static cn.darkjrong.mybatis.generator.common.enums.ColumnType.*;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.contains;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.containsAny;

/**
 * MYSQL 数据库字段类型转换
 * bit类型数据转换 bit(1) -> Boolean类型  bit(2->64)  -> Byte类型
 * @author hubin, hanchunlin, xiaoliang
 * @since 2017-01-20
 */
public class MySqlTypeConvert implements TypeConvert {
    public static final MySqlTypeConvert INSTANCE = new MySqlTypeConvert();

    @Override
    public ColumnType processTypeConvert(DateType dateType, String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "text", "json", "enum").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(containsAny("tinyint(1)", "bit(1)").then(BOOLEAN))
                .test(contains("bit").then(BYTE))
                .test(contains("int").then(INTEGER))
                .test(contains("decimal").then(BIG_DECIMAL))
                .test(contains("clob").then(CLOB))
                .test(contains("blob").then(BYTE_ARRAY))
                .test(contains("binary").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .test(containsAny("date", "time", "year").then(t -> toDateType(dateType, t)))
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
        String dateTypes = type.replaceAll("\\(\\d+\\)", "");
        switch (dateType) {
            case ONLY_DATE:
                return ColumnType.DATE;
            case SQL_PACK:
                switch (dateTypes) {
                    case "date":
                    case "year":
                        return ColumnType.DATE_SQL;
                    case "time":
                        return ColumnType.TIME;
                    default:
                        return ColumnType.TIMESTAMP;
                }
            case TIME_PACK:
                switch (dateTypes) {
                    case "date":
                        return ColumnType.LOCAL_DATE;
                    case "time":
                        return ColumnType.LOCAL_TIME;
                    case "year":
                        return ColumnType.YEAR;
                    default:
                        return ColumnType.LOCAL_DATE_TIME;
                }
        }
        return STRING;
    }

}
