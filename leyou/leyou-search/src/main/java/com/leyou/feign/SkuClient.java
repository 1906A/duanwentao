package com.leyou.feign;


import com.leyou.client.SkuClientServer;
import com.leyou.common.PageResult;
import com.leyou.pojo.vo.SpuVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "item-service")
public interface SkuClient extends SkuClientServer{

}
