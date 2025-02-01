package ${package.Dto};

<#list table.importPackages as pkg>
import ${pkg};
</#list>

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * ${table.comment!}DTO
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@ApiModel("${table.comment!}参数")
public class ${entity}DTO implements Serializable {

    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>

    /**
    * ${field.comment}
    */
    @ApiModelProperty("${field.comment}")
    private <#if field.propertyType=='Blob'>byte[]<#else>${field.propertyType}</#if> ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}
