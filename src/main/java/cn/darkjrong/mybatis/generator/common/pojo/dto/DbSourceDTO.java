package cn.darkjrong.mybatis.generator.common.pojo.dto;

import cn.darkjrong.mybatis.generator.common.enums.DbType;
import com.xdcplus.mp.domain.mysql.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 数据源信息DTO
 * </p>
 *
 * @author Rong.Jia
 * @since 2024-02-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DbSourceDTO extends Base implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 数据源名称
    */
    @NotBlank(message = "数据源名称 不能为空")
    private String name;

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

    /**
    * 数据库名
    */
    @NotBlank(message = "数据库名 不能为空")
    private String databaseName;

    /**
    * 数据库模式
    */
    private String schemaName;

    /**
    * 用户名
    */
    @NotBlank(message = "用户名 不能为空")
    private String username;

    /**
    * 密码
    */
    @NotBlank(message = "密码 不能为空")
    private String password;



}
