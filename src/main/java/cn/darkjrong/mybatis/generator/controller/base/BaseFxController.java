package cn.darkjrong.mybatis.generator.controller.base;

import cn.darkjrong.mybatis.generator.common.pojo.dto.ViewHolder;
import cn.darkjrong.mybatis.generator.common.utils.AlertUtils;
import cn.hutool.core.lang.Assert;
import com.github.spring.boot.javafx.text.LocaleText;
import com.github.spring.boot.javafx.view.ViewException;
import com.github.spring.boot.javafx.view.ViewLoader;
import com.github.spring.boot.javafx.view.ViewNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * Base FX 控制器
 *
 * @author Rong.Jia
 * @date 2025/01/21
 */
@Slf4j
@Data
public abstract class BaseFxController implements Initializable {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private LocaleText localeText;

    private Stage primaryStage;
    private Stage dialogStage;

    public BaseFxController showView(String title, String fxml) {
        try {
            ViewHolder viewHolder = loadView(fxml);
            Parent loginNode = (Parent)viewHolder.getNode();
            BaseFxController controller = viewHolder.getController();

            // fix bug: 嵌套弹出时会发生dialogStage被覆盖的情况
            Stage tmpDialogStage = new Stage();
            tmpDialogStage.setTitle(title);
            tmpDialogStage.initModality(Modality.APPLICATION_MODAL);
            tmpDialogStage.initOwner(getPrimaryStage());
            tmpDialogStage.setScene(new Scene(loginNode));
            tmpDialogStage.setMaximized(false);
            tmpDialogStage.setResizable(false);
            tmpDialogStage.show();
            controller.setDialogStage(tmpDialogStage);
            return controller;
        } catch (Exception e) {
            log.error(String.format("load FXMLPage 【%s】 failed, 【%s】", fxml, e.getMessage()), e);
            AlertUtils.showErrorAlert(e.getMessage());
            throw new ViewException(String.format("load FXMLPage 【%s】 failed, 【%s】", fxml, e.getMessage()));
        }
    }

    public void showDialogStage() {
        if (dialogStage != null) {
            dialogStage.show();
        }
    }

    public void closeDialogStage() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    public ViewHolder loadView(String view) {
        Assert.notBlank(view, "view cannot be empty");
        ClassPathResource componentResource = new ClassPathResource(ViewLoader.VIEW_DIRECTORY + File.separator + view);
        if (!componentResource.exists()) {
            log.error("View {} not found", view);
            throw new ViewNotFoundException(view);
        }
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(componentResource.getURL());
        } catch (IOException ex) {
            log.error(String.format("view 【%s】 instance exception, 【%s】", view, ex.getMessage()), ex);
            throw new ViewException(ex.getMessage(), ex);
        }
        loader.setControllerFactory(applicationContext::getBean);
        loader.setResources(localeText.getResourceBundle());

        try {
            Object load = loader.load();
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.setNode((Node) load);
            viewHolder.setController(loader.getController());
            return viewHolder;
        } catch (IOException e) {
            log.error(String.format("view 【%s】 load exception, 【%s】", view, e.getMessage()), e);
            throw new ViewException(e.getMessage(), e);
        }
    }







}
