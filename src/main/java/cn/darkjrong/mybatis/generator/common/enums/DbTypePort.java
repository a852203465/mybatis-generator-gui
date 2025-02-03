package cn.darkjrong.mybatis.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DB类型端口
 *
 * @author Rong.Jia
 * @date 2025/02/03
 */
@Getter
@AllArgsConstructor
public enum DbTypePort {

    NULL(-1),
    mysql(3306),
    oracle(1521),
    sqlserver(1433),
    postgresql(5432),
    sqlite(0);



    ;

    private final Integer value;



}
