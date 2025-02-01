package cn.darkjrong.mybatis.generator.common.domain;

import lombok.Builder;

/**
 * JDBC持有人
 *
 * @author Rong.Jia
 * @date 2024/02/16
 */
@Builder
public class JdbcHolder {
    public String driver;
    public String url;
}
