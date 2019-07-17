package com.example.product.server.service.impl;

import com.example.demo.biz.common.enums.ProductStatusEnum;
import com.example.product.server.dao.ProductInfoRepository;
import com.example.product.server.dto.CartDTO;
import com.example.product.server.entity.ProductInfo;
import com.example.product.server.enums.ExceptionEnum;
import com.example.product.server.exception.ProductException;
import com.example.product.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> fiindList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDTO.getProductId());
            //判断商品是否存在
            if (!productInfoOptional.isPresent()) {
                throw new ProductException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            //判断库存是否足够
            ProductInfo productInfo = productInfoOptional.get();
            Integer decreaseResult = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (decreaseResult < 0) {
                throw new ProductException(ExceptionEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(decreaseResult);
            productInfoRepository.save(productInfo);
        }
    }
}
