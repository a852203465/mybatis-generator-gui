package cn.darkjrong.mybatis.generator.codegen;

import java.util.ArrayList;
import java.util.List;



public class CodeGenUtils {

    private static final List<CodeGenerator> generators = new ArrayList<>();

    static {
        generators.add(new MysqlCodeGenerator());
        generators.add(new OracleCodeGenerator());
        generators.add(new PgSqlCodeGenerator());
        generators.add(new SqlServerCodeGenerator());
        generators.add(new SqliteCodeGenerator());
    }

    public static void codeGen(CodeGenParam param){
        generators.stream().filter(a -> a.supports(param.getDbType()))
                .findAny().ifPresent(b -> b.codeGen(param));
    }


}
