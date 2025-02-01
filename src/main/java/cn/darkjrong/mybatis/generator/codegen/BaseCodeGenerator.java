package cn.darkjrong.mybatis.generator.codegen;

import cn.darkjrong.mybatis.generator.common.domain.ColumnOverride;
import cn.darkjrong.mybatis.generator.common.domain.IgnoredColumn;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.xdcplus.mp.service.BaseService;
import com.xdcplus.mp.service.impl.BaseServiceImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseCodeGenerator implements CodeGenerator {

    @Override
    public void codeGen(CodeGenParam param) {
        autoGenerator(param);
    }

    protected void autoGenerator(CodeGenParam param) {
        DataSourceConfig.Builder dsBuilder = new DataSourceConfig.Builder(getJdbcUrl(param), param.getUsername(), param.getPassword());
        dsBuilder.schema(param.getSchema());
        FastAutoGenerator.create(dsBuilder)
                .globalConfig(builder -> {
                    builder.author(param.getAuthor())
                            .disableOpenDir()
                            .dateType(DateType.SQL_PACK);
                    if (param.getFileOverride()) builder.fileOverride();
                    builder.outputDir(getPath(param.getProjectPath(), param.getJavaSourcePath()));
                })
                .packageConfig((builder) -> {
                    builder.parent(param.getParentPath());
                    Map<OutputFile, String> pathInfos = new HashMap<>();
                    pathInfos.put(OutputFile.mapperXml, getPath(param.getProjectPath(), param.getJavaResourcePath(), param.getMapperXmlPath()));
                    String path = getPath(param.getProjectPath(),param.getJavaSourcePath(),param.getParentPath());

                    if (StrUtil.isNotBlank(param.getModuleName())) {
                        builder.moduleName(param.getModuleName());
                    }
                    if (StrUtil.isNotBlank(param.getEntityPath())) {
                        builder.entity(param.getEntityPath());
                    }
                    if (StrUtil.isNotBlank(param.getMapperPath())) {
                        builder.mapper(param.getMapperPath());
                    }
                   if (StrUtil.isNotBlank(param.getVoPath())) {
                       builder.vo(param.getVoPath());
                       pathInfos.put(OutputFile.vo, getPath(path, param.getVoPath()));
                    }
                    if (StrUtil.isNotBlank(param.getDtoPath())) {
                        builder.dto(param.getDtoPath());
                        pathInfos.put(OutputFile.dto, getPath(path, param.getDtoPath()));
                    }
                    if (StrUtil.isNotBlank(param.getQueryPath())) {
                        builder.query(param.getQueryPath());
                        pathInfos.put(OutputFile.query, getPath(path, param.getQueryPath()));
                    }
                    if (StrUtil.isNotBlank(param.getFilterDtoPath())) {
                        builder.filterDTO(param.getFilterDtoPath());
                        pathInfos.put(OutputFile.filterDTO, getPath(path, param.getFilterDtoPath()));
                    }
                    builder.pathInfo(pathInfos);

                })
                .injectionConfig(builder -> {
                    Map<String, String> customFile = new HashMap<>();
                    customFile.put("DTO.java", "templates/vms/other/dto.java.vm");
                    customFile.put("VO.java", "templates/vms/other/vo.java.vm");
                    customFile.put("FilterDTO.java", "templates/vms/other/filterDTO.java.vm");
                    customFile.put("Query.java", "templates/vms/other/query.java.vm");
                    builder.customFile(customFile);
                })
                .templateConfig(builder -> {
                    builder.controller("templates/vms/controller.java.vm")
                            .service("templates/vms/service.java.vm")
                            .serviceImpl("templates/vms/serviceImpl.java.vm")
                            .mapper("templates/vms/mapper.java.vm")
                            .mapperXml("templates/vms/mapper.xml.vm")
                            .entity("templates/vms/entity.java.vm");
                })
                .strategyConfig((builder) -> {
                    builder.addInclude(param.getTableName());
                    builder.addTablePrefix(param.getTablePrefix())
                            .mapperBuilder()
                            .enableBaseColumnList()
                            .enableBaseResultMap();

                    if (param.getEnableLombok()) {
                        builder.entityBuilder().enableLombok();
                    }
                    if (param.getEnableSchema()) {
                        builder.enableSchema();
                    }

                    Service.Builder serviceBuilder = builder.serviceBuilder();
                    serviceBuilder.formatServiceFileName("%sService");
                    if (param.getGenBaseService()) {
                        serviceBuilder.superServiceClass(BaseService.class)
                                .superServiceImplClass(BaseServiceImpl.class);
                    }

                    if (CollectionUtil.isNotEmpty(param.getIgnoredColumns())) {
                        String[] ignoreColumns = param.getIgnoredColumns()
                                .stream().map(IgnoredColumn::getColumnName)
                                .collect(Collectors.toList())
                                .toArray(new String[]{});
                        builder.entityBuilder().addIgnoreColumns(ignoreColumns);
                    }

                    if (CollectionUtil.isNotEmpty(param.getColumnOverrides())) {
                        builder.entityBuilder().addColumnOverrides(param.getColumnOverrides().toArray(new ColumnOverride[]{}));
                    }

                    builder.mapperBuilder()
                            .formatMapperFileName("%s" + param.getMapperSuffixName());

                    builder.controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new EnhanceTemplateEngine())
                .execute();

    }

    protected abstract String getJdbcUrl(CodeGenParam param);

    protected String getPath(String first, String... more) {
        Path path = Paths.get(first, more);
        return StrUtil.replace(path.toString(), ".", "/");
    }

}
