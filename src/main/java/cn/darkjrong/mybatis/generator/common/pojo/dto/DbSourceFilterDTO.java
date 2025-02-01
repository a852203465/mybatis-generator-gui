package cn.darkjrong.mybatis.generator.common.pojo.dto;

import cn.darkjrong.mybatis.generator.common.enums.DbType;
import com.xdcplus.pager.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 数据源信息查询DTO
 * </p>
 *
 * @author Rong.Jia
 * @since 2024-02-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DbSourceFilterDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 数据源名称
    */
    private String name;

    /**
     * 数据库类型, 详见{@link DbType}
     */
    private String dbType;

    /**
    * 状态（1:正常 0:停用, -1:未初始化）
    */
    private List<Integer> statuses;

    /**
     * 数据源管理员ID
     */
    private Long adminId;

    /**
     * 项目ID
     */
    private Long projectId;



}
