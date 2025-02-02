package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.common.enums.FXMLPage;
import cn.darkjrong.mybatis.generator.common.pojo.vo.DbSourceVO;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.darkjrong.mybatis.generator.common.utils.DataSourceUtils;
import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import cn.darkjrong.mybatis.generator.factory.meta.DatabaseMetaFactory;
import cn.darkjrong.mybatis.generator.service.DbSourceService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * 数据库列表控制器
 *
 * @author Rong.Jia
 * @date 2025/01/24
 */
@Slf4j
@ViewController
public class DatabaseListController extends BaseFxController {

    @FXML
    private TreeView<String> leftDBTree;

    @FXML
    public TextField filterTreeBox;

    @Autowired
    private GeneratorControlsController generatorControlsController;

    @Autowired
    private DatabaseMetaFactory databaseMetaFactory;

    @Autowired
    private DbSourceService dbSourceService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftDBTree.setShowRoot(false);
        leftDBTree.setRoot(new TreeItem<>());
        filterTreeBox.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                ObservableList<TreeItem<String>> schemas = leftDBTree.getRoot().getChildren();
                schemas.filtered(TreeItem::isExpanded).forEach(this::displayTables);
                ev.consume();
            }
        });
        Callback<TreeView<String>, TreeCell<String>> defaultCellFactory = TextFieldTreeCell.forTreeView();
        leftDBTree.setCellFactory((TreeView<String> tv) -> {
            TreeCell<String> cell = defaultCellFactory.call(tv);

            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                int level = leftDBTree.getTreeItemLevel(cell.getTreeItem());
                TreeCell<String> treeCell = (TreeCell<String>) event.getSource();
                TreeItem<String> treeItem = treeCell.getTreeItem();
                if (level == 1) {
                    final ContextMenu contextMenu = new ContextMenu();
                    MenuItem item1 = new MenuItem("关闭连接");
                    item1.setOnAction(event1 -> treeItem.getChildren().clear());
                    MenuItem item2 = new MenuItem("编辑连接");
                    item2.setOnAction(event1 -> {
                        DataSource selectedConfig = (DataSource) treeItem.getGraphic().getUserData();
                        TabPaneController controller = (TabPaneController) showView("编辑数据库连接", FXMLPage.NEW_CONNECTION.getFxml());
                        controller.setConfig(selectedConfig);
                        controller.showDialogStage();
                    });
                    MenuItem item3 = new MenuItem("删除连接");
                    item3.setOnAction(event1 -> {
                        DataSource dataSource = (DataSource) treeItem.getGraphic().getUserData();
                        try {
                            SpringUtil.getBean(DbSourceService.class).delete(dataSource.getSourceId());
                            this.loadLeftDBTree();
                        } catch (Exception e) {
                            AlertUtils.showErrorAlert("Delete connection failed! Reason: " + e.getMessage());
                        }
                    });
                    contextMenu.getItems().addAll(item1, item2, item3);
                    cell.setContextMenu(contextMenu);
                }
                if (event.getClickCount() == 2 && level == 1 && ObjectUtil.isNotNull(treeItem)) {
                    treeItem.setExpanded(true);
                    displayTables(treeItem);
                }

                // // left DB tree level3
                if (event.getClickCount() == 1 && level == 2 && ObjectUtil.isNotNull(treeItem)) {
                    String tableName = treeCell.getTreeItem().getValue();
                    DataSource selectedDatabaseConfig = (DataSource) treeItem.getParent().getGraphic().getUserData();
                    generatorControlsController.setField(selectedDatabaseConfig, tableName);
                }
            });
            return cell;
        });
        loadLeftDBTree();
    }

    private void displayTables(TreeItem<String> treeItem) {
        if (treeItem == null) {
            return;
        }
        if (!treeItem.isExpanded()) {
            return;
        }
        DataSource dataSource = (DataSource) treeItem.getGraphic().getUserData();
        try {
            String filter = filterTreeBox.getText();
            log.info("filter: {}", filter);
            List<String> tables = databaseMetaFactory.getTables(dataSource);
            if (StrUtil.isNotBlank(filter)) {
                tables = tables.stream()
                        .filter(a -> StrUtil.containsIgnoreCase(a, filter))
                        .collect(Collectors.toList());
            }
            if (CollUtil.isNotEmpty(tables)) {
                ObservableList<TreeItem<String>> children = treeItem.getChildren();
                children.clear();
                for (String tableName : tables) {
                    TreeItem<String> newTreeItem = new TreeItem<>();
                    ImageView imageView = new ImageView("static/icons/table.png");
                    imageView.setFitHeight(16);
                    imageView.setFitWidth(16);
                    newTreeItem.setGraphic(imageView);
                    newTreeItem.setValue(tableName);
                    children.add(newTreeItem);
                }
            } else if (StrUtil.isNotBlank(filter)) {
                treeItem.getChildren().clear();
            }
            if (StrUtil.isNotBlank(filter)) {
                ImageView imageView = new ImageView("static/icons/filter.png");
                imageView.setFitHeight(16);
                imageView.setFitWidth(16);
                imageView.setUserData(treeItem.getGraphic().getUserData());
                treeItem.setGraphic(imageView);
            } else {
                ImageView dbImage = new ImageView("static/icons/computer.png");
                dbImage.setFitHeight(16);
                dbImage.setFitWidth(16);
                dbImage.setUserData(treeItem.getGraphic().getUserData());
                treeItem.setGraphic(dbImage);
            }
        } catch (Exception e) {
            log.error(String.format("加载表树异常, 【%s】", e.getMessage()), e);
            AlertUtils.showErrorAlert(e.getMessage());
        }
    }

    public void loadLeftDBTree() {
        TreeItem<String> rootTreeItem = leftDBTree.getRoot();
        rootTreeItem.getChildren().clear();
        try {
            List<DbSourceVO> teSources = dbSourceService.findAll();
            for (DbSourceVO dbSourceVO : teSources) {
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(dbSourceVO.getName());
                ImageView dbImage = new ImageView("static/icons/computer.png");
                dbImage.setFitHeight(16);
                dbImage.setFitWidth(16);
                dbImage.setUserData(DataSourceUtils.getDataSource(dbSourceVO));
                treeItem.setGraphic(dbImage);
                rootTreeItem.getChildren().add(treeItem);
            }
        } catch (Exception e) {
            log.error("connect db failed, reason", e);
            AlertUtils.showErrorAlert(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
    }


}
