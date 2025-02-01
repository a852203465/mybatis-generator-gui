package cn.darkjrong.mybatis.generator.common.pojo.query;

import cn.darkjrong.mybatis.generator.common.pojo.dto.DbSourceFilterDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 数据源信息查询对象
 * </p>
 *
 * @author Rong.Jia
 * @since 2024-02-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DbSourceQuery extends DbSourceFilterDTO implements Serializable {

    private static final long serialVersionUID = 1L;


}
