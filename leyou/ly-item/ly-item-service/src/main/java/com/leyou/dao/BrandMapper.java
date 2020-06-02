package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper  extends Mapper<Brand> {
    List<Brand> findBrand(@Param("key")String key,
                          @Param("sortBy")String sortBy,
                          @Param("desc")boolean desc);

    @Insert("insert into tb_category_brand values (#{cid},#{bid})")
    void addType(Long bid, Long cid);

    @Delete("delete from tb_category_brand where brand_id = #{id}")
    void deleteBrandAndCategory(Long id);

    @Select("SELECT * FROM tb_category_brand t,tb_category y WHERE y.id =  t.category_id AND t.brand_id =#{pid}")
    List<Category> findCategoryByBrandId(Long pid);

    @Select("SELECT d.* FROM tb_brand d,tb_category_brand b WHERE d.id = b.brand_id AND b.category_id=#{cid}")
    List<Brand> findBrandBycid(Long cid);
}
