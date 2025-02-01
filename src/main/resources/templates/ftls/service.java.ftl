package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${package.Vo}.${entity}VO;
import com.xdcplus.mp.service.BaseService;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
<#if superServiceClass == 'BaseService'>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}, ${entity}, ${entity}VO> {
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
</#if>


}
</#if>
