package cn.darkjrong.mybatis.generator.service;

import cn.darkjrong.mybatis.generator.common.pojo.dto.GeneratorConfigInfoDTO;
import cn.darkjrong.mybatis.generator.common.pojo.entity.GeneratorConfigInfo;
import cn.darkjrong.mybatis.generator.common.pojo.vo.GeneratorConfigInfoVO;
import com.xdcplus.mp.service.BaseService;

import java.util.List;

/**
 * <p>
 * 生成配置信息 服务类
 * </p>
 *
 * @author Rong.Jia
 * @since 2025-01-30
 */

public interface GeneratorConfigInfoService extends BaseService<GeneratorConfigInfo, GeneratorConfigInfo, GeneratorConfigInfoVO> {

    /**
     * 保存生成配置信息
     * @param generatorConfigInfoDTO 新增生成配置信息参数
     */
    void saveGeneratorConfigInfo(GeneratorConfigInfoDTO generatorConfigInfoDTO);

    /**
     * update生成配置信息
     * @param generatorConfigInfoDTO 修改生成配置信息参数
     */
    void updateGeneratorConfigInfo(GeneratorConfigInfoDTO generatorConfigInfoDTO);

    /**
     * 按代码删除生成器配置信息
     *
     * @param code 编码
     */
    void deleteGeneratorConfigInfoByCode(String code);

    /**
     * 按代码删除生成器配置信息
     *
     * @param code 编码
     * @return {@link GeneratorConfigInfoVO }
     */
    GeneratorConfigInfoVO findGeneratorConfigInfoByCode(String code);

    /**
     * 查询全部
     *
     * @return {@link List }<{@link GeneratorConfigInfoVO }>
     */
    List<GeneratorConfigInfoVO> findAll();










}

