package cn.darkjrong.mybatis.generator.factory.meta;

import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.common.domain.TableMeta;
import cn.darkjrong.mybatis.generator.common.enums.DbType;

import java.util.List;

/**
 * 数据库元数据处理器
 *
 * @author Rong.Jia
 * @date 2023/05/10
 */
public interface DataBaseMetaProcessor {

    /**
     * 支持
     *
     * @param dbType db型
     * @return {@link Boolean}
     */
    Boolean supports(DbType dbType);

    /**
     * 查询表
     *
     * @return {@link List}<{@link String}>
     */
    List<String> getTables(DataSource dataSource);

    /**
     * 获取表元信息
     *
     * @param tableName 表名
     * @return {@link TableMeta}
     */
    TableMeta getTableMeta(DataSource dataSource, String tableName);

    /**
     * 存在表
     *
     * @param tableName 表名
     * @return {@link Boolean}
     */
    Boolean existTable(DataSource dataSource, String tableName);

    /**
     * 测试连接
     *
     * @param datasource 数据源信息
     * @return {@link Boolean}
     */
    Boolean testConnection(DataSource datasource);





}
