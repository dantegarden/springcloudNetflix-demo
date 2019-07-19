package com.example.product.client;

import com.example.product.common.dto.DecreaseStockInput;
import com.example.product.common.dto.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "biz-product")
public interface ProductClient {

    @GetMapping("/server/sayHello")
    public String productMsg();

    /**
     * 获取商品列表 被消费
     **/
    @PostMapping("/product/listForOrder")
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);
}
