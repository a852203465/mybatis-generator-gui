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
 * Oracle数据库 meta信息查询
 *
 * @author Rong.Jia
 * @date 2023/05/10
 */
@Slf4j
@Component
public class OracleDatabaseMetaProcessor extends BaseDatabaseMetaProcessor {

    @Override
    public Boolean supports(DbType dbType) {
        return DbType.oracle.equals(dbType);
    }

    @Override
    public Boolean existTable(DataSource dataSource, String tableName) {
        String existTable = SqlUtils.executeQuery(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.ORACLE_EXIST_TABLE, dataSource.getUsername(), tableName),
                dataSource.getUsername(), dataSource.getPassword(), "TABLE_NAME", String.class);
        return StrUtil.isNotBlank(existTable);
    }

    @Override
    public List<String> getTables(DataSource dataSource) {
        return SqlUtils.executeQueries(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.ORACLE_SHOW_TABLES, dataSource.getUsername()),
                dataSource.getUsername(), dataSource.getPassword(), "TABLE_NAME", String.class);
    }

    @Override
    public TableMeta getTableMeta(DataSource dataSource, String tableName) {
        TableMeta tableMeta = new TableMeta();
        tableMeta.setSchema(dataSource.getUsername());
        tableMeta.setTableName(tableName);

        Map<String, Object> tableCommentMap = SqlUtils.executeQuery(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.ORACLE_TABLE_COMMENTS, dataSource.getUsername(), tableName),
                dataSource.getUsername(), dataSource.getPassword());
        tableMeta.setComment(Convert.toStr(tableCommentMap.get("TABLE_COMMENT")));

        List<String> pkNames = SqlUtils.executeQueries(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.ORACLE_TABLE_PK_NAME, dataSource.getUsername(), tableName),
                dataSource.getUsername(), dataSource.getPassword(), "COLUMN_NAME", String.class);
        tableMeta.setPkNames(CollUtil.newHashSet(pkNames));

        tableMeta.setIndexs(getIndexMeta(dataSource, tableName));

        List<Map<String, Object>> colMap = SqlUtils.executeQueries(dataSource.getJdbcUrl(),
                SqlEnum.join(SqlEnum.ORACLE_SHOW_COL, dataSource.getUsername(), tableName, dataSource.getUsername()),
                dataSource.getUsername(), dataSource.getPassword());

        Map<String, Column> columns = new LinkedHashMap<>();
        for (Map<String, Object> cols : colMap) {
            Column column = new Column();
            column.setTableName(Convert.toStr(cols.get("TABLE_NAME")));
            column.setName(Convert.toStr(cols.get("COLUMN_NAME")));
            column.setComment(Convert.toStr(cols.get("COMMENTS")));
            column.setTypeName(Convert.toStr(cols.get("DATA_TYPE")));
            column.setSize(Convert.toInt(cols.get("DATA_LENGTH")));
            column.setPk(pkNames.contains(column.getName()));
            column.setNullable("Y".equalsIgnoreCase(Convert.toStr(cols.get("NULLABLE"))));
            columns.put(column.getName(), column);
        }
        tableMeta.setColumns(columns);
        return tableMeta;
    }

    @Override
    protected String indexSql(DataSource dataSource, String tableName) {
        return SqlEnum.join(SqlEnum.ORACLE_INDEX, dataSource.getUsername(), tableName);
    }












    
}
