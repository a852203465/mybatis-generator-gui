package cn.darkjrong.mybatis.generator.common.pojo.dto;

import cn.darkjrong.mybatis.generator.common.enums.DbType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 数据源DTO
 *
 * @author Rong.Jia
 * @date 2024/02/16
 */
@Data
public class DataSourceDTO {

    /**
     * 数据库类型, 详见{@link DbType}
     */
    @NotBlank(message = "数据库类型 不能为空")
    private String dbType;

    /**
     * IP
     */
    @NotBlank(message = "IP不能为空")
    private String ip;

    /**
     * 端口
     */
    @NotBlank(message = "端口不能为空")
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
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;




















}
