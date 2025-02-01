package cn.darkjrong.mybatis.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * fxml页面
 *
 * @author Rong.Jia
 * @date 2025/01/21
 */
@Getter
@AllArgsConstructor
public enum FXMLPage {

    NEW_CONNECTION("newConnection.fxml"),
    SELECT_TABLE_COLUMN("selectTableColumn.fxml"),
    TABLE_COLUMN_CONFIG("tableColumnConfigs.fxml"),
    GENERATOR_CONFIG("generatorConfig.fxml"),


    ;

    private final String fxml;


}
