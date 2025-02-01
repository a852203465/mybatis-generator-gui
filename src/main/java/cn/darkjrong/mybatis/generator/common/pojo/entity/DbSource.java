package cn.darkjrong.mybatis.generator.common.pojo.entity;

import cn.darkjrong.mybatis.generator.common.enums.DbType;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据源
 *
 * @author Rong.Jia
 * @date 2025/01/21
 */
@Data
@TableName("db_source")
public class DbSource {

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源编码
     */
    private String code;

    /**
     * 数据库类型, 详见{@link DbType}
     */
    private String dbType;

    /**
     * 连接地址
     */
    private String jdbcUrl;

    /**
     * 驱动
     */
    private String driverClassName;

    /**
     * 连接IP
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
     * 数据库模式
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

    /**
     * 添加人
     */
    private String createdUser;

    /**
     * 添加时间
     */
    private Long createdTime;

    /**
     * 修改人
     */
    private String updatedUser;

    /**
     * 修改时间
     */
    private Long updatedTime;

    /**
     * 描述
     */
    private String description;


}
