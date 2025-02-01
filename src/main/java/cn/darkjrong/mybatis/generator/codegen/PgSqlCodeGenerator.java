package cn.darkjrong.mybatis.generator.codegen;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

@Component
public class PgSqlCodeGenerator extends BaseCodeGenerator {

    @Override
    public Boolean supports(DbType dbType) {
        return DbType.postgresql.equals(dbType);
    }

    @Override
    protected String getJdbcUrl(CodeGenParam param) {
        String schema = param.getSchema();
        if (StrUtil.isBlank(schema)) schema = "public";
        // jdbc:postgresql://xxxxx:5432/leadtrans
        return "jdbc:postgresql://" + param.getHost() +":" + param.getPort() + "/" + param.getDatabase() + "?currentSchema=" + schema;
    }
}
