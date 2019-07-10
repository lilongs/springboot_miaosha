package com.example.demo.dao;

import com.example.demo.dataobject.PromoDO;
import org.apache.ibatis.annotations.Param;

public interface PromoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    PromoDO selectByPrimaryKey(Integer id);

    PromoDO selectByItemId(@Param("itemId") Integer itemId);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);
}