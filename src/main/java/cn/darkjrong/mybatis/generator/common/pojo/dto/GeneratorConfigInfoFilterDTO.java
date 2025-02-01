package cn.darkjrong.mybatis.generator.common.pojo.dto;

import com.xdcplus.pager.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 生成配置信息查询DTO
 * </p>
 *
 * @author Rong.Jia
 * @since 2025-01-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GeneratorConfigInfoFilterDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 项目目录
     */
    private String projectPath;
    /**
     * 作者
     */
    private String author;
    /**
     * JAVA源码目录
     */
    private String javaSourcePath;
    /**
     * 父级目录
     */
    private String parentPath;
    /**
     * 实体包名
     */
    private String entityPackage;
    /**
     * mapper包名
     */
    private String mapperPackage;
    /**
     * mapper后缀名
     */
    private String mapperSuffixName;
    /**
     * JAVA资源目录
     */
    private String javaResourcePath;
    /**
     * xml目录
     */
    private String mapperXmlPath;
    /**
     * 生成VO(1:是,0:否)
     */
    private Integer genVo;
    /**
     * 生成BaseService(1:是,0:否)
     */
    private Integer genBaeService;
    /**
     * 生成DTO(1:是,0:否)
     */
    private Integer genDto;
    /**
     * 生成Query(1:是,0:否)
     */
    private Integer genQuery;
    /**
     * 使用Lombok(1:是,0:否)
     */
    private Integer useLombokPlugin;
    /**
     * 覆盖原XML(1:是,0:否)
     */
    private Integer overrideXml;
    /**
     * 使用Schema前缀(1:是,0:否)
     */
    private Integer useSchemaPrefix;

}
