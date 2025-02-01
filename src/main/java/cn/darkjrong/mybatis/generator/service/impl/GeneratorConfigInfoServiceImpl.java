package cn.darkjrong.mybatis.generator.service.impl;

import cn.darkjrong.mybatis.generator.common.pojo.dto.GeneratorConfigInfoDTO;
import cn.darkjrong.mybatis.generator.common.pojo.entity.GeneratorConfigInfo;
import cn.darkjrong.mybatis.generator.common.pojo.query.GeneratorConfigInfoQuery;
import cn.darkjrong.mybatis.generator.common.pojo.vo.GeneratorConfigInfoVO;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.darkjrong.mybatis.generator.mapper.GeneratorConfigInfoMapper;
import cn.darkjrong.mybatis.generator.service.GeneratorConfigInfoService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.xdcplus.core.exceptions.XdcRuntimeException;
import com.xdcplus.core.lang.constants.NumberConstant;
import com.xdcplus.mp.service.impl.BaseServiceImpl;
import com.xdcplus.mp.utils.PageableUtils;
import com.xdcplus.mp.utils.PropertyUtils;
import com.xdcplus.pager.dto.PageDTO;
import com.xdcplus.pager.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 生成配置信息 服务实现类
 * </p>
 *
 * @author Rong.Jia
 * @since 2025-01-30
 */
@Slf4j
@Service
public class GeneratorConfigInfoServiceImpl extends BaseServiceImpl<GeneratorConfigInfoMapper, GeneratorConfigInfo, GeneratorConfigInfo, GeneratorConfigInfoVO> implements GeneratorConfigInfoService {

    @Autowired
    private GeneratorConfigInfoMapper generatorConfigInfoMapper;

    @Override
    public GeneratorConfigInfoVO queryById(Serializable id) {
        return this.objectConversion(this.getById(id));
    }

    public PageVO<GeneratorConfigInfoVO> page(PageDTO pageDTO) {
        GeneratorConfigInfoQuery query = new GeneratorConfigInfoQuery();
        BeanUtil.copyProperties(pageDTO, query);

        if (pageDTO.getCurrentPage() > NumberConstant.ZERO) {
            PageableUtils.basicPage(pageDTO);
        }

        PageInfo<GeneratorConfigInfo> pageInfo = new PageInfo<>(generatorConfigInfoMapper.findGeneratorConfigInfo(query));
        return PropertyUtils.copyProperties(pageInfo, this.objectConversion(pageInfo.getList()));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveGeneratorConfigInfo(GeneratorConfigInfoDTO generatorConfigInfoDTO) {
        GeneratorConfigInfo generatorConfigInfo = generatorConfigInfoMapper.findGeneratorConfigInfoByName(generatorConfigInfoDTO.getName());
        if (ObjectUtil.isNotNull(generatorConfigInfo)) {
            log.error("saveGeneratorConfigInfo(),配置信息【{}】已存在", generatorConfigInfoDTO.getName());
            AlertUtils.showErrorAlert("配置信息已存在");
            throw new XdcRuntimeException("配置信息已存在");
        }
        generatorConfigInfo = new GeneratorConfigInfo();
        BeanUtil.copyProperties(generatorConfigInfoDTO, generatorConfigInfo);
        generatorConfigInfo.setCode(IdUtil.fastSimpleUUID());
        this.saveOrUpdate(generatorConfigInfo);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateGeneratorConfigInfo(GeneratorConfigInfoDTO generatorConfigInfoDTO) {
        GeneratorConfigInfo generatorConfigInfo = this.getById(generatorConfigInfoDTO.getId());
        if (ObjectUtil.isNull(generatorConfigInfo)) {
            log.error("配置信息【{}】不存在", generatorConfigInfoDTO.getId());
            AlertUtils.showErrorAlert("配置信息不存在");
            throw new XdcRuntimeException("配置信息不存在");
        }
        GeneratorConfigInfo info = generatorConfigInfoMapper.findGeneratorConfigInfoByName(generatorConfigInfoDTO.getName());
        if (ObjectUtil.isNotNull(info) && !StrUtil.equals(info.getCode(), generatorConfigInfoDTO.getCode())) {
            log.error("updateGeneratorConfigInfo(),配置信息【{}】已存在", generatorConfigInfoDTO.getName());
            AlertUtils.showErrorAlert("配置信息已存在");
            throw new XdcRuntimeException("配置信息已存在");
        }
        BeanUtil.copyProperties(generatorConfigInfoDTO, generatorConfigInfo);
        this.updateById(generatorConfigInfo);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteGeneratorConfigInfoByCode(String code) {
        GeneratorConfigInfo generatorConfigInfo = generatorConfigInfoMapper.findGeneratorConfigInfoByCode(code);
        if (ObjectUtil.isNotNull(generatorConfigInfo)) {
            delete(generatorConfigInfo.getId());
        }
    }

    @Override
    public GeneratorConfigInfoVO findGeneratorConfigInfoByCode(String code) {
        GeneratorConfigInfo generatorConfigInfo = generatorConfigInfoMapper.findGeneratorConfigInfoByCode(code);
        return this.objectConversion(generatorConfigInfo);
    }

    @Override
    public List<GeneratorConfigInfoVO> findAll() {
        return this.objectConversion(this.list());
    }


}
