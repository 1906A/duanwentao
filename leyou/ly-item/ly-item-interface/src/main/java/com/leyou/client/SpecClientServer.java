package com.leyou.client;

import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("specParam")
public interface SpecClientServer {
    //根据组id查询参数列表
    @RequestMapping("paramsByCid")
    public List<SpecParam> findSpecParamByGidAndSearching(@RequestParam("cid") Long cid);
    }
