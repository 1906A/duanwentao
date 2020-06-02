package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.pojo.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    public PageResult<SpuVo> findSpuByPage(String key, Integer page, Integer rows, Integer saleable) {
        //pageHelper分页
        PageHelper.startPage(page,rows);

        List<SpuVo> spuList = spuMapper.findSpuByPage(key, saleable);

        PageInfo<SpuVo> pageInfo= new PageInfo<SpuVo>(spuList);

        return new PageResult<SpuVo>(pageInfo.getTotal(),pageInfo.getList());

    }

    //保存商品信息
    public void saveSpuDetail(SpuVo spuVo) {
        Date nowDate = new Date();
        //保存spu
        Spu spu = new Spu();
        spu.setTitle(spuVo.getTitle());
        spu.setSubTitle(spuVo.getSubTitle());
        spu.setBrandId(spuVo.getBrandId());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setSaleable(false);//默认保存时不上架商品
        spu.setValid(true);
        spu.setCreateTime(nowDate);
        spu.setLastUpdateTime(nowDate);

        spuMapper.insert(spu);
        //保存spu_detail
          SpuDetail spuDetail = spuVo.getSpuDetail();
          spuDetail.setSpuId(spu.getId());
//        SpuDetail spuDetail = new SpuDetail();
//        spuDetail.setSpuId(spuVo.getId());
//        spuDetail.setAfterService(spuVo.getSpuDetail().getAfterService());
//        spuDetail.setDescription(spuVo.getSpuDetail().getDescription());
//        spuDetail.setGenericSpec(spuVo.getSpuDetail().getGenericSpec());
//        spuDetail.setPackingList(spuVo.getSpuDetail().getPackingList());
//        spuDetail.setSpecialSpec(spuVo.getSpuDetail().getSpecialSpec());
        spuDetailMapper.insert(spuDetail);
        //保存sku
        List<Sku> skus = spuVo.getSkus();
        skus.forEach(sku->{
            sku.setSpuId(spu.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);
            //库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
        //保存stock
    }
    //修改商品回显spu/detail/{spuId}
    //根据spuid查询商品集列表
    public SpuDetail findSpuDetailBySpuId(Long spuId) {
        return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    //修改商品信息
    public void updateSpuDetail(SpuVo spuVo) {
        Date nowDate = new Date();
        //修改spu
        spuVo.setSaleable(null);//默认保存时不上架商品
        spuVo.setValid(null);
        spuVo.setCreateTime(null);
        spuVo.setLastUpdateTime(nowDate);
        spuMapper.updateByPrimaryKeySelective(spuVo);
        //修改spuDeatil
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);
        //修改sku
        List<Sku> skus = spuVo.getSkus();
        skus.forEach(s -> {
            //删除sku
            s.setEnable(false);
            skuMapper.updateByPrimaryKey(s);
            //库存
            stockMapper.deleteByPrimaryKey(s.getId());
        } );
        //保存sku
        List<Sku> skus1 = spuVo.getSkus();
        skus1.forEach(sku->{
            sku.setSpuId(spuVo.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);
            //库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
        //修改stock
    }
    //删除
    public void deleteSpuBySpuId(Long spuId) {
        //删除sku
        List<Sku> skuList = skuMapper.findSkusBySpuId(spuId);
        skuList.forEach(s ->{
            //删除sku
            s.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(s);
            //库存
            stockMapper.deleteByPrimaryKey(s.getId());
        });
        //删除spuDetail
        spuDetailMapper.deleteByPrimaryKey(spuId);
        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);


    }

    public void upOrDown(Long spuId,int saleable) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable==1?true:false);
        spuMapper.updateByPrimaryKeySelective(spu);
    }
}

