package cn.darkjrong.mybatis.generator.factory.converts.names;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 属性名称转换
 *
 * @author Rong.Jia
 * @date 2025/01/28
 */
public interface NameConvert {

    /**
     * 执行实体名称转换
     *
     * @param tableName      表名
     * @param namingStrategy 命名策略
     * @param tablePrefix    表前缀
     * @param tableSuffix    表后缀
     * @return {@link String }
     */
    String entityNameConvert(String tableName, NamingStrategy namingStrategy, String tablePrefix, String tableSuffix);

    /**
     * 执行属性名称转换
     *
     * @param fieldName      字段名称
     * @param namingStrategy 命名策略
     * @param fieldPrefix    字段前缀
     * @param fieldSuffix    字段后缀
     * @return {@link String }
     */
    String propertyNameConvert(String fieldName, NamingStrategy namingStrategy, String fieldPrefix, String fieldSuffix);

    /**
     * 执行实体名称转换
     *
     * @param tableName      表名
     * @param namingStrategy 命名策略
     * @return {@link String }
     */
    default String entityNameConvert(String tableName, NamingStrategy namingStrategy) {
        return entityNameConvert(tableName, namingStrategy, null, null);
    }

    /**
     * 执行属性名称转换
     *
     * @param fieldName      字段名称
     * @param namingStrategy 命名策略
     * @return {@link String }
     */
    default String propertyNameConvert(String fieldName, NamingStrategy namingStrategy) {
        return propertyNameConvert(fieldName, namingStrategy, null, null);
    }










}
