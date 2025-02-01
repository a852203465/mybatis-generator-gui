package cn.darkjrong.mybatis.generator.mapper;

import cn.darkjrong.mybatis.generator.common.pojo.entity.GeneratorConfigInfo;
import cn.darkjrong.mybatis.generator.common.pojo.query.GeneratorConfigInfoQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 生成配置信息 Mapper 接口
 * </p>
 *
 * @author Rong.Jia
 * @since 2025-01-30
 */
public interface GeneratorConfigInfoMapper extends BaseMapper<GeneratorConfigInfo> {

    /**
     * 查询生成配置信息
     * @param query 查询参数
     * @return {@link List }<{@link GeneratorConfigInfo }>
     */
    List<GeneratorConfigInfo> findGeneratorConfigInfo(GeneratorConfigInfoQuery query);

    /**
     * 按名称查找生成器配置信息
     *
     * @param name 名字
     * @return {@link GeneratorConfigInfo }
     */
    GeneratorConfigInfo findGeneratorConfigInfoByName(@Param("name") String name);

    /**
     * 按代码删除生成器配置信息
     *
     * @param code 编码
     */
    void deleteGeneratorConfigInfoByCode(@Param("code") String code);

    /**
     * 按代码删除生成器配置信息
     *
     * @param code 编码
     * @return {@link GeneratorConfigInfo }
     */
    GeneratorConfigInfo findGeneratorConfigInfoByCode(@Param("code") String code);












}
