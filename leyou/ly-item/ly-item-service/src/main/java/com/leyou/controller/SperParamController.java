package com.leyou.controller;

import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("specParam")
public class SperParamController {
    @Autowired
    SpecParamService specParamService;

    //添加规格参数
    @RequestMapping("param")
    public void saveSpecParam(@RequestBody SpecParam specParam){
        if(specParam.getId()==null){
            specParamService.saveSpecParam(specParam);
        }else{
            specParamService.updateSpecParam(specParam);
        }
    }
    //删除规格参数
    @RequestMapping("param/{id}")
    public void deleteSpecParamById(@PathVariable("id")Long id){
        specParamService.deleteSpecParamById(id);
    }

    //根据分类id查询规格参数
    @RequestMapping("params")
    public List<SpecParam> findSpecParamBycid(@RequestParam("cid") Long cid){
        return  specParamService.findSpecParamBycid(cid);
    }
    //根据三级分类id和可搜索查询参数列表
    @RequestMapping("paramsByCid")
    public List<SpecParam> findSpecParamByGidAndSearching(@RequestParam("cid") Long cid){

        return specParamService.findSpecParamByGidAndSearching(cid);
    }

}
