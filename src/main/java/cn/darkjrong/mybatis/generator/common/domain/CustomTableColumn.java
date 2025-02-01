package cn.darkjrong.mybatis.generator.common.domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 定制列信息
 *
 * @author Rong.Jia
 * @date 2025/01/24
 */
public class CustomTableColumn {

    private BooleanProperty checked = new SimpleBooleanProperty(true); // Default set to true

    private StringProperty columnName = new SimpleStringProperty();

    private StringProperty javaType = new SimpleStringProperty();

    private StringProperty jdbcType = new SimpleStringProperty();

    private StringProperty propertyName = new SimpleStringProperty();

//    private StringProperty typeHandle = new SimpleStringProperty();

    private StringProperty columnComment = new SimpleStringProperty();

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public StringProperty columnNameProperty() {
        return columnName;
    }

    public StringProperty javaTypeProperty() {
        return javaType;
    }

    public StringProperty jdbcTypeProperty() {
        return jdbcType;
    }

    public StringProperty propertyNameProperty() {
        return propertyName;
    }

    public StringProperty columnCommentProperty() {
        return columnComment;
    }

    public String getColumnName() {
        return columnName.get();
    }

    public void setColumnName(String columnName) {
        this.columnName.set(columnName);
    }

    public String getJdbcType() {
        return jdbcType.get();
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType.set(jdbcType);
    }

    public Boolean getChecked() {
        return this.checked.get();
    }

    public void setChecked(Boolean checked) {
        this.checked.set(checked);
    }

    public void setColumnComment(String columnComment) {
        this.columnComment.set(columnComment);
    }

    public String getColumnComment() {
        return columnComment.get();
    }

    public String getJavaType() {
        return javaType.get();
    }

    public void setJavaType(String javaType) {
        this.javaType.set(javaType);
    }

    public String getPropertyName() {
        return propertyName.get();
    }

    public void setPropertyName(String propertyName) {
        this.propertyName.set(propertyName);
    }

}
