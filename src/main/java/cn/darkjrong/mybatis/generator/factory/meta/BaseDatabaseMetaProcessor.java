package cn.darkjrong.mybatis.generator.factory.meta;

import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.common.domain.Index;
import cn.darkjrong.mybatis.generator.common.utils.SqlUtils;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础数据库元数据处理器
 *
 * @author Rong.Jia
 * @date 2023/05/10
 */
@Slf4j
public abstract class BaseDatabaseMetaProcessor implements DataBaseMetaProcessor {

    /**
     * 获取索引元信息
     *
     * @param dataSource 数据源
     * @param tableName  表名
     * @return {@link Map}<{@link String}, {@link Index}> key:索引名,value:索引信息
     */
    protected Map<String, Index> getIndexMeta(DataSource dataSource, String tableName) {
        List<Map<String, Object>> mapList = SqlUtils.executeQueries(dataSource.getJdbcUrl(),
                indexSql(dataSource, tableName), dataSource.getUsername(), dataSource.getPassword());
        Map<String, Index> indexMap = new LinkedHashMap<>();
        for (Map<String, Object> objectMap : mapList) {
            Index index = new Index();
            index.setIndexName(Convert.toStr(objectMap.get("index_name")));
            index.setColumnName(Convert.toStr(objectMap.get("column_name")));
            index.setIndexType(Convert.toStr(objectMap.get("index_type")));
            indexMap.put(index.getIndexName(), index);
        }
        return indexMap;
    }

    /**
     * 索引SQL
     *
     * @param dataSource 数据源
     * @param tableName  表名
     * @return {@link String} SQL
     */
    protected abstract String indexSql(DataSource dataSource, String tableName);

    @Override
    public Boolean testConnection(DataSource datasource) {
        Connection connection = null;
        try {
            Class.forName(datasource.getDriverClassName());
            connection = DriverManager.getConnection(datasource.getJdbcUrl(),
                    datasource.getUsername(), datasource.getPassword());
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error(String.format("数据源【%s】测试连接异常【%s】", JSON.toJSONString(datasource), e.getMessage()), e);
        } finally {
            IoUtil.close(connection);
        }
        return Boolean.FALSE;
    }




}
