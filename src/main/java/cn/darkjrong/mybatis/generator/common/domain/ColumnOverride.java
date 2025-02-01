package cn.darkjrong.mybatis.generator.common.domain;

import lombok.Data;

/**
 * 列覆盖
 *
 * @author Rong.Jia
 * @date 2025/01/28
 */
@Data
public class ColumnOverride {

    /**
     * The column name.
     */
    private String columnName;

    /**
     * The java property.
     */
    private String javaProperty;

    /**
     * The jdbc type.
     */
    private String jdbcType;

    /**
     * The java type.
     */
    private String javaType;

    public ColumnOverride(String columnName) {
        this.columnName = columnName;
    }

}
