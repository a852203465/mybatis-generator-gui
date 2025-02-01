package cn.darkjrong.mybatis.generator.codegen;

import org.springframework.stereotype.Component;

@Component
public class MysqlCodeGenerator extends BaseCodeGenerator {

    @Override
    public Boolean supports(DbType dbType) {
        return DbType.mysql.equals(dbType);
    }

    @Override
    protected String getJdbcUrl(CodeGenParam param) {
        return "jdbc:mysql://" + param.getHost() + ":" + param.getPort() + "/" + param.getDatabase() + "?useUnicode=true&useSSL=false&characterEncoding=utf8";
    }
}
