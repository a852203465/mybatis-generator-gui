package ${package.Dto};

<#list table.importPackages as pkg>
import ${pkg};
</#list>

import com.xdcplus.pager.dto.PageDTO;

import lombok.EqualsAndHashCode;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * ${table.comment!}查询DTO
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("${table.comment!}查询参数")
public class ${entity}FilterDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.propertyName != 'id'
    && field.propertyName != 'createdUser'
    && field.propertyName != 'createdTime'
    && field.propertyName != 'updatedUser'
    && field.propertyName != 'updatedTime'
    && field.propertyName != 'description'>

    /**
    * ${field.comment}
    */
    @ApiModelProperty("${field.comment}")
    private <#if field.propertyType=='Blob'>byte[]<#else>${field.propertyType}</#if> ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
}
