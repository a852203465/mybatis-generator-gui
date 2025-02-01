package cn.darkjrong.mybatis.generator.service.impl;

import cn.darkjrong.mybatis.generator.common.domain.JdbcHolder;
import cn.darkjrong.mybatis.generator.mapper.DbSourceMapper;
import cn.darkjrong.mybatis.generator.common.pojo.dto.DataSourceDTO;
import cn.darkjrong.mybatis.generator.common.pojo.dto.DbSourceDTO;
import cn.darkjrong.mybatis.generator.common.pojo.entity.DbSource;
import cn.darkjrong.mybatis.generator.common.pojo.query.DbSourceQuery;
import cn.darkjrong.mybatis.generator.common.pojo.vo.DbSourceVO;
import cn.darkjrong.mybatis.generator.service.DbSourceService;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.darkjrong.mybatis.generator.common.utils.DataSourceUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
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
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 数据源信息 服务实现类
 * </p>
 *
 * @author Rong.Jia
 * @since 2024-02-07
 */
@Slf4j
@Service
public class DbSourceServiceImpl extends BaseServiceImpl<DbSourceMapper, DbSource, DbSource, DbSourceVO> implements DbSourceService {

    @Autowired
    private DbSourceMapper dbSourceMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public DbSourceVO saveSource(DbSourceDTO dbSourceDTO) {
        DbSource dbSource = dbSourceMapper.findSourceByName(dbSourceDTO.getName());
        if (ObjectUtil.isNotNull(dbSource)) {
            AlertUtils.showErrorAlert("数据源已存在");
        }
        dbSource = dbSourceMapper.findSourceUnique(dbSourceDTO.getDbType(), dbSourceDTO.getIp(),
                dbSourceDTO.getPort(), dbSourceDTO.getDatabaseName(), dbSourceDTO.getSchemaName());
        if (ObjectUtil.isNotNull(dbSource)) {
            AlertUtils.showErrorAlert("数据源已存在");
        }

        DataSourceDTO dataSourceDTO = new DataSourceDTO();
        BeanUtil.copyProperties(dbSourceDTO, dataSourceDTO);
        JdbcHolder jdbcHolder = DataSourceUtils.joinUrl(dataSourceDTO);

        dbSource = new DbSource();
        BeanUtil.copyProperties(dbSourceDTO, dbSource);
        dbSource.setCode(IdUtil.fastSimpleUUID());
        dbSource.setDriverClassName(jdbcHolder.driver);
        dbSource.setJdbcUrl(jdbcHolder.url);
        dbSource.setCreatedTime(DateUtil.current());
        this.saveOrUpdate(dbSource);
        return this.objectConversion(dbSource);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateSource(DbSourceDTO dbSourceDTO) {
        DbSource dbSource = this.getById(dbSourceDTO.getId());
        if (ObjectUtil.isNull(dbSource)) {
            AlertUtils.showErrorAlert("数据源信息不存在");
        }

        DbSource dbSourceEntity = dbSourceMapper.findSourceByName(dbSourceDTO.getName());
        if (ObjectUtil.isNotNull(dbSourceEntity) && ObjectUtil.notEqual(dbSourceEntity.getCode(), dbSource.getCode())) {
            log.error("**********,updateSource(),数据源名【{}】已存在", dbSourceDTO.getName());
            AlertUtils.showErrorAlert("数据源已存在");
        }
        dbSourceEntity = dbSourceMapper.findSourceUnique(dbSourceDTO.getDbType(), dbSourceDTO.getIp(),
                dbSourceDTO.getPort(), dbSourceDTO.getDatabaseName(), dbSourceDTO.getSchemaName());
        if (ObjectUtil.isNotNull(dbSourceEntity) && ObjectUtil.notEqual(dbSourceEntity.getCode(), dbSource.getCode())) {
            AlertUtils.showErrorAlert("数据源已存在");
        }

        DataSourceDTO dataSourceDTO = new DataSourceDTO();
        BeanUtil.copyProperties(dbSourceDTO, dataSourceDTO);
        JdbcHolder jdbcHolder = DataSourceUtils.joinUrl(dataSourceDTO);

        BeanUtil.copyProperties(dbSourceDTO, dbSource);
        dbSource.setDriverClassName(jdbcHolder.driver);
        dbSource.setJdbcUrl(jdbcHolder.url);
        dbSource.setUpdatedTime(DateUtil.current());
        this.updateById(dbSource);
    }

    @Override
    public PageVO<DbSourceVO> page(PageDTO pageDTO) {
        DbSourceQuery query = new DbSourceQuery();
        BeanUtil.copyProperties(pageDTO, query);

        if (pageDTO.getCurrentPage() > NumberConstant.ZERO) {
            PageableUtils.basicPage(pageDTO);
        }
        PageInfo<DbSource> pageInfo = new PageInfo<>(dbSourceMapper.findSource(query));
        return PropertyUtils.copyProperties(pageInfo, this.objectConversion(pageInfo.getList()));
    }

    @Override
    public DbSourceVO findSourceByCode(String code) {
        return this.objectConversion(dbSourceMapper.findSourceByCode(code));
    }

    @Override
    public DbSourceVO queryById(Serializable id) {
        return this.objectConversion(this.getById(id));
    }

    @Override
    public DbSourceVO findSourceUnique(String dbType, String ip, String port, String database, String schema) {
        return this.objectConversion(dbSourceMapper.findSourceUnique(dbType, ip, port, database, schema));
    }

    @Override
    public List<DbSourceVO> findSourceByDbInfo(String dbType, String ip, String port, String database) {
        return this.objectConversion(dbSourceMapper.findSourceByDbInfo(dbType, ip, port, database));
    }

    @Override
    public List<DbSourceVO> findSourcesByIds(List<Long> ids) {
        if (CollectionUtil.isNotEmpty(ids)) {
            return this.objectConversion(dbSourceMapper.findSourcesByIds(ids));
        }
        return Collections.emptyList();
    }

    @Override
    public List<DbSourceVO> findAll() {
        return this.objectConversion(this.list());
    }








}
