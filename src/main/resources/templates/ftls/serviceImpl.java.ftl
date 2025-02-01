package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import ${package.Vo}.${entity}VO;

import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import com.xdcplus.mp.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
    <#if superServiceImplClass == 'BaseServiceImpl'>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}, ${entity}, ${entity}VO> implements ${table.serviceName} {
    <#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    </#if>

    @Autowired
    private ${table.mapperName} ${table.mapperName?uncap_first};

    <#if superServiceImplClass == 'BaseServiceImpl'>
    @Override
    public ${entity}VO queryById(Serializable id) {
        return this.objectConversion(this.getById(id));
    }
    </#if>

}
</#if>
