package cn.darkjrong.mybatis.generator.factory.converts.types;

import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import static cn.darkjrong.mybatis.generator.common.enums.ColumnType.*;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.contains;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.containsAny;

/**
 * MYSQL 数据库字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class FirebirdTypeConvert implements TypeConvert {
    public static final FirebirdTypeConvert INSTANCE = new FirebirdTypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public ColumnType processTypeConvert(DateType dateType, String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("cstring", "text").then(STRING))
                .test(contains("short").then(SHORT))
                .test(contains("long").then(LONG))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .test(contains("blob").then(BLOB))
                .test(contains("int64").then(LONG))
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
        switch (dateType) {
            case ONLY_DATE:
                return ColumnType.DATE;
            case SQL_PACK:
                switch (type) {
                    case "date":
                    case "year":
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
                    case "year":
                        return ColumnType.YEAR;
                    default:
                        return ColumnType.LOCAL_DATE_TIME;
                }
        }
        return STRING;
    }

}

