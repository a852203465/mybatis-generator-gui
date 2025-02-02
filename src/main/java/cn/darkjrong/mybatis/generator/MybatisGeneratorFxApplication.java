package cn.darkjrong.mybatis.generator;

import cn.darkjrong.mybatis.generator.common.constants.Constant;
import cn.darkjrong.mybatis.generator.controller.MainController;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dustinredmond.fxtrayicon.FXTrayIcon;
import com.github.spring.boot.javafx.view.ViewLoader;
import com.github.spring.boot.javafx.view.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

@Slf4j
public class MybatisGeneratorFxApplication extends Application {

    private Long lastClickTime;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        setViewManager(stage);
        FXMLLoader mainFxml = getMainFxml();
        Parent parent = mainFxml.load();
        stage.setTitle(Constant.TITLE);
        stage.setResizable(true);
        stage.setScene(new Scene(parent));

        Image imageIcon = new Image(Constant.MYBATIS_LOGO);
        stage.getIcons().add(imageIcon);

        MainController controller = mainFxml.getController();
        controller.setPrimaryStage(stage);

        setCloseRequest(stage);
        maximizeListener(stage);
        minimizeListener(stage);
        setTrayIcon(stage, imageIcon);

        stage.show();
    }

    /**
     * 添加最小化监听
     * @param stage 主窗口
     */
    private void minimizeListener(Stage stage) {
        stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    log.info("================= minimizeListener,窗口已最小化 =================");
                } else {
                    log.info("================= minimizeListener,窗口已恢复 =================");
                }
            }
        });
    }

    /**
     * 添加最大化监听
     * @param stage 主窗口
     */
    private void maximizeListener(Stage stage) {
        stage.maximizedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    log.info("================= maximizeListener,窗口已最大化 =================");
                } else {
                    log.info("================= maximizeListener,窗口已恢复 =================");
                }
            }
        });
    }

    /**
     * 获取主窗口
     *
     * @return {@link FXMLLoader }
     * @throws Exception 异常
     */
    private FXMLLoader getMainFxml() throws Exception {
        ClassPathResource componentResource = new ClassPathResource(ViewLoader.VIEW_DIRECTORY + File.separator + "main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(componentResource.getURL());
        fxmlLoader.setControllerFactory(SpringUtil.getApplicationContext()::getBean);
        return fxmlLoader;
    }

    /**
     * 设置视图管理器
     *
     * @param stage 主窗口
     */
    private void setViewManager(Stage stage) {
        ViewManager viewManager = SpringUtil.getBean(ViewManager.class);
        viewManager.registerPrimaryStage(stage);
    }

    /**
     * 设置关闭事件
     *
     * @param stage 主窗口
     */
    private void setCloseRequest(Stage stage) {
        stage.setOnCloseRequest(event -> {
            log.info("================= 关闭窗口 =================");
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * 设置托盘图标
     *
     * @param stage     主窗口
     * @param imageIcon 图像图标
     */
    private void setTrayIcon(Stage stage, Image imageIcon) {
        FXTrayIcon trayIcon = new FXTrayIcon.Builder(stage, imageIcon)
                .onAction(event -> {
                    if (ObjectUtil.isNull(lastClickTime)) {
                        lastClickTime = DateUtil.current();
                    } else {
                        Long now = DateUtil.current();
                        boolean isClick = (now - lastClickTime) < 500;
                        if (isClick) {
                            log.info("================= 打开主界面 =================");
                            Platform.runLater(() -> {
                                if (stage.isIconified()) {
                                    stage.setIconified(Boolean.FALSE);
                                }
                                // 请求焦点到主窗口，这将使其置顶
                                stage.requestFocus();
                                // 将窗口置于最前端
                                stage.toFront();

                            });
                        }
                        lastClickTime = null;
                    }
                })
//                .animate(fileList, 75)
//                .addExitMenuItem("Exit", e -> Main.stopRunning())
                .show()
                .build();
    }


}