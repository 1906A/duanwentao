package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategorySevice categorySevice;

    //根据节点id查询所有
    @RequestMapping("list")
    public List<Category> list(@RequestParam("pid") Long pid){
        Category category = new Category();
        category.setParentId(pid);
        return  categorySevice.findCategory(category);
    }
    @RequestMapping("list1")
    public Category findCate(@RequestParam("pid")Integer pid){

        return categorySevice.findCate(pid);
    }

    //添加商品分类
    @RequestMapping("add")
    public String add(@RequestBody Category category){
        String result = "SUCC";
        try{
            categorySevice.CategortAdd(category);
        }catch (Exception e){
            System.out.println("添加商品分类异常");
            result = "FALL";
        }
        return result;
    }
    //修改商品分类
    @RequestMapping("update")
    public String update(@RequestBody Category category){
        String result = "SUCC";
        try{
            categorySevice.CategortUpdate(category);
        }catch (Exception e){
            System.out.println("修改商品分类异常");
            result = "FALL";
        }
        return result;
    }
    //删除商品分类
    @RequestMapping("deleteById")
    public String deleteById(@RequestParam("id") Long id){
        String result = "SUCC";
        try{
            categorySevice.deleteById(id);
        }catch (Exception e){
            System.out.println("修改商品分类异常");
            result = "FALL";
        }
        return result;
    }

}
