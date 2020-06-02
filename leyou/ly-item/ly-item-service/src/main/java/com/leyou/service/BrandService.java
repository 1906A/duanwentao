package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    BrandMapper brandMapper;
    public PageResult<Brand> findBrand(String key, Integer page, Integer rows, String sortBy, boolean desc) {
        //pageHelper分页
        PageHelper.startPage(page,rows);

        List<Brand> brandList= brandMapper.findBrand(key,sortBy,desc);

        PageInfo<Brand> pageInfo = new PageInfo<Brand>(brandList);

        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());

    }

    //新增品牌
    public void add(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);

        cids.forEach(id ->{
        brandMapper.addType(brand.getId(),id);
        });

    }

    public void deleteById(Long id) {
        //删除brang表
        Brand brand = new Brand();
        brand.setId(id);
        brandMapper.deleteByPrimaryKey(brand);
        //删除关系表
        brandMapper.deleteBrandAndCategory(id);
    }

    public List<Category> findCategoryByBrandId(Long pid) {
        return  brandMapper.findCategoryByBrandId(pid);
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        //1.修改品牌表
        brandMapper.updateByPrimaryKey(brand);

        //2.修改品牌和分类的关系表
        // 分类表 先删掉品牌下所有分类，再重新添加分类
        //删除分类
        brandMapper.deleteBrandAndCategory(brand.getId());
        cids.forEach(cid ->{
            brandMapper.addType(brand.getId(),cid);
        });
    }

    public List<Brand> findBrandBycid(Long cid) {
        List<Brand> brandList = brandMapper.findBrandBycid(cid);
        return brandList;
    }
}
