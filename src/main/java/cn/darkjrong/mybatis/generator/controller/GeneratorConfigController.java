package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.common.domain.GeneratorConfig;
import cn.darkjrong.mybatis.generator.common.pojo.vo.GeneratorConfigInfoVO;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import cn.darkjrong.mybatis.generator.service.GeneratorConfigInfoService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * 生成配置控制器
 *
 * @author Rong.Jia
 * @date 2025/01/30
 */
@Slf4j
@ViewController
public class GeneratorConfigController extends BaseFxController {

    @FXML
    private TableView<GeneratorConfig> configTable;

    @FXML
    private TableColumn<GeneratorConfig, String> nameColumn;

    @FXML
    private TableColumn<GeneratorConfig, String> projectFolderColumn;

    @FXML
    private TableColumn<GeneratorConfig, String> authorColumn;

    @FXML
    private TableColumn<GeneratorConfig, String> parentFolderColumn;

    @FXML
    private TableColumn<GeneratorConfig, String> entityPackageColumn;

    @FXML
    private TableColumn<GeneratorConfig, String> mapperPackageColumn;

    @FXML
    private TableColumn<GeneratorConfig, String> xmlMapperPackageColumn;

    @FXML
    private TableColumn<GeneratorConfig, Void> tableActionsColumn;

    private GeneratorConfigController controller;

    @Autowired
    private GeneratorConfigInfoService generatorConfigInfoService;

    @Autowired
    private GeneratorControlsController generatorControlsController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = this;
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        projectFolderColumn.setCellValueFactory(new PropertyValueFactory<>("projectPath"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        parentFolderColumn.setCellValueFactory(new PropertyValueFactory<>("parentPath"));
        entityPackageColumn.setCellValueFactory(new PropertyValueFactory<>("entityPackage"));
        mapperPackageColumn.setCellValueFactory(new PropertyValueFactory<>("mapperPackage"));
        xmlMapperPackageColumn.setCellValueFactory(new PropertyValueFactory<>("mapperXmlPath"));

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        projectFolderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        parentFolderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        entityPackageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mapperPackageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        xmlMapperPackageColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        projectFolderColumn.setCellFactory(column -> {
            return new TableCell<GeneratorConfig, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setTooltip(null);
                    } else {
                        setText(item);
                        Tooltip tooltip = new Tooltip(item);
                        setTooltip(tooltip);
                    }
                }
            };
        });

        tableActionsColumn.setCellFactory(column -> {
            return new TableCell<GeneratorConfig, Void>() {
                private final Button applyButton = new Button("应用");
                private final Button deleteButton = new Button("删除");
                private final HBox hBox = new HBox(applyButton, deleteButton);

                { // 初始化块，用于设置按钮事件处理程序和样式等
                    hBox.setSpacing(10); // 设置按钮之间的间距
                    applyButton.setOnAction(event -> handleApplyAction(getIndex(), getTableView()));
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
        refreshTableView();
    }

    private void handleApplyAction(int index, TableView<GeneratorConfig> table) {
        GeneratorConfig selectedGenConfig = table.getItems().get(index);
        log.info("handleApplyAction() selectedGenConfig: {}", selectedGenConfig);
        if (ObjectUtil.isNotNull(selectedGenConfig)) {
            generatorControlsController.setGeneratorConfig(selectedGenConfig);
            controller.closeDialogStage();
        }
    }

    private void handleDeleteAction(int index, TableView<GeneratorConfig> table) {
        GeneratorConfig selectedGenConfig = table.getItems().get(index);
        log.info("handleDeleteAction selectedGenConfig: {}", selectedGenConfig);
        if (ObjectUtil.isNotNull(selectedGenConfig)) {
            generatorConfigInfoService.deleteGeneratorConfigInfoByCode(selectedGenConfig.getCode());
            refreshTableView();
        }
    }

    public void refreshTableView() {
        try {
            List<GeneratorConfigInfoVO> generatorConfigInfos = generatorConfigInfoService.findAll();
            List<GeneratorConfig> configs = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(generatorConfigInfos)) {
                configs = generatorConfigInfos.stream()
                        .map(a -> BeanUtil.copyProperties(a, GeneratorConfig.class))
                        .collect(Collectors.toList());
            }
            configTable.setItems(FXCollections.observableList(configs));
        } catch (Exception e) {
            log.error(String.format("配置刷新表格异常,【%s】", e.getMessage()), e);
            AlertUtils.showErrorAlert(e.getMessage());
        }
    }

}
