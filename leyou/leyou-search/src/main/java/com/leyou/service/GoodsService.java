package com.leyou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.entity.Goods;
import com.leyou.feign.SkuClient;
import com.leyou.feign.SpecClient;
import com.leyou.feign.SpuClient;
import com.leyou.pojo.Sku;
import com.leyou.pojo.SpecParam;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.vo.SpuVo;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {
    @Autowired
    private SpuClient spuClient;
    @Autowired
    private SkuClient skuClient;
    @Autowired
    private SpecClient specClient;


    private static final ObjectMapper MAPPER=new ObjectMapper();

    public Goods convert(SpuVo spuVo) throws Exception {
        Goods goods = new Goods();
        goods.setAll(spuVo.getTitle()+""+spuVo.getCname().replace("/","")+""+spuVo.getBname());
        //基础数据
        goods.setId(spuVo.getId());
        goods.setSubTitle(spuVo.getSubTitle());
        goods.setBrandId(spuVo.getBrandId());
        goods.setCid1(spuVo.getCid1());
        goods.setCid2(spuVo.getCid2());
        goods.setCid3(spuVo.getCid3());
        goods.setCreateTime(spuVo.getCreateTime());

        //复杂数据
        //根据spuid查询sku
        List<Sku> skuList = skuClient.findSpecParamByGidAndSearching(spuVo.getId());

        List<Long> price =new ArrayList<>();
        skuList.forEach(sku->{
            price.add(sku.getPrice());
        });

        goods.setPrice(price);
        goods.setSkus(MAPPER.writeValueAsString(skuList));

        Map<String, Object> specs =new HashMap<>();

        //根据三级分类id和可搜索条件查询规格参数
        List<SpecParam> specParamList = specClient.findSpecParamByGidAndSearching(spuVo.getCid3());

        //根据spuid查询spudetail
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuVo.getId());
        specParamList.forEach(sp ->{
            if(sp.getGeneric()){
                try {
                    Map<Long,Object> genericSpec = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>(){}) ;
                    String value = genericSpec.get(sp.getId()).toString();

                    if(sp.getNumeric()){
                        value = chooseSegment(value,sp);
                    }

                    specs.put(sp.getName(),value);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{

                Map<Long,Object> specialSpec = null;
                try {
                    specialSpec = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, Object>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String value = specialSpec.get(sp.getId()).toString();

                specs.put(sp.getName(),value);
            }

        });
        goods.setSpecs(specs);

        return goods;
    }
    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;

    }

}
