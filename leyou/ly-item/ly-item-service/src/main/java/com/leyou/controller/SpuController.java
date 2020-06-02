package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.vo.SpuVo;
import com.leyou.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @RequestMapping("page")
    public PageResult<SpuVo> findSpuByPage(@RequestParam("key") String key,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("rows") Integer rows,
                                           @RequestParam(required = false,value = "saleable") Integer saleable){

        return spuService.findSpuByPage(key,page,rows,saleable);
    }

    //保存商品信息
    @RequestMapping("saveOrUpdateGoods")
    public void saveSpuDetail(@RequestBody SpuVo spuVo){
        if(spuVo.getId()!=null){
            spuService.updateSpuDetail(spuVo);
        }else{
            spuService.saveSpuDetail(spuVo);
        }


    }

    //修改商品回显spu/detail/{spuId}
    //根据spuid查询商品集列表
    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId){
        SpuDetail spuDetail = spuService.findSpuDetailBySpuId(spuId);
        return spuDetail;
    }
    //删除
    @RequestMapping("deleteById/{spuId}")
    public void deleteSpuBySpuId(@PathVariable("spuId") Long spuId){
        spuService.deleteSpuBySpuId(spuId);

    }
    //上下架
    @RequestMapping("upOrDown")
    public void upOrDown(@RequestParam("spuId") Long spuId,
                         @RequestParam("saleable") int saleable){
        spuService.upOrDown(spuId,saleable);

    }
}
