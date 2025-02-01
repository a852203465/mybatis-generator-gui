package cn.darkjrong.mybatis.generator.codegen;

public interface CodeGenerator {

    Boolean supports(DbType dbType);

    void codeGen(CodeGenParam param);


















}
