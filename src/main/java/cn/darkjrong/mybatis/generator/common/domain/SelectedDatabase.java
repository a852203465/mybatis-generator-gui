package cn.darkjrong.mybatis.generator.common.domain;

import lombok.Data;

import java.util.Set;

/**
 * 所选数据库
 *
 * @author Rong.Jia
 * @date 2025/01/26
 */
@Data
public class SelectedDatabase {

    private DataSource dataSource;

    private Set<String> tableNames;


}
