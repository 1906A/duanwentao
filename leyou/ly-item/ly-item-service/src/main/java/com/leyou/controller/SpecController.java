package com.leyou.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    SpecGroupService specGroupService;
    @Autowired
    SpecParamService specParamService;

    //查询商品规格
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpecGroupList(@PathVariable("cid")Long cateGoryId){

        return specGroupService.findSpecGroupList(cateGoryId);
    }
    /*
    * 保存商品规格
    * */
    @RequestMapping("group")
    public void saveSpecGroup(@RequestBody SpecGroup specGroup){
        //判断是修改还是添加
        if(specGroup.getId()==null){
            specGroupService.saveSpecGroup(specGroup);
        }else{
            specGroupService.updateSpecGroup(specGroup);
        }

    }

    //删除商品规格
    @RequestMapping("group/{id}")
    public void deleteBySpecGroupId(@PathVariable("id") Long id){
        specGroupService.deleteSpecGroup(id);
    }

    //根据组id查询参数列表
    @RequestMapping("params")
    public List<SpecParam> findSpecParamByGid(@RequestParam("gid") Long gid){

        return specGroupService.findSpecParamByGid(gid);
    }

}
