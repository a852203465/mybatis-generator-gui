package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import cn.darkjrong.mybatis.generator.common.domain.DataSource;
import cn.darkjrong.mybatis.generator.factory.meta.DatabaseMetaFactory;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 选项卡窗格控制器
 *
 * @author Rong.Jia
 * @date 2025/01/21
 */
@Slf4j
@ViewController
public class TabPaneController extends BaseFxController {

    @FXML
    private TabPane tabPane;

    @Autowired
    private DbConnectionController tabControlAController;

//    @FXML
//    private OverSshController tabControlBController;

    private boolean isOverssh;

    @Autowired
    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.setPrefHeight(((AnchorPane) tabPane.getSelectionModel().getSelectedItem().getContent()).getPrefHeight());
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            isOverssh = observable.getValue().getText().equals("SSH");
            tabPane.prefHeightProperty().bind(((AnchorPane) tabPane.getSelectionModel().getSelectedItem().getContent()).prefHeightProperty());
            getDialogStage().close();
            getDialogStage().show();
        });
    }

    public void setConfig(DataSource selectedConfig) {
        tabControlAController.setConfig(selectedConfig);
//        tabControlBController.setDbConnectionConfig(selectedConfig);
//        if (ObjectUtil.isAllNotEmpty(
//                selectedConfig.getSshHost(),
//                selectedConfig.getSshPassword(),
//                selectedConfig.getSshPort(),
//                selectedConfig.getSshUser(),
//                selectedConfig.getLport())) {
//            log.info("Found SSH based Config");
//            tabPane.getSelectionModel().selectLast();
//        }
    }

    private DataSource extractConfigForUI() {
//        if (isOverssh) {
////            return tabControlBController.extractConfigFromUi();
//        } else {
//            return tabControlAController.extractConfigForUI();
//        }
        return tabControlAController.extractConfigForUI();
    }

    @FXML
    void saveConnection() {
        if (isOverssh) {
//            tabControlBController.saveConfig();
        } else {
            tabControlAController.saveConnection();
        }
    }


    @FXML
    void testConnection() {
        DataSource dataSource = extractConfigForUI();
        if (ObjectUtil.isNull(dataSource)) {
            AlertUtils.showErrorAlert("数据源信息为空,请输入");
            return;
        }
        DatabaseMetaFactory databaseMetaFactory = SpringUtil.getBean(DatabaseMetaFactory.class);
        Boolean testConnection = databaseMetaFactory.testConnection(dataSource);
        if (testConnection) {
            AlertUtils.showInfoAlert("连接成功");
        } else {
            AlertUtils.showErrorAlert("连接失败");
        }
    }
//    void testConnection() {
//        DataSource config = extractConfigForUI();
//        if (config == null) {
//            return;
//        }
//        if (StringUtils.isAnyEmpty(config.getName(),
//                config.getHost(),
//                config.getPort(),
//                config.getUsername(),
//                config.getEncoding(),
//                config.getDbType(),
//                config.getSchema())) {
//            AlertUtils.showWarnAlert("密码以外其他字段必填");
//            return;
//        }
//        Session sshSession = DbUtil.getSSHSession(config);
//        if (isOverssh && sshSession != null) {
////            PictureProcessStateController pictureProcessState = new PictureProcessStateController();
////            pictureProcessState.setDialogStage(getDialogStage());
////            pictureProcessState.startPlay();
//            //如果不用异步，则视图会等方法返回才会显示
//            Task task = new Task<Void>() {
//                @Override
//                protected Void call() throws Exception {
//                    DbUtil.engagePortForwarding(sshSession, config);
////                    DbUtil.getConnection(config);
//                    return null;
//                }
//            };
//            task.setOnFailed(event -> {
//                Throwable e = task.getException();
//                log.error("task Failed", e);
//                if (e instanceof RuntimeException) {
//                    if (e.getMessage().equals("Address already in use: JVM_Bind")) {
////                        tabControlBController.setLPortLabelText(config.getLport() + "已经被占用，请换其他端口");
//                    }
//                    //端口转发一定不成功，导致数据库连接不上
////                    pictureProcessState.playFailState("连接失败:" + e.getMessage(), true);
//                    return;
//                }
//
//                if (e.getCause() instanceof EOFException) {
////                    pictureProcessState.playFailState("连接失败, 请检查数据库的主机名，并且检查端口和目标端口是否一致", true);
//                    //端口转发已经成功，但是数据库连接不上，故需要释放连接
//                    DbUtil.shutdownPortForwarding(sshSession);
//                    return;
//                }
////                pictureProcessState.playFailState("连接失败:" + e.getMessage(), true);
//                //可能是端口转发已经成功，但是数据库连接不上，故需要释放连接
//                DbUtil.shutdownPortForwarding(sshSession);
//            });
//            task.setOnSucceeded(event -> {
//                try {
////                    pictureProcessState.playSuccessState("连接成功", true);
//                    DbUtil.shutdownPortForwarding(sshSession);
////                    tabControlBController.recoverNotice();
//                } catch (Exception e) {
//                    log.error("", e);
//                }
//            });
//            new Thread(task).start();
//        } else {
//            try {
////                DbUtil.getConnection(config);
//                AlertUtils.showInfoAlert("连接成功");
//            } catch (RuntimeException e) {
//                log.error("", e);
//                AlertUtils.showWarnAlert("连接失败, " + e.getMessage());
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//                AlertUtils.showWarnAlert("连接失败");
//            }
//        }
//    }

    @FXML
    void cancel() {
        getDialogStage().close();
    }
}
