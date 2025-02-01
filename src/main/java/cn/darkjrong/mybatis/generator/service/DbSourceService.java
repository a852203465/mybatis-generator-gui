package cn.darkjrong.mybatis.generator.service;

import cn.darkjrong.mybatis.generator.common.pojo.dto.DbSourceDTO;
import cn.darkjrong.mybatis.generator.common.pojo.entity.DbSource;
import cn.darkjrong.mybatis.generator.common.pojo.vo.DbSourceVO;
import com.xdcplus.mp.service.BaseService;

import java.util.List;

/**
 * <p>
 * 数据源信息 服务类
 * </p>
 *
 * @author Rong.Jia
 * @since 2024-02-07
 */
public interface DbSourceService extends BaseService<DbSource, DbSource, DbSourceVO> {

    /**
     * 保存源
     *
     * @param dbSourceDTO TE源DTO
     * @return {@link DbSourceVO }
     */
    DbSourceVO saveSource(DbSourceDTO dbSourceDTO);

    /**
     * 修改源
     *
     * @param dbSourceDTO TE源DTO
     */
    void updateSource(DbSourceDTO dbSourceDTO);

    /**
     * 查询源根据编码
     *
     * @param code 编码
     * @return {@link DbSourceVO}
     */
    DbSourceVO findSourceByCode(String code);

    /**
     * 查询唯一数据源
     *
     * @param dbType   DB 类型
     * @param ip       IP
     * @param port     端口
     * @param database 数据库
     * @param schema   schema
     * @return {@link DbSourceVO}
     */
    DbSourceVO findSourceUnique(String dbType, String ip, String port, String database, String schema);

    /**
     * 查询数据源
     *
     * @param dbType   DB 类型
     * @param ip       IP
     * @param port     端口
     * @param database 数据库
     * @return {@link List}<{@link DbSourceVO}>
     */
    List<DbSourceVO> findSourceByDbInfo(String dbType, String ip, String port, String database);

    /**
     * 根据ID集合查询数据源集合
     *
     * @param ids ID集合
     * @return {@link List }<{@link DbSourceVO }>
     */
    List<DbSourceVO> findSourcesByIds(List<Long> ids);

    /**
     * 查询所有
     *
     * @return {@link List }<{@link DbSourceVO }>
     */
    List<DbSourceVO> findAll();


}
