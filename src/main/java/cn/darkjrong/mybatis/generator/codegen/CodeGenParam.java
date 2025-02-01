package cn.darkjrong.mybatis.generator.codegen;

import cn.darkjrong.mybatis.generator.common.domain.ColumnOverride;
import cn.darkjrong.mybatis.generator.common.domain.IgnoredColumn;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;

import java.util.List;

@Data
public class CodeGenParam {

    /**
     * 数据库类型
     */
    private DbType dbType;

    /**
     * 项目目录
     */
    private String projectPath;

    /**
     * JAVA源码目录
     */
    private String javaSourcePath;

    /**
     * 父级目录
     */
    private String parentPath = "";
    private String moduleName = "";

    /**
     * 实体类路径
     */
    private String entityPath;

    /**
     * Mapper路径
     */
    private String mapperPath;

    /**
     * Mapper后缀名
     */
    private String mapperSuffixName = "Mapper";

    /**
     * JAVA资源目录
     */
    private String javaResourcePath;

    /**
     * Mapper.xml路径
     */
    private String mapperXmlPath;


    private String database;
    private String schema;

    private String username;
    private String password;
    private String host;
    private String port;

    private String author;
    private String tablePrefix;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 开启lombok
     */
    private Boolean enableLombok = Boolean.TRUE;

    /**
     * 覆盖原文件
     */
    private Boolean fileOverride = Boolean.TRUE;

    /**
     * 开启Schema
     */
    private Boolean enableSchema = Boolean.FALSE;

    /**
     * 生成BaseService
     */
    private Boolean genBaseService = Boolean.TRUE;

    /**
     * VO路径
     */
    private String voPath;

    /**
     * DTO路径
     */
    private String dtoPath;

    /**
     * Query路径
     */
    private String queryPath;

    /**
     * FilterDTO路径
     */
    private String filterDtoPath;

    /**
     * 覆盖列
     */
    private List<ColumnOverride> columnOverrides;

    /**
     * 跳过列
     */
    private List<IgnoredColumn> ignoredColumns;

    /**
     * 列名策略
     */
    private NamingStrategy columnNameStrategy;


    public String getTablePrefix() {
        return StrUtil.isBlank(tablePrefix) ? "" : tablePrefix;
    }

}
