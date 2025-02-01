package cn.darkjrong.mybatis.generator.factory.converts.types;

import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import static cn.darkjrong.mybatis.generator.common.enums.ColumnType.*;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.contains;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.containsAny;

/**
 * KingbaseES 字段类型转换
 *
 * @author kingbase, hanchunlin
 * @since 2019-10-12
 */
public class OscarTypeConvert implements TypeConvert {
    public static final OscarTypeConvert INSTANCE = new OscarTypeConvert();

    /**
     * @param dateType 时间类型
     * @param fieldType    字段类型
     * @return 返回对应的字段类型
     */
    @Override
    public ColumnType processTypeConvert(DateType dateType, String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("CHARACTER", "char", "varchar", "text", "character varying").then(STRING))
                .test(containsAny("bigint", "int8").then(LONG))
                .test(containsAny("int", "int1", "int2", "int3", "int4", "tinyint", "integer").then(INTEGER))
                .test(containsAny("date", "time", "timestamp").then(p -> toDateType(dateType, p)))
                .test(containsAny("bit", "boolean").then(BOOLEAN))
                .test(containsAny("decimal", "numeric", "number").then(BIG_DECIMAL))
                .test(contains("clob").then(CLOB))
                .test(contains("blob").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(containsAny("double", "real", "float4", "float8").then(DOUBLE))
                .or(STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param  dateType 时间类型
     * @param type   类型
     * @return 返回对应的列类型
     */
    private ColumnType toDateType(DateType dateType, String type) {
        if (dateType == DateType.SQL_PACK) {
            switch (type) {
                case "date":
                    return DATE_SQL;
                case "time":
                    return TIME;
                default:
                    return TIMESTAMP;
            }
        } else if (dateType == DateType.TIME_PACK) {
            switch (type) {
                case "date":
                    return LOCAL_DATE;
                case "time":
                    return LOCAL_TIME;
                default:
                    return LOCAL_DATE_TIME;
            }
        }
        return ColumnType.DATE;
    }

}

