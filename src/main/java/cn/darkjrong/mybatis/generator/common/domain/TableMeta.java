package cn.darkjrong.mybatis.generator.common.domain;

import cn.hutool.db.meta.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 数据库表信息
 *
 * @author Rong.Jia
 * @date 2024/06/24
 */
@Data
public class TableMeta implements Serializable {

    private static final long serialVersionUID = 599574589182690459L;

    /**
     * table所在的schema
     */
    private String schema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 注释
     */
    private String comment;

    /**
     * 主键字段列表
     */
    private Set<String> pkNames = new LinkedHashSet<>();

    /**
     * 列信息
     */
    private Map<String, Column> columns = new LinkedHashMap<>();

    /**
     * 索引信息
     */
    private Map<String, Index> indexs = new LinkedHashMap<>();



}
