package cn.darkjrong.mybatis.generator.common.domain;

import cn.darkjrong.mybatis.generator.common.enums.DbType;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据源
 *
 * @author Rong.Jia
 * @date 2023/05/10
 */
@Data
public class DataSource implements Serializable {

    private static final long serialVersionUID = 1268701215878013011L;

    /**
     * 主键
     */
    private Long sourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * DB类型
     */
    private DbType dbType;

    /**
     * jdbc驱动类
     */
    private String driverClassName;

    /**
     * 连接URL
     */
    private String jdbcUrl;

    /**
     * IP
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 数据库名
     */
    private String databaseName;

    /**
     * schema名
     */
    private String schemaName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;




}
