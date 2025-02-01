package cn.darkjrong.mybatis.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * sql
 *
 * @author Rong.Jia
 * @date 2023/02/15
 */
@Getter
@AllArgsConstructor
public enum SqlEnum {

    // mysql
    MYSQL_SHOW_TABLES("SELECT table_name FROM information_schema.TABLES WHERE table_schema = '%s'"),
    MYSQL_SHOW_COL("SELECT\n" +
            "\tCOL.TABLE_NAME AS TABLE_NAME,\n" +
            "\tCOL.COLUMN_NAME AS COLUMN_NAME,\n" +
            "\tCOL.COLUMN_KEY AS COLUMN_KEY,\n" +
            "\tCOL.COLUMN_TYPE AS COLUMN_TYPE,\n" +
            "\tCOL.DATA_TYPE AS DATA_TYPE,\n" +
            "\tCOL.COLUMN_COMMENT AS COLUMN_COMMENT,\n" +
            "\tCOL.ORDINAL_POSITION AS COLUMN_ORDER,\n" +
            "\tCOL.IS_NULLABLE AS IS_NULLABLE,\n" +
            "\tCOL.character_maximum_length AS SIZE\n" +
            "FROM\n" +
            "\tINFORMATION_SCHEMA.COLUMNS COL \n" +
            "WHERE\n" +
            "\tCOL.table_schema = '%s' \n" +
            "\tAND COL.TABLE_NAME = '%s' \n" +
            "ORDER BY\n" +
            "\tordinal_position"),
    MYSQL_EXIST_TABLE("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'"),
    MYSQL_INDEX("SELECT\n" +
            "\tindex_name,\n" +
            "\tcolumn_name,\n" +
            "\tindex_type \n" +
            "FROM\n" +
            "\tINFORMATION_SCHEMA.STATISTICS \n" +
            "WHERE\n" +
            "\tTABLE_SCHEMA = '%s' \n" +
            "\tAND TABLE_NAME = '%s'"),
    MYSQL_PAGE("select * from (%s) a%s LIMIT 0, 10"),
    MYSQL_TABLE_COMMENTS("SELECT\n" +
            "\tTABLE_NAME,\n" +
            "\tTABLE_COMMENT \n" +
            "FROM\n" +
            "\tinformation_schema.TABLES \n" +
            "WHERE\n" +
            "\tTABLE_SCHEMA = '%s' \n" +
            "\tAND table_name = '%s'"),
    MYSQL_TABLE_PK_NAME("SELECT\n" +
            "\tCOLUMN_NAME \n" +
            "FROM\n" +
            "\tinformation_schema.COLUMNS \n" +
            "WHERE\n" +
            "\tTABLE_SCHEMA = '%s' \n" +
            "\tAND TABLE_NAME = '%s' \n" +
            "\tAND COLUMN_KEY = 'PRI'"),

    //oracle
    ORACLE_SHOW_TABLES("SELECT TABLE_NAME FROM all_tables WHERE OWNER = '%s'"),
    ORACLE_SHOW_COL("SELECT\n" +
            "\tatC.TABLE_NAME,\n" +
            "\tATC.COLUMN_NAME,\n" +
            "\tucc.comments,\n" +
            "\tATC.DATA_TYPE,\n" +
            "\tATC.DATA_LENGTH,\n" +
            "\tATC.NULLABLE,\n" +
            "\tATC.COLUMN_ID \n" +
            "FROM\n" +
            "\tALL_TAB_COLUMNS atc\n" +
            "\tLEFT JOIN all_col_comments ucc ON atc.table_name = ucc.table_name \n" +
            "WHERE\n" +
            "\tatc.column_name = ucc.column_name \n" +
            "\tAND atc.OWNER = ucc.OWNER \n" +
            "\tAND atc.OWNER = '%s' \n" +
            "\tAND atc.TABLE_NAME = '%s'"),
    ORACLE_EXIST_TABLE("SELECT\n" +
            "\tTABLE_NAME\n" +
            "FROM\n" +
            "\tall_tables \n" +
            "WHERE\n" +
            "\tOWNER = '%s' \n" +
            "\tAND table_name = '%s'"),
    ORACLE_TABLE_COMMENTS("SELECT\n" +
            "\tutc.table_name as TABLE_NAME,\n" +
            "\tutc.comments as TABLE_COMMENT\n" +
            "FROM\n" +
            "\tall_tables ut,\n" +
            "\tall_tab_comments utc \n" +
            "WHERE\n" +
            "\tut.table_name = utc.table_name \n" +
            "\tAND ut.OWNER = utc.OWNER \t" +
            "\t AND ut.OWNER = '%s'" +
            "\tAND utc.table_name = '%s'"),
    ORACLE_TABLE_PK_NAME("SELECT DISTINCT\n" +
            "\tcu.COLUMN_NAME,\n" +
            "\tau.CONSTRAINT_TYPE \n" +
            "FROM\n" +
            "\tall_cons_columns cu,\n" +
            "\tall_constraints au \n" +
            "WHERE\n" +
            "\tcu.CONSTRAINT_NAME = au.CONSTRAINT_NAME \n" +
            "\tAND cu.OWNER = au.OWNER \n" +
            "\tAND au.OWNER = '%s' \n" +
            "\tAND au.TABLE_NAME = '%s' \n" +
            "\tAND CONSTRAINT_TYPE = 'P'"),
    ORACLE_INDEX("SELECT DISTINCT\n" +
            "\tindex_name,\n" +
            "\tcolumn_name,\n" +
            "\tindex_type \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\tic.column_name,\n" +
            "\t\ti.index_name,\n" +
            "\t\ti.uniqueness AS index_type \n" +
            "\tFROM\n" +
            "\t\tall_indexes i\n" +
            "\t\tJOIN all_ind_columns ic ON i.index_name = ic.index_name \n" +
            "\tWHERE\n" +
            "\t\ti.OWNER = '%s' \n" +
            "\t\ti.table_name = '%s' \n" +
            "\tORDER BY\n" +
            "\t\ti.index_name,\n" +
            "\tic.column_position \n" +
            "\t)"),
    ORACLE_PAGE("select * from (%s) a%s where ROWNUM <= 10"),


