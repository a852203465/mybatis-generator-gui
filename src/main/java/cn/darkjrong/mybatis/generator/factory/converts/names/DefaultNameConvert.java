package cn.darkjrong.mybatis.generator.factory.converts.names;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * 默认名称转换接口类
 *
 * @author Rong.Jias
 * @date 2025/01/28
 */
@Slf4j
@Component
public class DefaultNameConvert implements NameConvert {

    @Override
    public String entityNameConvert(String tableName, NamingStrategy namingStrategy, String tablePrefix, String tableSuffix) {
        return NamingStrategy.capitalFirst(processName(tableName, namingStrategy,
                StrUtil.isNotBlank(tablePrefix) ? CollectionUtil.newHashSet(tablePrefix) : Collections.emptySet(),
                StrUtil.isNotBlank(tableSuffix) ? CollectionUtil.newHashSet(tableSuffix) : Collections.emptySet()));
    }

    @Override
    public String propertyNameConvert(String fieldName, NamingStrategy namingStrategy, String fieldPrefix, String fieldSuffix) {
        return processName(fieldName, namingStrategy,
                StrUtil.isNotBlank(fieldPrefix) ? CollectionUtil.newHashSet(fieldPrefix) : Collections.emptySet(),
                StrUtil.isNotBlank(fieldSuffix) ? CollectionUtil.newHashSet(fieldSuffix) : Collections.emptySet());
    }

    private String processName(String name, NamingStrategy strategy, Set<String> prefix, Set<String> suffix) {
        String propertyName = name;
        // 删除前缀
        if (CollectionUtil.isNotEmpty(prefix)) {
            propertyName = NamingStrategy.removePrefix(propertyName, prefix);
        }
        // 删除后缀
        if (CollectionUtil.isNotEmpty(suffix)) {
            propertyName = NamingStrategy.removeSuffix(propertyName, suffix);
        }
        if (StringUtils.isBlank(propertyName)) {
            throw new RuntimeException(String.format("%s 的名称转换结果为空，请检查是否配置问题", name));
        }
        // 下划线转驼峰
        if (NamingStrategy.underline_to_camel.equals(strategy)) {
            return NamingStrategy.underlineToCamel(propertyName);
        }
        return propertyName;
    }
}
