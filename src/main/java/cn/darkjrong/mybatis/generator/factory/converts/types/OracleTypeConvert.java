package cn.darkjrong.mybatis.generator.factory.converts.types;

import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import static cn.darkjrong.mybatis.generator.common.enums.ColumnType.*;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.contains;
import static cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts.containsAny;

/**
 * Oracle 数据库生成对应实体类时字段类型转换，跟据 Oracle 中的数据类型，返回对应的 Java 类型
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class OracleTypeConvert implements TypeConvert {
    public static final OracleTypeConvert INSTANCE = new OracleTypeConvert();

    /**
     * 处理类型转换
     *
     * @param dateType    时间类型
     * @param fieldType 字段类型
     * @return 返回的对应的列类型
     */
    @Override
    public ColumnType processTypeConvert(DateType dateType, String fieldType) {
        return TypeConverts.use(fieldType)
                .test(containsAny("char", "clob").then(STRING))
                .test(containsAny("date", "timestamp").then(p -> toDateType(dateType)))
                .test(contains("number").then(OracleTypeConvert::toNumberType))
                .test(contains("float").then(FLOAT))
                .test(contains("blob").then(BYTE_ARRAY))
                .test(containsAny("binary", "raw").then(BYTE_ARRAY))
                .or(STRING);
    }

    /**
     * 将对应的类型名称转换为对应的 java 类类型
     * <p>
     * String.valueOf(Integer.MAX_VALUE).length() == 10
     * Integer 不一定能装下 10 位的数字
     * <p>
     * String.valueOf(Long.MAX_VALUE).length() == 19
     * Long 不一定能装下 19 位的数字
     *
     * @param typeName 类型名称
     * @return 返回列类型
     */
    private static ColumnType toNumberType(String typeName) {
        if (typeName.matches("number\\([0-9]\\)")) {
            return ColumnType.INTEGER;
        } else if (typeName.matches("number\\(1[0-8]\\)")) {
            return ColumnType.LONG;
        }
        return ColumnType.BIG_DECIMAL;
    }

    /**
     * 当前时间为字段类型，根据全局配置返回对应的时间类型
     *
     * @param dateType 时间类型
     * @return 时间类型
     * @see GlobalConfig#getDateType()
     */
    protected static ColumnType toDateType(DateType dateType) {
        switch (dateType) {
            case ONLY_DATE:
                return ColumnType.DATE;
            case SQL_PACK:
                return ColumnType.TIMESTAMP;
            case TIME_PACK:
                return ColumnType.LOCAL_DATE_TIME;
            default:
                return STRING;
        }
    }

}
