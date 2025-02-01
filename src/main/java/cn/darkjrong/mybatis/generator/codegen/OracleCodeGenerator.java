package cn.darkjrong.mybatis.generator.codegen;

import org.springframework.stereotype.Component;

@Component
public class OracleCodeGenerator extends BaseCodeGenerator {

    @Override
    public Boolean supports(DbType dbType) {
        return DbType.oracle.equals(dbType);
    }

    @Override
    protected String getJdbcUrl(CodeGenParam param) {
        // jdbc:oracle:thin:@localhost:1521/ORCL
        return "jdbc:oracle:thin:@" + param.getHost() + ":"+ param.getPort() + "/" + param.getDatabase();
    }
}
