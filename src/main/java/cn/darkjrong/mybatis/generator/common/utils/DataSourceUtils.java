package cn.darkjrong.mybatis.generator.common.utils;

import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.common.domain.JdbcHolder;
import cn.darkjrong.mybatis.generator.common.enums.DbType;
import cn.darkjrong.mybatis.generator.common.pojo.dto.DataSourceDTO;
import cn.darkjrong.mybatis.generator.common.pojo.vo.DbSourceVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源工具类
 *
 * @author Rong.Jia
 * @date 2025/01/22
 */
public class DataSourceUtils {

    public static DataSource getDataSource(DbSourceVO dbSourceVO) {
        DataSource dataSource = new DataSource();
        BeanUtil.copyProperties(dbSourceVO, dataSource);
        dataSource.setSourceId(dbSourceVO.getId());
        dataSource.setDbType(DbType.of(dbSourceVO.getDbType()));
        return dataSource;
    }

    public static DataSource getDataSource(Long sourceId, TextField nameField,
                                           TextField hostField,
                                           TextField portField,
                                           TextField userNameField,
                                           TextField passwordField,
                                           TextField databaseField,
                                           TextField schemaField,
                                           ChoiceBox<String> dbTypeChoice) {
        String name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String username = userNameField.getText();
        String password = passwordField.getText();
        String database = databaseField.getText();
        String schema = schemaField.getText();
        String dbType = dbTypeChoice.getValue();

        DataSource dataSource = new DataSource();
        dataSource.setSourceId(sourceId);
        dataSource.setName(name);
        dataSource.setDbType(DbType.of(dbType));
        dataSource.setIp(host);
        dataSource.setPort(port);
        dataSource.setDatabaseName(database);
        dataSource.setSchemaName(schema);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        JdbcHolder jdbcHolder = joinUrl(dataSource);
        dataSource.setJdbcUrl(jdbcHolder.url);
        dataSource.setDriverClassName(jdbcHolder.driver);
        return dataSource;
    }

    public static JdbcHolder joinUrl(DataSourceDTO dataSourceDTO) {
        DbType dataSourceType = DbType.of(dataSourceDTO.getDbType());
        if (ObjectUtil.isNull(dataSourceType)) {
            AlertUtils.showErrorAlert(String.format("数据源类型【%s】不支持", dataSourceDTO.getDbType()));
        }
        return joinUrl(dataSourceType, dataSourceDTO.getIp(), dataSourceDTO.getPort(),
                dataSourceDTO.getDatabaseName(), dataSourceDTO.getSchemaName());
    }

    public static JdbcHolder joinUrl(DataSource dataSource) {
        return joinUrl(dataSource.getDbType(), dataSource.getIp(), dataSource.getPort(),
                dataSource.getDatabaseName(), dataSource.getSchemaName());
    }

    public static JdbcHolder joinUrl(DbType dbType, String ip, String port,
                                     String database, String schema) {
        Map<String, String> param = new HashMap<>();
        param.put("ip", ip);
        param.put("port", port);
        param.put("database", StrUtil.isBlank(database) ? StrUtil.EMPTY : database);

        if (DbType.postgresql.equals(dbType)) {
            param.put("schema", StrUtil.isBlank(schema) ? "public" : schema);
        } else if (DbType.sqlserver.equals(dbType)) {
            param.put("schema", StrUtil.isBlank(schema) ? "dbo" : schema);
        }else {
            param.put("schema", StrUtil.isBlank(schema) ? StrUtil.EMPTY : schema);
        }

        String url = StrUtil.format(dbType.getJdbcUrl(), param);
        return JdbcHolder.builder()
                .driver(dbType.getDriver())
                .url(url)
                .build();
    }
















}
