package cn.darkjrong.mybatis.generator.factory.meta;

import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.common.domain.TableMeta;
import cn.darkjrong.mybatis.generator.common.enums.DbType;
import cn.darkjrong.mybatis.generator.common.enums.SqlEnum;
import cn.darkjrong.mybatis.generator.common.utils.SqlUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SqlServer数据库 meta信息查询
 *
 * @author Rong.Jia
 * @date 2023/05/10
 */
@Slf4j
@Component
public class SqlServerDatabaseMetaProcessor extends BaseDatabaseMetaProcessor {

    private static final String DEFAULT_SCHEMA = "dbo";

    @Override
    public Boolean supports(DbType dbType) {
        return DbType.sqlserver.equals(dbType);
    }

    @Override
    public Boolean existTable(DataSource dataSource, String tableName) {
        String schema = SqlUtils.getSchema(dataSource.getSchemaName(), DEFAULT_SCHEMA);
        return SqlUtils.executeQuery(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.SQL_SERVER_EXIST_TABLE, dataSource.getDatabaseName(), schema, tableName),
                dataSource.getUsername(), dataSource.getPassword(), "num", Long.class) > 0;
    }

    @Override
    public List<String> getTables(DataSource dataSource) {
        String schema = SqlUtils.getSchema(dataSource.getSchemaName(), DEFAULT_SCHEMA);
        return SqlUtils.executeQueries(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.SQL_SERVER_SHOW_TABLES, dataSource.getDatabaseName(), schema),
                dataSource.getUsername(), dataSource.getPassword(), "TABLE_NAME", String.class);
    }

    @Override
    public TableMeta getTableMeta(DataSource dataSource, String tableName) {
        String schema = SqlUtils.getSchema(dataSource.getSchemaName(), DEFAULT_SCHEMA);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setSchema(dataSource.getDatabaseName());
        tableMeta.setTableName(tableName);

        Map<String, Object> tableCommentMap = SqlUtils.executeQuery(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.SQL_SERVER_TABLE_COMMENTS, schema, tableName),
                dataSource.getUsername(), dataSource.getPassword());
        tableMeta.setComment(Convert.toStr(tableCommentMap.get("comment")));

        List<String> pkNames = SqlUtils.executeQueries(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.SQL_SERVER_TABLE_PK_NAME, schema, tableName),
                dataSource.getUsername(), dataSource.getPassword(), "COLUMN_NAME", String.class);
        tableMeta.setPkNames(CollUtil.newHashSet(pkNames));

        tableMeta.setIndexs(getIndexMeta(dataSource, tableName));

        List<Map<String, Object>> colMap = SqlUtils.executeQueries(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.SQL_SERVER_SHOW_COL, tableName),
                dataSource.getUsername(), dataSource.getPassword());

        Map<String, Column> columns = new LinkedHashMap<>();
        for (Map<String, Object> objectMap : colMap) {
            Column column = new Column();
            column.setTableName(Convert.toStr(objectMap.get("table_name")));
            column.setName(Convert.toStr(objectMap.get("column_name")));
            column.setTypeName(Convert.toStr(objectMap.get("column_type")));
            column.setNullable(StrUtil.equals(Convert.toStr(objectMap.get("is_nullable")), "YES"));
            column.setComment(Convert.toStr(objectMap.get("column_comment")));
            column.setSize(Convert.toInt(objectMap.get("size")));
            column.setPk(StrUtil.equals(Convert.toStr(objectMap.get("column_key")), "PRI"));
            columns.put(column.getName(), column);
        }
        tableMeta.setColumns(columns);
        return tableMeta;
    }

    @Override
    protected String indexSql(DataSource dataSource, String tableName) {
        String schema = SqlUtils.getSchema(dataSource.getSchemaName(), DEFAULT_SCHEMA);
        return SqlEnum.join(SqlEnum.SQL_SERVER_INDEX, schema, tableName);
    }


}
