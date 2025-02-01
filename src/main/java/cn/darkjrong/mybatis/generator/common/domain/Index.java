package cn.darkjrong.mybatis.generator.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 索引信息
 *
 * @author Rong.Jia
 * @date 2024/06/24
 */
@Data
public class Index implements Serializable {

    private static final long serialVersionUID = 2637369922140282859L;

    /**
     * 索引名
     */
    private String indexName;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 索引类型
     */
    private String indexType;

}
