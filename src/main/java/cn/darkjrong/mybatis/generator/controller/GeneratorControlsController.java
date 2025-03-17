package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.codegen.CodeGenParam;
import cn.darkjrong.mybatis.generator.codegen.CodeGenUtils;
import cn.darkjrong.mybatis.generator.codegen.DbType;
import cn.darkjrong.mybatis.generator.common.domain.*;
import cn.darkjrong.mybatis.generator.common.enums.ColumnType;
import cn.darkjrong.mybatis.generator.common.enums.FXMLPage;
import cn.darkjrong.mybatis.generator.common.pojo.dto.GeneratorConfigInfoDTO;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import cn.darkjrong.mybatis.generator.factory.converts.names.NameConvert;
import cn.darkjrong.mybatis.generator.factory.converts.types.TypeConverts;
import cn.darkjrong.mybatis.generator.factory.meta.DatabaseMetaFactory;
import cn.darkjrong.mybatis.generator.service.GeneratorConfigInfoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.*;

/**
 * 生成操作控制器
 *
 * @author Rong.Jia
 * @date 2025/01/24
 */
@Slf4j
@ViewController
public class GeneratorControlsController extends BaseFxController {

    @FXML
    private TextField mapperXmlFolderField;
    @FXML
    private CheckBox voCheckBox;
    @FXML
    private CheckBox dtoCheckBox;
    @FXML
    private CheckBox queryCheckBox;
    @FXML
    private CheckBox serviceCheckBox;
    @FXML
    private TextField mapperFolderField;
    @FXML
    private TextField entityFolderField;
    @FXML
    private TextField javaTargetProject;
    @FXML
    private TextField parentFolderField;
    @FXML
    private TextField mappingTargetProject;
    @FXML
    private TextField mapperNameField;
    @FXML
    private TextField projectFolderField;

    @FXML
    private CheckBox overrideXML;

    @FXML
    private CheckBox useLombokPlugin;

    @FXML
    private CheckBox useSchemaPrefix;

    @FXML
    private TextField authorField;

    @FXML
    private TableView<SelectedTable> selectedTableListView;

    @FXML
    private TableColumn<SelectedTable, String> tableNameColumn;
    @FXML
    private TableColumn<SelectedTable, String> tablePrefixNameColumn;
    @FXML
    private TableColumn<SelectedTable, String> tableClassNameColumn;
    @FXML
    private TableColumn<SelectedTable, String> tableKeyColumn;

    @FXML
    private TableColumn<SelectedTable, Void> tableActionsColumn;

    @Autowired
    private NameConvert nameConvert;

    @Autowired
    private DatabaseMetaFactory databaseMetaFactory;

    @Autowired
    private GeneratorConfigInfoService generatorConfigInfoService;

    private Set<SelectedTable> selectedTables = new HashSet<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        tablePrefixNameColumn.setCellValueFactory(new PropertyValueFactory<>("tablePrefix"));
        tableClassNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        tableKeyColumn.setCellValueFactory(new PropertyValueFactory<>("pkName"));

        tablePrefixNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePrefixNameColumn.setOnEditCommit(event -> {
            SelectedTable selectedTable = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newValue = event.getNewValue();
            selectedTable.setTablePrefix(newValue);
            if (StrUtil.isNotBlank(newValue)) {
                String className = StrUtil.removePrefix(selectedTable.getTableName(), newValue);
                selectedTable.setClassName(StrUtil.upperFirst(StrUtil.toCamelCase(className)));
            } else {
                selectedTable.setClassName(StrUtil.upperFirst(StrUtil.toCamelCase(selectedTable.getTableName())));
            }
            selectedTableListView.refresh();
        });

        tableKeyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tableKeyColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPkName(event.getNewValue());
        });

        tableActionsColumn.setCellFactory(column -> {
            return new TableCell<SelectedTable, Void>() {
                private final Button editButton = new Button("定制列");
                private final Button deleteButton = new Button("删除");
                private final HBox hBox = new HBox(editButton, deleteButton);
                { // 初始化块，用于设置按钮事件处理程序和样式等
                    hBox.setSpacing(10); // 设置按钮之间的间距
                    editButton.setOnAction(event -> handleEditAction(getIndex(), getTableView()));
                    deleteButton.setOnAction(event -> handleDeleteAction(getIndex(), getTableView()));
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(hBox); // 设置单元格的图形为HBox，包含按钮
                    }
                }
            };
        });
    }

    @FXML
    public void chooseProjectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(getPrimaryStage());
        if (selectedFolder != null) {
            projectFolderField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    public void generateCode() {
        if (CollectionUtil.isEmpty(selectedTables)) {
            AlertUtils.showWarnAlert("请先在左侧选择数据库表");
            return;
        }
        if (!validateConfig()) return;
        try {
            for (SelectedTable selectedTable : selectedTables) {
                CodeGenParam param = new CodeGenParam();
                DataSource selectedDatabaseConfig = selectedTable.getSelectedDataSource();

                param.setProjectPath(projectFolderField.getText());
                param.setDbType(DbType.valueOf(selectedDatabaseConfig.getDbType().name()));
                param.setDatabase(selectedDatabaseConfig.getDatabaseName());
                param.setSchema(selectedDatabaseConfig.getSchemaName());
                param.setUsername(selectedDatabaseConfig.getUsername());
                param.setPassword(selectedDatabaseConfig.getPassword());
                param.setHost(selectedDatabaseConfig.getIp());
                param.setPort(selectedDatabaseConfig.getPort());
                param.setAuthor(authorField.getText());
                param.setEnableSchema(useSchemaPrefix.isSelected());
                param.setTablePrefix(selectedTable.getTablePrefix());
                param.setTableName(selectedTable.getTableName());
                param.setJavaSourcePath(javaTargetProject.getText());
                param.setParentPath(parentFolderField.getText());
                param.setEntityPath("common.pojo." + entityFolderField.getText());
                param.setMapperPath(mapperFolderField.getText());
                param.setMapperSuffixName(mapperNameField.getText());
                param.setJavaResourcePath(mappingTargetProject.getText());
                param.setMapperXmlPath(mapperXmlFolderField.getText());
                param.setEnableLombok(useLombokPlugin.isSelected());
                param.setFileOverride(overrideXML.isSelected());
                param.setGenBaseService(serviceCheckBox.isSelected());
                param.setColumnOverrides(selectedTable.getColumnOverride());
                param.setIgnoredColumns(selectedTable.getIgnoredColumn());
                param.setColumnNameStrategy(selectedTable.getColumnNameStrategy());
                if (voCheckBox.isSelected()) {
                    param.setVoPath("common.pojo.vo");
                }
                if (dtoCheckBox.isSelected()) {
                    param.setDtoPath("common.pojo.dto");
                }
                if (queryCheckBox.isSelected()) {
                    param.setFilterDtoPath("common.pojo.dto");
                    param.setQueryPath("common.pojo.query");
                }
                CodeGenUtils.codeGen(param);
            }
            selectedTableListView.getItems().clear();
            selectedTables.clear();
            projectFolderField.clear();
            parentFolderField.clear();
            AlertUtils.showInfoAlert("生成成功");
        } catch (Exception e) {
            log.error(String.format("代码生成异常 【{}】", e.getMessage()), e);
            AlertUtils.showErrorAlert(e.getMessage());
        }
    }

    private Boolean validateConfig() {
        String projectFolder = projectFolderField.getText();
        if (StrUtil.isEmpty(projectFolder))  {
            AlertUtils.showErrorAlert("项目路径不能为空");
            return Boolean.FALSE;
        }
        if (StringUtils.isAnyEmpty(entityFolderField.getText(), mapperFolderField.getText(), mapperXmlFolderField.getText())) {
            AlertUtils.showErrorAlert("包名不能为空");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @FXML
    public void saveGeneratorConfig() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("保存当前配置");
        dialog.setContentText("请输入配置名称");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String name = result.get();
            if (StrUtil.isEmpty(name)) {
                AlertUtils.showErrorAlert("名称不能为空");
                return;
            }
            log.info("user choose name: {}", name);
            try {
                String projectFolder = projectFolderField.getText();
                String author = authorField.getText();
                Integer useSchema = useSchemaPrefix.isSelected() ? 1 : 0;
                String javaSourcePath = javaTargetProject.getText();
                String parentFolder = parentFolderField.getText();
                String entityFolder = entityFolderField.getText();
                String mapperFolder = mapperFolderField.getText();
                String mapperSuffixName = mapperNameField.getText();
                String javaResourcePath = mappingTargetProject.getText();
                String mapperXmlPath = mapperXmlFolderField.getText();
                Integer useLombok = useLombokPlugin.isSelected() ? 1 : 0;
                Integer overrideXml = overrideXML.isSelected() ? 1 : 0;
                Integer genBaseService = serviceCheckBox.isSelected() ? 1 : 0;
                Integer genVo = voCheckBox.isSelected() ? 1 : 0;
                Integer genDto = dtoCheckBox.isSelected() ? 1 : 0;
                Integer genQuery = queryCheckBox.isSelected() ? 1 : 0;

                GeneratorConfigInfoDTO generatorConfigInfoDTO = new GeneratorConfigInfoDTO();
                generatorConfigInfoDTO.setName(name);
                generatorConfigInfoDTO.setProjectPath(projectFolder);
                generatorConfigInfoDTO.setAuthor(author);
                generatorConfigInfoDTO.setJavaSourcePath(javaSourcePath);
                generatorConfigInfoDTO.setParentPath(parentFolder);
                generatorConfigInfoDTO.setEntityPackage(entityFolder);
                generatorConfigInfoDTO.setMapperPackage(mapperFolder);
                generatorConfigInfoDTO.setMapperSuffixName(mapperSuffixName);
                generatorConfigInfoDTO.setJavaResourcePath(javaResourcePath);
                generatorConfigInfoDTO.setMapperXmlPath(mapperXmlPath);
                generatorConfigInfoDTO.setGenVo(genVo);
                generatorConfigInfoDTO.setGenBaeService(genBaseService);
                generatorConfigInfoDTO.setGenDto(genDto);
                generatorConfigInfoDTO.setGenQuery(genQuery);
                generatorConfigInfoDTO.setUseLombokPlugin(useLombok);
                generatorConfigInfoDTO.setOverrideXml(overrideXml);
                generatorConfigInfoDTO.setUseSchemaPrefix(useSchema);
                generatorConfigInfoService.saveGeneratorConfigInfo(generatorConfigInfoDTO);
                AlertUtils.showErrorAlert("保存配置成功");
            } catch (Exception e) {
                log.error(String.format("保存配置失败, 【%s】", e.getMessage()), e);
                AlertUtils.showErrorAlert("保存配置失败");
            }
        }
    }

    @FXML
    public void openTargetFolder() {
        try {
            Desktop.getDesktop().browse(new File(projectFolderField.getText()).toURI());
        }catch (Exception e) {
            log.error(String.format("打开目录【%s】失败, 【%s】", projectFolderField.getText(), e.getMessage()), e);
            AlertUtils.showErrorAlert("打开目录失败，请检查目录是否填写正确" + e.getMessage());
        }
    }

    public void setField(DataSource selectedDatabaseConfig, String tableName) {
        SelectedTable selectedTable = new SelectedTable();
        selectedTable.setTableName(tableName);
        selectedTable.setUniqueCode(IdUtil.fastSimpleUUID());
        selectedTable.setClassName(StrUtil.upperFirst(StrUtil.toCamelCase(tableName)));
        selectedTable.setSelectedDataSource(selectedDatabaseConfig);
        if (selectedTables.stream().noneMatch(a -> StrUtil.equals(a.getTableName(), selectedTable.getTableName())
                        && StrUtil.equals(a.getSelectedDataSource().getJdbcUrl(), selectedDatabaseConfig.getJdbcUrl()))) {
            selectedTables.add(selectedTable);
        }
        displayTables();
    }

    private void displayTables() {
        if (CollUtil.isEmpty(selectedTables)) {
            log.error("***************** 没有选中的表跳过刷新表格 ******************");
            return;
        }
        selectedTableListView.setItems(FXCollections.observableList(CollectionUtil.newArrayList(selectedTables)));
    }


    private void handleEditAction(int index, TableView<SelectedTable> table) {
        SelectedTable selectedTable = table.getItems().get(index);
        log.info("handleEditAction() selectedTable: {}", selectedTable);
        if (selectedTable == null) {
            AlertUtils.showWarnAlert("请先在左侧选择数据库表");
            return;
        }
        SelectTableColumnController selectTableColumnController = (SelectTableColumnController)showView("定制列", FXMLPage.SELECT_TABLE_COLUMN.getFxml());
        try {
            TableMeta tableMeta = databaseMetaFactory.getTableMeta(selectedTable.getSelectedDataSource(), selectedTable.getTableName());
            Map<String, Column> columns = tableMeta.getColumns();
            List<CustomTableColumn> customTableColumns = new ArrayList<>();
            columns.forEach((key, value) -> {
                CustomTableColumn customTableColumn = new CustomTableColumn();
                customTableColumn.setColumnName(value.getName());
                customTableColumn.setJdbcType(value.getTypeName());
                customTableColumn.setColumnComment(value.getComment());

                ColumnType columnType = TypeConverts.getTypeConvert(com.baomidou.mybatisplus.annotation.DbType.getDbType(selectedTable.getSelectedDataSource().getDbType().name()))
                        .processTypeConvert(value.getTypeName());
                customTableColumn.setJavaType(columnType.getPkg());
                customTableColumn.setPropertyName(nameConvert.propertyNameConvert(value.getName(),
                        NamingStrategy.underline_to_camel, null, null));
                customTableColumns.add(customTableColumn);
            });

            selectTableColumnController.setColumnList(FXCollections.observableList(customTableColumns));
            selectTableColumnController.setSelectedTable(selectedTable);
            selectTableColumnController.showDialogStage();
        } catch (Exception e) {
            log.error(String.format("【%s】设置定制列异常, 【%s】", JSON.toJSONString(selectedTable), e.getMessage()), e);
            AlertUtils.showErrorAlert(e.getMessage());
        }
    }

    private void handleDeleteAction(int index, TableView<SelectedTable> table) {
        SelectedTable selectedTable = table.getItems().get(index);
        log.info("delete selectedTable: {}", selectedTable);
        table.getItems().remove(selectedTable);
        selectedTables.removeIf(selected -> selected.equals(selectedTable));
    }

    /**
     * SET 选中的表列
     *
     * @param selectedTable   所选表
     * @param columnOverrides 列覆盖
     * @param ignoredColumns  忽略列
     * @param columnNameStrategy 列名策略
     */
    public void setSelectTableColumn(SelectedTable selectedTable,
                                     List<ColumnOverride> columnOverrides,
                                     List<IgnoredColumn> ignoredColumns,
                                     NamingStrategy columnNameStrategy) {
        String uniqueCode = selectedTable.getUniqueCode();
        selectedTables.forEach(a -> {
            if (StrUtil.equals(uniqueCode, a.getUniqueCode())) {
                a.setColumnOverride(columnOverrides);
                a.setIgnoredColumn(ignoredColumns);
                a.setColumnNameStrategy(columnNameStrategy);
            }
        });
    }

    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        projectFolderField.setText(generatorConfig.getProjectPath());
        authorField.setText(generatorConfig.getAuthor());
        useSchemaPrefix.setSelected(parseBoolean(generatorConfig.getUseSchemaPrefix()));
        javaTargetProject.setText(generatorConfig.getJavaSourcePath());
        parentFolderField.setText(generatorConfig.getParentPath());
        entityFolderField.setText(generatorConfig.getEntityPackage());
        mapperFolderField.setText(generatorConfig.getMapperPackage());
        mapperNameField.setText(generatorConfig.getMapperSuffixName());
        mappingTargetProject.setText(generatorConfig.getMapperPackage());
        mapperXmlFolderField.setText(generatorConfig.getMapperXmlPath());
        useLombokPlugin.setSelected(parseBoolean(generatorConfig.getUseLombokPlugin()));
        overrideXML.setSelected(parseBoolean(generatorConfig.getOverrideXml()));
        serviceCheckBox.setSelected(parseBoolean(generatorConfig.getGenBaeService()));
        voCheckBox.setSelected(parseBoolean(generatorConfig.getGenVo()));
        dtoCheckBox.setSelected(parseBoolean(generatorConfig.getGenDto()));
        queryCheckBox.setSelected(parseBoolean(generatorConfig.getGenQuery()));
    }

    private Boolean parseBoolean(Integer num) {
        return num == 1 ? Boolean.TRUE : Boolean.FALSE;
    }



}
