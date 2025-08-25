package cn.darkjrong.mybatis.generator.common.config;

import cn.hutool.core.io.FileUtil;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.JDBC;

import javax.sql.DataSource;

/**
 * SQLite配置
 *
 * @author Rong.Jia
 * @date 2025/01/21
 */
@Configuration
public class SqliteConfig {

    private static final String DB_DIR = "C:/mggui/";
    // key: 密码,cipher:算法, legacy:版本
    private static final String DB_FILE = "mggui.db";

    @Bean
    public DataSource sqliteDataSource() {
        String dbPath = DB_FILE;
        if (FileUtil.isWindows()) {
            FileUtil.mkdir(DB_DIR);
            //  + "?key=" + NetUtil.getLocalMacAddress() + "&cipher=sqlcipher&legacy=4"
            dbPath = DB_DIR + DB_FILE;
        }
        return DataSourceBuilder.create()
                .url("jdbc:sqlite:" + dbPath)
                .driverClassName(JDBC.class.getName())
                .build();
    }



}
