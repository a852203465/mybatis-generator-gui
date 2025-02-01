package cn.darkjrong.mybatis.generator.controller;

import cn.darkjrong.mybatis.generator.common.enums.FXMLPage;
import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 主控制器
 *
 * @author Rong.Jia
 * @date 2025/01/21
 */
@Slf4j
@ViewController
public class MainController extends BaseFxController {

    @FXML
    private Label connectionLabel;

    @FXML
    private Label configLabel;

    @FXML
    public AnchorPane databaseList;

    @FXML
    public AnchorPane generatorControls;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView dbImage = new ImageView("static/icons/computer.png");
        dbImage.setFitHeight(40);
        dbImage.setFitWidth(40);
        connectionLabel.setGraphic(dbImage);
        connectionLabel.setOnMouseClicked(event -> {
            showView("新建数据库连接", FXMLPage.NEW_CONNECTION.getFxml());
        });
        ImageView configImage = new ImageView("static/icons/config-list.png");
        configImage.setFitHeight(40);
        configImage.setFitWidth(40);
        configLabel.setGraphic(configImage);
        configLabel.setOnMouseClicked(event -> {
            showView("配置", FXMLPage.GENERATOR_CONFIG.getFxml());
        });
	}




}
