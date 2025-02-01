package cn.darkjrong.mybatis.generator.codegen;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 增强 FreeMarker 模板引擎
 * 对自定义的类处理
 *
 * @author Rong.Jia
 * @date 2023/10/31
 */
@Slf4j
public class EnhanceTemplateEngine extends VelocityTemplateEngine {

    protected String getPathInfo(OutputFile outputFile) {
        return getConfigBuilder().getPathInfo().get(outputFile);
    }

    @Override
    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {

        // 可以调用 tableInfo 的getFieldNames方法获得所有的列
//        this.printTableColumn(tableInfo);

        // objectMap 里的key可以在ftl文件中直接引用
        // https://copyfuture.com/blogs-details/20210404114118659h
        String entityName = tableInfo.getEntityName();
//        String otherPath = this.getPathInfo(OutputFile.other);

        customFile.forEach((key, value) -> {
            String path = null;
            if (StrUtil.containsIgnoreCase(key,"dto")) {
                path = this.getPathInfo(OutputFile.dto);
            }else if (StrUtil.containsIgnoreCase(key,"vo")) {
                path = this.getPathInfo(OutputFile.vo);
            }else if (StrUtil.containsIgnoreCase(key,"filterDTO")) {
                path = this.getPathInfo(OutputFile.filterDTO);
            }else if (StrUtil.containsIgnoreCase(key,"query")) {
                path = this.getPathInfo(OutputFile.query);

            }
            String fileName = String.format(path + "/" + entityName + "%s", key);
            this.outputFile(new File(fileName), objectMap, value);
        });
    }

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            ConfigBuilder config = this.getConfigBuilder();
            List<TableInfo> tableInfoList = config.getTableInfoList();
            tableInfoList.forEach(tableInfo -> {
                Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
                Optional.ofNullable(config.getInjectionConfig()).ifPresent(t -> {
                    t.beforeOutputFile(tableInfo, objectMap);
                    // 输出自定义文件
                    outputCustomFile(t.getCustomFile(), tableInfo, objectMap);
                });
                // Mp.java
                outputEntity(tableInfo, objectMap);
                // mapper and xml
                outputMapper(tableInfo, objectMap);
                // service
                outputService(tableInfo, objectMap);
                // MpController.java
                outputController(tableInfo, objectMap);
            });
        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    /**
     * 获得所有的表列名
     *
     * @param tableInfo 表信息
     */
    private void printTableColumn(TableInfo tableInfo) {
        System.out.println("所有的列名：" + tableInfo.getFieldNames());
    }
}
