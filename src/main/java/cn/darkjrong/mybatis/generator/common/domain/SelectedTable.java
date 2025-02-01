package cn.darkjrong.mybatis.generator.common.domain;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.Objects;

/**
 * 所选的表信息
 *
 * @author Rong.Jia
 * @date 2025/01/27
 */
public class SelectedTable {

    private StringProperty uniqueCode = new SimpleStringProperty("");

    private StringProperty tableName = new SimpleStringProperty();

    private StringProperty tablePrefix = new SimpleStringProperty();

    private StringProperty className = new SimpleStringProperty();

    private StringProperty pkName = new SimpleStringProperty();

    private ObjectProperty<DataSource> selectedDataSource = new SimpleObjectProperty<>();

    private ObjectProperty<List<ColumnOverride>> columnOverride = new SimpleObjectProperty<>();

    private ObjectProperty<List<IgnoredColumn>> ignoredColumn = new SimpleObjectProperty<>();

    private ObjectProperty<NamingStrategy> columnNameStrategy = new SimpleObjectProperty<>();

    public void setColumnNameStrategy(NamingStrategy columnNameStrategy) {
        this.columnNameStrategy.set(columnNameStrategy);
    }

    public NamingStrategy getColumnNameStrategy() {
        return columnNameStrategy.get();
    }

    public ObjectProperty<NamingStrategy> columnNameStrategyProperty() {
        return columnNameStrategy;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode.set(uniqueCode);
    }

    public String getUniqueCode() {
        return uniqueCode.get();
    }

    public StringProperty uniqueCodeProperty() {
        return uniqueCode;
    }

    public ObjectProperty<List<IgnoredColumn>> ignoredColumnProperty() {
        return ignoredColumn;
    }

    public void setIgnoredColumn(List<IgnoredColumn> ignoredColumn) {
        this.ignoredColumn.set(ignoredColumn);
    }

    public List<IgnoredColumn> getIgnoredColumn() {
        return ignoredColumn.get();
    }

    public StringProperty pkNameProperty() {
        return pkName;
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public StringProperty tablePrefixProperty() {
        return tablePrefix;
    }

    public ObjectProperty<DataSource> selectedDataSourceProperty() {
        return selectedDataSource;
    }

    public StringProperty classNameProperty() {
        return className;
    }

    public void setColumnOverride(List<ColumnOverride> columnOverride) {
        this.columnOverride.set(columnOverride);
    }

    public List<ColumnOverride> getColumnOverride() {
        return columnOverride.get();
    }

    public ObjectProperty<List<ColumnOverride>> columnOverrideProperty() {
        return columnOverride;
    }

    public String getTableName() {
        return tableName.get();
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    public String getTablePrefix() {
        return tablePrefix.get();
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix.set(tablePrefix);
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public String getClassName() {
        return className.get();
    }

    public String getPkName() {
        return pkName.get();
    }

    public void setPkName(String pkName) {
        this.pkName.set(pkName);
    }

    public void setSelectedDataSource(DataSource selectedDataSource) {
        this.selectedDataSource.set(selectedDataSource);
    }

    public DataSource getSelectedDataSource() {
        return selectedDataSource.get();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SelectedTable that = (SelectedTable) o;
        return Objects.equals(tableName, that.tableName)
                && Objects.equals(tablePrefix, that.tablePrefix)
                && Objects.equals(className, that.className)
                && Objects.equals(pkName, that.pkName)
                && Objects.equals(selectedDataSource.get(), that.selectedDataSource.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, tablePrefix, className, pkName, selectedDataSource.get());
    }

    @Override
    public String toString() {
        return "SelectedTable{" +
                "tableName=" + tableName +
                ", tablePrefix=" + tablePrefix +
                ", className=" + className +
                ", pkName=" + pkName +
                ", selectedDataSource=" + selectedDataSource.get() +
                '}';
    }
}