    // pgsql
    PGSQL_SHOW_TABLES("SELECT\n" +
            "\ttablename AS TABLE_NAME \n" +
            "FROM\n" +
            "\tpg_tables \n" +
            "WHERE\n" +
            "\tschemaname = '%s' \n" +
            "ORDER BY\n" +
            "\ttablename"),
    PGSQL_SHOW_COL("SELECT\n" +
            "\tattr.attname AS COLUMN_NAME,\n" +
            "\tattr.attnum AS COLUMN_ORDER,\n" +
            "\tty.typname AS COLUMN_TYPE,\n" +
            "\tattr.attnotnull AS IS_NOT_NULLABLE,\n" +
            "\tdn.description AS column_comment \n" +
            "FROM\n" +
            "\tpg_class cl,\n" +
            "\tpg_namespace n,\n" +
            "\tpg_attribute attr,\n" +
            "\tpg_type ty,\n" +
            "\tpg_description dn\n" +
            "WHERE\n" +
            "\tn.nspname = '%s' \n" +
            "\tand cl.relname = '%s'\n" +
            "\tand attr.attnum > 0 \n" +
            "\tAND attr.attrelid = cl.OID \n" +
            "\tAND attr.atttypid = ty.OID \n" +
            "\tAND attr.attnum = dn.objsubid \n" +
            "\tAND dn.objsubid > 0 \n" +
            "\tAND n.OID = cl.relnamespace \n"),
    PGSQL_EXIST_TABLE("select count(*) as num from pg_tables where schemaname = '%s' and tablename = '%s'"),
    PGSQL_INDEX("SELECT\n" +
            "  i.relname AS index_name,\n" +
            "  array_to_string (ARRAY_AGG (A.attname), ', ') AS COLUMN_NAME,\n" +
            "  array_to_string (ARRAY_AGG (b.amname), ', ') AS index_type\n" +
            "FROM\n" +
            "  pg_class T,\n" +
            "  pg_class i,\n" +
            "  pg_index ix,\n" +
            "  pg_attribute A,\n" +
            "  pg_am b,\n" +
            "  pg_namespace n\n" +
            "WHERE\n" +
            "  T.OID = ix.indrelid\n" +
            "  AND i.OID = ix.indexrelid\n" +
            "  AND A.attrelid = T.OID\n" +
            "  AND i.relam = b.OID\n" +
            "  AND A.attnum = ANY (ix.indkey)\n" +
            "  AND T.relkind = 'r'\n" +
            "  AND n.oid = t.relnamespace\n" +
            "  AND n.nspname = '%s'\n" +
            "  AND T.relname = '%s'\n" +
            "GROUP BY\n" +
            "  T.relname,\n" +
            "  i.relname\n" +
            "ORDER BY\n" +
            "  T.relname,\n" +
            "  i.relname;"),
    PGSQL_PAGE("select * FROM (%s) a%s LIMIT 10 OFFSET 0"),
    PGSQL_TABLE_COMMENTS("SELECT \n" +
            "T.relname AS TABLE_NAME,\n" +
            "\td.description AS TABLE_COMMENT \n" +
            "FROM\n" +
            "\tpg_class T \n" +
            "\tJOIN pg_description d ON T.OID = d.objoid \n" +
            "\tJOIN pg_namespace n on n.oid = t.relnamespace\n" +
            "WHERE\n" +
            "\tT.relkind = 'r' \n" +
            "\tAND n.nspname = '%s'\n" +
            "\tAND T.relname = '%s' \n" +
            "\tAND d.objsubid = 0"),
    PGSQL_TABLE_PK_NAME("SELECT\n" +
            "  kcu.column_name AS primary_key_column\n" +
            "FROM\n" +
            "  information_schema.table_constraints tco\n" +
            "  JOIN information_schema.key_column_usage kcu ON kcu.constraint_name = tco.constraint_name\n" +
            "  AND kcu.constraint_schema = tco.constraint_schema\n" +
            "  AND kcu.constraint_name = tco.constraint_name\n" +
            "WHERE\n" +
            "  tco.constraint_type = 'PRIMARY KEY'\n" +
            "  AND tco.table_schema = '%s'\n" +
            "  AND tco.table_name = '%s'"),

