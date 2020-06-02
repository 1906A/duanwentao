package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    //品牌列表中的分页查询
    @RequestMapping("page")
    public Object findBrandByPage(@RequestParam("key") String key,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("rows") Integer rows,
                                  @RequestParam("sortBy") String sortBy,
                                  @RequestParam("desc") boolean desc){
        System.out.println(key+"=="+page+"=="+rows+"=="+sortBy+"=="+desc);

        PageResult<Brand> brandList = brandService.findBrand(key,page,rows,sortBy,desc);
        return brandList;
    }

    //添加品牌
    @RequestMapping("addOrEditBrand")
    public void add(Brand brand,@RequestParam("cids") List<Long> cids){

        //判断主键id是否有值
        if(brand.getId()!=null){
            brandService.updateBrand(brand,cids);
        }else{
            brandService.add(brand,cids);
        }

    }

    //根据品牌id删除
    @RequestMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id")Long id){
        brandService.deleteById(id);
    }
    //根据品牌id查询具体分类
    @RequestMapping("bid/{id}")
    public List<Category> findCategoryByBrandId(@PathVariable("id")Long pid){
        return brandService.findCategoryByBrandId(pid);
    }

    //根据分类查询品牌
    @RequestMapping("cid/{cid}")
    public List<Brand> findBrandBycid(@PathVariable("cid")Long cid){
        return brandService.findBrandBycid(cid);
    }
}
