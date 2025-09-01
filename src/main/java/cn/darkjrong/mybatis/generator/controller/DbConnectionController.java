package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.common.enums.DbType;
import cn.darkjrong.mybatis.generator.common.pojo.dto.DbSourceDTO;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.darkjrong.mybatis.generator.common.utils.DataSourceUtils;
import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import cn.darkjrong.mybatis.generator.service.DbSourceService;
import cn.hutool.core.util.StrUtil;
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
    @Autowired
    private DbSourceService dbSourceService;

    private Boolean isUpdate = false;
    private Boolean isCopy = false;

    private Long primayKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        dbTypeChoice.setOnAction(event -> {
//            String selectedItem = dbTypeChoice.getSelectionModel().getSelectedItem();
//            log.info("The database type is currently selected 【{}】 ", selectedItem);
//            if (StrUtil.isNotBlank(selectedItem)) {
//                DbTypePort dbTypePort = DbTypePort.valueOf(selectedItem);
//                if (ObjectUtil.isNotNull(dbTypePort)) {
//                    portField.setText(Convert.toStr(dbTypePort.getValue()));
//                }
//            }
//        });
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

        if (StrUtil.isBlank(name)) {
            AlertUtils.showWarnAlert("连接名称不能为空");
            return;
        }
        if (StrUtil.isBlank(dbType)) {
            AlertUtils.showWarnAlert("数据库类型不能为空");
            return;
        }
        if (StrUtil.isBlank(host)) {
            AlertUtils.showWarnAlert("数据库IP地址不能为空");
            return;
        }
        if (StrUtil.isBlank(port)) {
            AlertUtils.showWarnAlert("数据库端口不能为空");
            return;
        }
        if (StrUtil.isBlank(userName)) {
            AlertUtils.showWarnAlert("用户名不能为空");
            return;
        }
        if (StrUtil.isBlank(password)) {
            AlertUtils.showWarnAlert("密码不能为空");
            return;
        }
        if (StrUtil.isBlank(database)) {
            AlertUtils.showWarnAlert("数据库不能为空");
            return;
        }
        if (StrUtil.equalsIgnoreCase(dbType, DbType.oracle.name())) {
            if (StrUtil.isBlank(schema)) {
                AlertUtils.showWarnAlert("Schema不能为空");
                return;
            }
        }
        if (StrUtil.equalsIgnoreCase(dbType, DbType.postgresql.name())) {
            if (StrUtil.isBlank(schema)) {
                schema = "public";
            }
        }
        if (StrUtil.equalsIgnoreCase(dbType, DbType.sqlserver.name())) {
            if (StrUtil.isBlank(schema)) {
                schema = "dbo";
            }
        }

        if (StrUtil.equalsIgnoreCase(dbType, DbType.oracle.name())) {
            if (StrUtil.isNotBlank(database)) {
                database = database.toUpperCase();
            }
            if (StrUtil.isNotBlank(schema)) {
                schema = schema.toUpperCase();
            }
            if (StrUtil.isNotBlank(userName)) {
                userName = userName.toUpperCase();
            }
        }

        DbSourceDTO dbSourceDTO = new DbSourceDTO();
        dbSourceDTO.setName(name);
        dbSourceDTO.setIp(host);
        dbSourceDTO.setPort(port);
        dbSourceDTO.setDbType(dbType);
        dbSourceDTO.setDatabaseName(database);
        dbSourceDTO.setSchemaName(schema);
        dbSourceDTO.setUsername(userName);
        dbSourceDTO.setPassword(password);

        try {
            if (isUpdate && !isCopy) {
                dbSourceDTO.setId(primayKey);
                dbSourceService.updateSource(dbSourceDTO);
            } else if (isCopy) {
                dbSourceService.saveSource(dbSourceDTO);
            } else {
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

    public void setConfig(DataSource config, Boolean isCopy) {
        isUpdate = true;
        this.isCopy = isCopy;
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
