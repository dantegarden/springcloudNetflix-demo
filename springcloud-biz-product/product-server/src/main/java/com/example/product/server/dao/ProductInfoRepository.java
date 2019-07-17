package com.example.product.server.dao;

import com.example.product.server.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    /***所有在架的商品*/
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * 查多个商品信息
     **/
    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
