package cn.darkjrong.mybatis.generator.factory.converts.types;

import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import static cn.darkjrong.mybatis.generator.common.enums.ColumnType.*;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.contains;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.containsAny;

/**
 * SQLServer 字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class SqlServerTypeConvert implements TypeConvert {

    public static final SqlServerTypeConvert INSTANCE = new SqlServerTypeConvert();

    @Override
    public ColumnType processTypeConvert(DateType dateType, String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "xml", "text").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(contains("int").then(INTEGER))
                .test(containsAny("date", "time").then(t -> toDateType(dateType, t)))
                .test(contains("bit").then(BOOLEAN))
                .test(containsAny("decimal", "numeric").then(DOUBLE))
                .test(contains("money").then(BIG_DECIMAL))
                .test(containsAny("binary", "image").then(BYTE_ARRAY))
                .test(containsAny("float", "real").then(FLOAT))
                .or(STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param dateType 时间类型
     * @param fieldType   类型
     * @return 返回对应的列类型
     */
    public static ColumnType toDateType(DateType dateType, String fieldType) {
        switch (dateType) {
            case SQL_PACK:
                switch (fieldType) {
                    case "date":
                        return DATE_SQL;
                    case "time":
                        return TIME;
                    default:
                        return TIMESTAMP;
                }
            case TIME_PACK:
                switch (fieldType) {
                    case "date":
                        return LOCAL_DATE;
                    case "time":
                        return LOCAL_TIME;
                    default:
                        return LOCAL_DATE_TIME;
                }
            default:
                return DATE;
        }
    }
}

