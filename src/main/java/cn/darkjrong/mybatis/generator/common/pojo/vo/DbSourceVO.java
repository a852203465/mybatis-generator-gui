package cn.darkjrong.mybatis.generator.common.pojo.vo;

import cn.darkjrong.mybatis.generator.common.enums.DbType;
import com.xdcplus.mp.domain.mysql.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 数据源信息VO
 * </p>
 *
 * @author Rong.Jia
 * @since 2024-02-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DbSourceVO extends Base implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 数据源名称
    */
    private String name;

    /**
    * 数据源编码
    */
    private String code;

    /**
    * 驱动
    */
    private String driverClassName;

    /**
    * 连接地址
    */
    private String jdbcUrl;

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
     * 连接IP
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 数据库类型, 详见{@link DbType}
     */
    private String dbType;










}
