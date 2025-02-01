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
        ViewManager viewManager = SpringUtil.getBean(ViewManager.class);
        viewManager.registerPrimaryStage(stage);

        stage.setOnCloseRequest(event -> {
            log.info("============ 关闭窗口 =================");
            Platform.exit();
            System.exit(0);
        });

        ClassPathResource componentResource = new ClassPathResource(ViewLoader.VIEW_DIRECTORY + File.separator + "main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(componentResource.getURL());
        fxmlLoader.setControllerFactory(SpringUtil.getApplicationContext()::getBean);

        Parent parent = fxmlLoader.load();
        stage.setTitle(Constant.TITLE);
        stage.setResizable(true);
        stage.setScene(new Scene(parent));
        Image imageIcon = new Image(Constant.MYBATIS_LOGO);
        stage.getIcons().add(imageIcon);
        stage.show();

        MainController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);

        FXTrayIcon trayIcon = new FXTrayIcon.Builder(stage, imageIcon)
                .onAction(event -> {
                    if (ObjectUtil.isNull(lastClickTime)) {
                        lastClickTime = DateUtil.current();
                    } else {
                        Long now = DateUtil.current();
                        boolean isClick = (now - lastClickTime) < 1000;
                        if (isClick) {
                            log.info("========== 打开主界面 ================");
                            Platform.runLater(() -> {
                                stage.requestFocus(); // 请求焦点到主窗口，这将使其置顶。
                                stage.toFront(); // 将窗口置于最前端。
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