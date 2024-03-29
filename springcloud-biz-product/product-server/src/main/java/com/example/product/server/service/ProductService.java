package com.example.product.server.service;


import com.example.product.common.dto.DecreaseStockInput;
import com.example.product.server.dto.CartDTO;
import com.example.product.server.entity.ProductInfo;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    List<ProductInfo> findList(List<String> productIdList);

    /**
     * 扣库存
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
