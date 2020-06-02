package com.leyou.service;

import com.leyou.dao.SkuMapper;
import com.leyou.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuService {
    @Autowired
    private SkuMapper skuMapper;
    //根据spuId 查询商品集合
    public List<Sku> findSkusBySpuId(Long id) {
        return skuMapper.findSkusBySpuId(id);
    }
}
