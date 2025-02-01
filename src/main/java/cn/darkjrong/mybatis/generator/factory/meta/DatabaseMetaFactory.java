package cn.darkjrong.mybatis.generator.factory.meta;

import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.common.domain.TableMeta;
import cn.darkjrong.mybatis.generator.common.enums.DbType;
import cn.darkjrong.mybatis.generator.service.DbSourceService;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * meta信息工厂
 *
 * @author Rong.Jia
 * @date 2023/05/10
 */
@Slf4j
@Component
public class DatabaseMetaFactory {

    @Autowired
    private List<DataBaseMetaProcessor> processors;

    @Autowired
    private DbSourceService dbSourceService;

    /**
     * 根据数据库类型返回对应的接口
     *
     * @param datasource 数据源
     * @return {@link DataBaseMetaProcessor}
     */
    private DataBaseMetaProcessor getByDb(DataSource datasource) {
        DbType dbType = datasource.getDbType();
        DataBaseMetaProcessor processor = processors.stream()
                .filter(a -> a.supports(dbType)).findAny().orElse(null);
        
        if (ObjectUtil.isNull(processor)) {
            throw new UnsupportedOperationException("暂不支持的类型：".concat(dbType.name()));
        }
        return processor;
    }

    /**
     * 查询表
     *
     * @param dataSource 数据源配置
     * @return {@link List}<{@link String}>
     */
    public List<String> getTables(DataSource dataSource) {
        DataBaseMetaProcessor dataBaseMetaProcessor = getByDb(dataSource);
        return dataBaseMetaProcessor.getTables(dataSource);
    }

    /**
     * 获取表元信息
     *
     * @param tableName 表名
     * @return {@link TableMeta}
     */
    public TableMeta getTableMeta(DataSource dataSource, String tableName) {
        DataBaseMetaProcessor dataBaseMetaProcessor = getByDb(dataSource);
        return dataBaseMetaProcessor.getTableMeta(dataSource, tableName);
    }

    /**
     * 存在表
     *
     * @param tableName        表名
     * @param dataSource 数据源配置
     * @return {@link Boolean}
     */
    public Boolean existTable(DataSource dataSource, String tableName) {
        DataBaseMetaProcessor dataBaseMetaProcessor = getByDb(dataSource);
        return dataBaseMetaProcessor.existTable(dataSource, tableName);
    }

    /**
     * 测试连接
     *
     * @param datasource 数据源信息
     * @return {@link Boolean}
     */
    public Boolean testConnection(DataSource datasource) {
        DataBaseMetaProcessor dataBaseMetaProcessor = getByDb(datasource);
        return dataBaseMetaProcessor.testConnection(datasource);
    }


}
