package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.common.enums.DbTypePort;
import cn.darkjrong.mybatis.generator.common.pojo.dto.DbSourceDTO;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.darkjrong.mybatis.generator.common.utils.DataSourceUtils;
import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import cn.darkjrong.mybatis.generator.service.DbSourceService;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * DB 连接控制器
 *
 * @author Rong.Jia
 * @date 2025/02/03
 */
@Slf4j
@ViewController
public class DbConnectionController extends BaseFxController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField databaseField;
    @FXML
    private TextField schemaField;
    @FXML
    private ChoiceBox<String> dbTypeChoice;
    @Autowired
    private TabPaneController tabPaneController;
    @Autowired
    private DatabaseListController databaseListController;

    private boolean isUpdate = false;
    private Long primayKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbTypeChoice.setOnAction(event -> {
            String selectedItem = dbTypeChoice.getSelectionModel().getSelectedItem();
            log.info("The database type is currently selected 【{}】 ", selectedItem);
            if (StrUtil.isNotBlank(selectedItem)) {
                DbTypePort dbTypePort = DbTypePort.valueOf(selectedItem);
                if (ObjectUtil.isNotNull(dbTypePort)) {
                    portField.setText(Convert.toStr(dbTypePort.getValue()));
                }
            }
        });
    }

    final void saveConnection() {
        String name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String dbType = dbTypeChoice.getValue();
        String database = databaseField.getText();
        String schema = schemaField.getText();
//        if (StringUtils.isAnyEmpty(name, host, port, userName, dbType, database)) {
//            AlertUtils.showWarnAlert("密码以外其他字段必填");
//        }

        DbSourceDTO dbSourceDTO = new DbSourceDTO();
        dbSourceDTO.setName(name);
        dbSourceDTO.setIp(host);
        dbSourceDTO.setPort(port);
        dbSourceDTO.setDbType(dbType);
        dbSourceDTO.setDatabaseName(database);
        dbSourceDTO.setSchemaName(schema);
        dbSourceDTO.setUsername(userName);
        dbSourceDTO.setPassword(password);
        DbSourceService dbSourceService = SpringUtil.getBean(DbSourceService.class);

        try {
            if (isUpdate) {
                dbSourceDTO.setId(primayKey);
                dbSourceService.updateSource(dbSourceDTO);
            }else {
                dbSourceService.saveSource(dbSourceDTO);
            }
            tabPaneController.getDialogStage().close();
            databaseListController.loadLeftDBTree();
        } catch (Exception e) {
            log.error(String.format("数据源【%s】保存异常, 【%s】",
                    JSON.toJSONString(dbSourceDTO), e.getMessage()), e);
            AlertUtils.showErrorAlert(e.getMessage());
        }
    }

    public DataSource extractConfigForUI() {
        DataSource dataSource = DataSourceUtils.getDataSource(primayKey, nameField, hostField, portField, userNameField,
                passwordField, databaseField, schemaField, dbTypeChoice);
//        if (StringUtils.isAnyEmpty(name, host, port, userName, dbType, schema)) {
//            AlertUtils.showWarnAlert("密码以外其他字段必填");
//            return null;
//        }
        return dataSource;
    }

    public void setConfig(DataSource config) {
        isUpdate = true;
        primayKey = config.getSourceId(); // save id for update config
        nameField.setText(config.getName());
        hostField.setText(config.getIp());
        portField.setText(config.getPort());
        userNameField.setText(config.getUsername());
        passwordField.setText(config.getPassword());
        dbTypeChoice.setValue(config.getDbType().name());
        databaseField.setText(config.getDatabaseName());
        schemaField.setText(config.getSchemaName());
    }

}
