package cn.darkjrong.mybatis.generator.common.domain;

import lombok.Getter;

/**
 * The Class IgnoredColumn.
 *
 * @author Jeff Butler
 */
@Getter
public class IgnoredColumn {

    /**
     * The column name.
     */
    private String columnName;

    /**
     * Instantiates a new ignored column.
     *
     * @param columnName the column name
     */
    public IgnoredColumn(String columnName) {
        this.columnName = columnName;
    }


}