    // sqlserver
    SQL_SERVER_SHOW_TABLES("SELECT\n" +
            "\ttable_name \n" +
            "FROM\n" +
            "\t%s.information_schema.tables \n" +
            "WHERE\n" +
            "\ttable_schema = '%s'"),
    SQL_SERVER_SHOW_COL("SELECT\n" +
            "\td.name AS table_name,\n" +
            "\ta.colorder AS column_order,\n" +
            "\ta.name AS column_name,\n" +
            "\t(CASE WHEN (SELECT COUNT (*) FROM sysobjects WHERE (name IN (SELECT name \t\tFROM sysindexes WHERE(id = a.id) AND (indid IN (SELECT indid FROM \t\t sysindexkeys WHERE (id = a.id) AND (colid IN ( SELECT colid FROM     syscolumns WHERE (id = a.id) AND (name = a.name))))))) AND (xtype     = 'PK')) > 0 THEN 'PRI' ELSE '' END) AS column_key,\n" +
            "\tb.name AS column_type,\n" +
            "\t(CASE WHEN a.isnullable= 1 THEN 'YES' ELSE 'NO' END) AS is_nullable,\n" +
            "\tisnull(g.[value], ' ') AS column_comment,\n" +
            "\ta.length as size\n" +
            "FROM\n" +
            "\tsyscolumns a\n" +
            "\tLEFT JOIN systypes b ON a.xtype= b.xusertype\n" +
            "\tINNER JOIN sysobjects d ON a.id= d.id \n" +
            "\tAND d.xtype= 'U' \n" +
            "\tAND d.name<> 'dtproperties'\n" +
            "\tLEFT JOIN syscomments e ON a.cdefault= e.id\n" +
            "\tLEFT JOIN sys.extended_properties g ON a.id= g.major_id \n" +
            "\tAND a.colid= g.minor_id \n" +
            "\tLEFT JOIN sys.extended_properties f ON d.id= f.class \n" +
            "\tAND f.minor_id= 0 \n" +
            "WHERE\n" +
            "\tb.name IS NOT NULL\n" +
            "\tAND g.class_desc = 'OBJECT_OR_COLUMN'\n" +
            "\tAND d.name= '%s' \n" +
            "ORDER BY\n" +
            "a.id,\n" +
            "a.colorder"),
    SQL_SERVER_EXIST_TABLE("SELECT \n" +
            "COUNT(*) AS num \n" +
            "FROM\n" +
            "\t%s.information_schema.tables \n" +
            "WHERE\n" +
            "\ttable_schema = '%s' \n" +
            "\tAND table_name = '%s'"),
    SQL_SERVER_INDEX("SELECT\n" +
            "\ti.name AS index_name,\n" +
            "\tc.name AS column_name,\n" +
            "\ti.type_desc AS index_type \n" +
            "FROM\n" +
            "\tsys.indexes i\n" +
            "\tJOIN sys.tables t ON i.object_id = t.object_id\n" +
            "\tJOIN sys.schemas s ON t.schema_id = s.schema_id\n" +
            "\tINNER JOIN sys.index_columns ic ON i.object_id = ic.object_id \n" +
            "\tAND i.index_id = ic.index_id\n" +
            "\tINNER JOIN sys.columns c ON ic.object_id = c.object_id \n" +
            "\tAND c.column_id = ic.column_id \n" +
            "WHERE\n" +
            "\ts.name = '%s' \n" +
            "\tAND t.name = '%s'"),
    SQL_SERVER_TABLE_COMMENTS("SELECT \n" +
            "    t.name AS table_name,\n" +
            "    p.value AS comment\n" +
            "FROM \n" +
            "    sys.tables t\n" +
            "INNER JOIN \n" +
            "    sys.extended_properties p ON t.object_id = p.major_id\n" +
            "WHERE \n" +
            "  schema_name(t.schema_id) = '%s' and p.minor_id = 0 AND t.name = '%s';"),
    SQL_SERVER_PAGE("select top 10 * from (%s) a%s"),
    SQL_SERVER_TABLE_PK_NAME("SELECT\n" +
            "\tCOLUMN_NAME \n" +
            "FROM\n" +
            "\tINFORMATION_SCHEMA.KEY_COLUMN_USAGE \n" +
            "WHERE\n" +
            "\tOBJECTPROPERTY( OBJECT_ID( CONSTRAINT_NAME ), 'IsPrimaryKey' ) = 1 \n" +
            "\tAND table_schema = '%s' \n" +
            "\tAND TABLE_NAME = '%s'"),



    ;



    private final String value;

    public static String join(SqlEnum sql, Object... args) {
        return String.format(sql.value, args);
    }



}
