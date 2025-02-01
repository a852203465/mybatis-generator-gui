package cn.darkjrong.mybatis.generator.factory.converts.types;

import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

/**
 * 类型转换
 *
 * @author Rong.Jia
 * @date 2025/01/28
 */
public interface TypeConvert {

    /**
     * 执行类型转换
     *
     * @param fieldType 字段类型
     * @return ignore
     */
    default ColumnType processTypeConvert(String fieldType) {
        return processTypeConvert(DateType.SQL_PACK, fieldType);
    }

    /**
     * 执行类型转换
     *
     * @param dateType date类型
     * @param fieldType    字段类型
     * @return ignore
     */
    ColumnType processTypeConvert(DateType dateType, String fieldType);


}
