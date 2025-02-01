package cn.darkjrong.mybatis.generator.factory.converts.types;

import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import static cn.darkjrong.mybatis.generator.common.enums.ColumnType.*;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.contains;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.containsAny;

/**
 * DB2 字段类型转换
 *
 * @author zhanyao, hanchunlin
 * @since 2018-05-16
 */
public class DB2TypeConvert implements TypeConvert {
    public static final DB2TypeConvert INSTANCE = new DB2TypeConvert();

    @Override
    public ColumnType processTypeConvert(DateType dateType, String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "text", "json", "enum").then(STRING))
                .test(contains("bigint").then(LONG))
                .test(contains("smallint").then(BASE_SHORT))
                .test(contains("int").then(INTEGER))
                .test(containsAny("date", "time", "year").then(DATE))
                .test(contains("bit").then(BOOLEAN))
                .test(contains("decimal").then(BIG_DECIMAL))
                .test(contains("clob").then(CLOB))
                .test(contains("blob").then(BLOB))
                .test(contains("binary").then(BYTE_ARRAY))
                .test(contains("float").then(FLOAT))
                .test(contains("double").then(DOUBLE))
                .or(STRING);
    }
}