package cn.darkjrong.mybatis.generator.mapper;

import cn.darkjrong.mybatis.generator.common.pojo.entity.DbSource;
import cn.darkjrong.mybatis.generator.common.pojo.query.DbSourceQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据源信息 Mapper 接口
 * </p>
 *
 * @author Rong.Jia
 * @since 2024-02-07
 */
public interface DbSourceMapper extends BaseMapper<DbSource> {

    /**
     * 查询源
     *
     * @param query 查询
     * @return {@link List}<{@link DbSource}>
     */
    List<DbSource> findSource(DbSourceQuery query);

    /**
     * 查询源根据名字
     *
     * @param name 名字
     * @return {@link DbSource}
     */
    DbSource findSourceByName(@Param("name") String name);

    /**
     * 查询源根据编码
     *
     * @param code 编码
     * @return {@link DbSource}
     */
    DbSource findSourceByCode(@Param("code") String code);

    /**
     * 查询唯一数据源
     *
     * @param dbType   DB 类型
     * @param ip       IP
     * @param port     端口
     * @param database 数据库
     * @param schema   schema
     * @return {@link DbSource}
     */
    DbSource findSourceUnique(@Param("dbType") String dbType, @Param("ip") String ip, @Param("port") String port,
                              @Param("database") String database, @Param("schema") String schema);

    /**
     * 查询数据源
     *
     * @param dbType   DB 类型
     * @param ip       IP
     * @param port     端口
     * @param database 数据库
     * @return {@link List}<{@link DbSource}>
     */
    List<DbSource> findSourceByDbInfo(@Param("dbType") String dbType, @Param("ip") String ip,
                                      @Param("port") String port, @Param("database") String database);

    /**
     * 根据ID集合查询数据源集合
     *
     * @param ids ID集合
     * @return {@link List }<{@link DbSource }>
     */
    List<DbSource> findSourcesByIds(@Param("ids") List<Long> ids);










}
