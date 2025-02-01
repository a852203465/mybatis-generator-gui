package ${package.Query};

<#list table.importPackages as pkg>
import ${pkg};
</#list>

import ${package.Dto}.${entity}FilterDTO;
import lombok.EqualsAndHashCode;
import lombok.Data;

/**
 * <p>
 * ${table.comment!}查询对象
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ${entity}Query extends ${entity}FilterDTO implements Serializable {

    private static final long serialVersionUID = 1L;


}
