package cn.darkjrong.mybatis.generator.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 数据源类型
 *
 * @author Rong.Jia
 * @date 2025/01/22
 */
@Getter
@AllArgsConstructor
public enum DbType {


    NULL("",""),
    mysql("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{ip}:{port}/{database}?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true&failOverReadOnly=false"),
    oracle("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@{ip}:{port}/{database}"),
    sqlserver("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://{ip}:{port};DatabaseName={database}"),
    postgresql("org.postgresql.Driver", "jdbc:postgresql://{ip}:{port}/{database}?currentSchema={schema}"),
    sqlite("org.sqlite.JDBC", "jdbc:sqlite:{database}");






    ;

    private final String driver;
    private final String jdbcUrl;


    public static DbType of(String name) {
        return Arrays.stream(DbType.values())
                .filter(a -> StrUtil.equalsIgnoreCase(a.name(), name))
                .findAny().orElse(NULL);
    }










}
