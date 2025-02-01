package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import cn.darkjrong.mybatis.generator.common.domain.CustomTableColumn;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.hutool.core.collection.CollectionUtil;
import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定制列配置UI Controller
 *
 * @author Rong.Jia
 * @date 2025/01/22
 */
@Slf4j
@ViewController
public class TableColumnConfigsController extends BaseFxController {

    // pattern regex and split prefix: (?<=aggregate_|f_)[^"]+  f_ or d_ prefix
    private static final String COL_NAME_PREFIX_REGEX = "(?<=%s)[^\"]+";
    private static final String OR_REGEX = "|";

    @FXML
    private Label currentTableNameLabel;
    @FXML
    private TextField columnNamePrefixTextLabel;

    private TableView<CustomTableColumn> columnListView;
    private String tableName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // do nothing
    }

    @FXML
    public void cancel() {
        this.closeDialogStage();
    }

    @FXML
    public void confirm() {
        try {
            // 1. generator bean propert name
            this.genProertyNameByColumnNamePrefix();

            // close window
            this.closeDialogStage();
        } catch (Exception e) {
            log.error("confirm throw exception.", e);
            AlertUtils.showErrorAlert(e.getMessage());
        }
    }

    public void setColumnListView(TableView<CustomTableColumn> columnListView) {
        this.columnListView = columnListView;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        currentTableNameLabel.setText(tableName);
    }

    private void genProertyNameByColumnNamePrefix() {
        String columnNamePrefix = this.columnNamePrefixTextLabel.getText();
        if (StringUtils.isNotBlank(columnNamePrefix)) {
            if (StringUtils.endsWith(columnNamePrefix.trim(), OR_REGEX)) {
                columnNamePrefix = StringUtils.removeEnd(columnNamePrefix.trim(), OR_REGEX);
            }

            String regex = String.format(COL_NAME_PREFIX_REGEX, columnNamePrefix);
            log.info("table:{}, column_name_prefix:{}, regex:{}", this.tableName, columnNamePrefix, regex);

            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

            ObservableList<CustomTableColumn> items = columnListView.getItems();
            if (CollectionUtil.isNotEmpty(items)) {
                items.stream().forEach(item -> {
                    String columnName = item.getColumnName();
                    Matcher matcher = pattern.matcher(columnName);
                    if (matcher.find()) {
                        // use first match result
                        String regexColumnName = matcher.group();
                        if (StringUtils.isNotBlank(regexColumnName)) {
//                            String propertyName = JavaBeansUtil.getCamelCaseString(regexColumnName, false);
                            log.debug("table:{} column_name:{} regex_column_name:{}", tableName, columnName, regexColumnName);

//                            if (StringUtils.isNotBlank(propertyName)) item.setPropertyName(propertyName);
                        } else {
                            log.warn("table:{} column_name:{} regex_column_name is blank", tableName, columnName);
                        }
                    } else {
                        // if not match, set property name is null
//                        item.setPropertyName(null);
                    }
                });
            }
        }
    }

}
