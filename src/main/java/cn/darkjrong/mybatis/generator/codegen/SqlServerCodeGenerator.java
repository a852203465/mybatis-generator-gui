package cn.darkjrong.mybatis.generator.codegen;

import org.springframework.stereotype.Component;

@Component
public class SqlServerCodeGenerator extends BaseCodeGenerator {

    @Override
    public Boolean supports(DbType dbType) {
        return DbType.sqlserver.equals(dbType);
    }

    @Override
    protected String getJdbcUrl(CodeGenParam param) {
        // jdbc:sqlserver://localhost:1433;DatabaseName=DB_CustomSMS
        return "jdbc:sqlserver://" + param.getHost() +":" + param.getPort() + ";DatabaseName=" + param.getDatabase()+ "";
    }
}
