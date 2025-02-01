package cn.darkjrong.mybatis.generator.common.pojo.query;

import cn.darkjrong.mybatis.generator.common.pojo.dto.GeneratorConfigInfoFilterDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 生成配置信息查询对象
 * </p>
 *
 * @author Rong.Jia
 * @since 2025-01-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GeneratorConfigInfoQuery extends GeneratorConfigInfoFilterDTO implements Serializable {

    private static final long serialVersionUID = 1L;


}
