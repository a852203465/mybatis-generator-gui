package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.common.domain.ColumnOverride;
import cn.darkjrong.mybatis.generator.common.domain.CustomTableColumn;
import cn.darkjrong.mybatis.generator.common.domain.IgnoredColumn;
import cn.darkjrong.mybatis.generator.common.domain.SelectedTable;
import cn.darkjrong.mybatis.generator.common.enums.FXMLPage;
import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import cn.darkjrong.mybatis.generator.factory.converts.names.NameConvert;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 选择表列控制器
 *
 * @author Rong.Jia
 * @date 2025/01/22
 */
@Slf4j
@ViewController
public class SelectTableColumnController extends BaseFxController {

    @FXML
    private TableView<CustomTableColumn> columnListView;
    @FXML
    private TableColumn<CustomTableColumn, Boolean> checkedColumn;
    @FXML
    private TableColumn<CustomTableColumn, String> columnNameColumn;
    @FXML
    private TableColumn<CustomTableColumn, String> jdbcTypeColumn;
    @FXML
    private TableColumn<CustomTableColumn, String> javaTypeColumn;
    @FXML
    private TableColumn<CustomTableColumn, String> propertyNameColumn;
    @FXML
    private TableColumn<CustomTableColumn, String> typeHandlerColumn;

    @FXML
    private TableColumn<CustomTableColumn, String> columnComment;

    @FXML
    private ToggleGroup radioButtonGroup;

    @FXML
    private RadioButton humpRadioButton;

    @FXML
    private RadioButton invariabilityRadioButton;

    @Autowired
    private NameConvert nameConvert;

    @Setter
    @Getter
    private SelectedTable selectedTable;

    @Autowired
    private GeneratorControlsController generatorControlsController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // cellvaluefactory
        checkedColumn.setCellValueFactory(new PropertyValueFactory<>("checked"));
        columnNameColumn.setCellValueFactory(new PropertyValueFactory<>("columnName"));
        jdbcTypeColumn.setCellValueFactory(new PropertyValueFactory<>("jdbcType"));
        javaTypeColumn.setCellValueFactory(new PropertyValueFactory<>("javaType"));
        columnComment.setCellValueFactory(new PropertyValueFactory<>("columnComment"));
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<>("propertyName"));

//        typeHandlerColumn.setCellValueFactory(new PropertyValueFactory<>("typeHandler"));
        // Cell Factory that customize how the cell should render
        checkedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkedColumn));

        jdbcTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        columnComment.setCellFactory(TextFieldTableCell.forTableColumn());

        // handle commit event to save the user input data
        jdbcTypeColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setJdbcType(event.getNewValue());
        });
        javaTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // handle commit event to save the user input data
        javaTypeColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setJavaType(event.getNewValue());
        });
        propertyNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        propertyNameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPropertyName(event.getNewValue());
        });
//        typeHandlerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        typeHandlerColumn.setOnEditCommit(event -> {
//            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTypeHandle(event.getNewValue());
//        });

        humpRadioButton.setUserData(true);
        invariabilityRadioButton.setUserData(false);

        radioButtonGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                Boolean isHump = (Boolean) selectedRadioButton.getUserData();
                NamingStrategy namingStrategy = isHump ? NamingStrategy.underline_to_camel : NamingStrategy.no_change;
                for (CustomTableColumn item : columnListView.getItems()) {
                    item.setPropertyName(nameConvert.propertyNameConvert(item.getColumnName(), namingStrategy));
                }
            }
        });
    }

    @FXML
    public void ok() {
        ObservableList<CustomTableColumn> items = columnListView.getItems();
        if (CollectionUtil.isNotEmpty(items)) {
            List<IgnoredColumn> ignoredColumns = new ArrayList<>();
            List<ColumnOverride> columnOverrides = new ArrayList<>();
            items.forEach(item -> {
                if (!item.getChecked()) {
                    IgnoredColumn ignoredColumn = new IgnoredColumn(item.getColumnName());
                    ignoredColumns.add(ignoredColumn);
                } else {
                    ColumnOverride columnOverride = new ColumnOverride(item.getColumnName());
                    columnOverride.setJdbcType(item.getJdbcType());
                    columnOverride.setJavaProperty(item.getPropertyName());
                    columnOverride.setJavaType(item.getJavaType());
                    columnOverrides.add(columnOverride);
                }
            });
            RadioButton selectedRadioButton = (RadioButton) radioButtonGroup.getSelectedToggle();
            Boolean isHump = (Boolean) selectedRadioButton.getUserData();
            NamingStrategy namingStrategy = isHump ? NamingStrategy.underline_to_camel : NamingStrategy.no_change;
            generatorControlsController.setSelectTableColumn(selectedTable, columnOverrides, ignoredColumns, namingStrategy);
        }
        getDialogStage().close();
    }

    @FXML
    public void cancel() {
        getDialogStage().close();
    }

    @FXML
    public void configAction() {
        TableColumnConfigsController controller
                = (TableColumnConfigsController) showView("定制列配置", FXMLPage.TABLE_COLUMN_CONFIG.getFxml());
        controller.setColumnListView(this.columnListView);
        controller.showDialogStage();
    }

    public void setColumnList(ObservableList<CustomTableColumn> columns) {
        columnListView.setItems(columns);
    }



}
