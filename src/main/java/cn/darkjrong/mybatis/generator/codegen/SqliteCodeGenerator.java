package cn.darkjrong.mybatis.generator.codegen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqliteCodeGenerator extends BaseCodeGenerator {

    @Override
    public Boolean supports(DbType dbType) {
        return DbType.sqlite.equals(dbType);
    }

    @Override
    protected String getJdbcUrl(CodeGenParam param) {
        // jdbc:sqlite:pack.sqlite3
        return "jdbc:sqlite:" + param.getDatabase();
    }

}
