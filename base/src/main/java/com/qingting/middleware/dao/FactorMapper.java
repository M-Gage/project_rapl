package com.qingting.middleware.dao;

import com.qingting.middleware.entity.Factor;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-13 14:50
 */
@Mapper
public interface FactorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Factor record);

    int insertSelective(Factor record);

    Factor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Factor record);

    int updateByPrimaryKey(Factor record);

    List<Factor> selectListByFactorStr(@Param("factor") String factor);
}