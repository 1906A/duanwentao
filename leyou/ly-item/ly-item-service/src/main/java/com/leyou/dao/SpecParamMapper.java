package com.leyou.dao;

import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpecParamMapper extends Mapper<SpecParam> {
    @Select("SELECT p.* FROM tb_spec_param p,tb_category Y WHERE p.cid = y.id AND y.id =#{cid}")
    List<SpecParam> findSpecParamBycid(Long cid);
}
