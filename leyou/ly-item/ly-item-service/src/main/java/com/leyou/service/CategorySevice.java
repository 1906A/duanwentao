package com.leyou.service;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategorySevice {
    @Autowired
    CategoryMapper categoryMapper;

    //根据节点id所有分类信息
    public List<Category> findCategory(Category category){
        return categoryMapper.select(category);
    }

    //测试
    public Category findCate(int id){
        return categoryMapper.findCate(id);
    }

    //添加商品分类
    public void CategortAdd(Category category) {
        categoryMapper.insertSelective(category);
    }

    //修改商品分类
    public void CategortUpdate(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
    //删除商品分类
    public void deleteById(Long id) {
        Category category = new Category();
        category.setId(id);
        categoryMapper.deleteByPrimaryKey(category);
    }
}
