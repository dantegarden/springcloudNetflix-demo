package com.example.product.server.service.impl;

import com.example.product.common.dto.DecreaseStockInput;
import com.example.product.common.dto.ProductInfoOutput;
import com.example.product.server.dao.ProductInfoRepository;
import com.example.product.server.dto.CartDTO;
import com.example.product.server.entity.ProductInfo;
import com.example.product.server.enums.ExceptionEnum;
import com.example.product.server.enums.ProductStatusEnum;
import com.example.product.server.exception.ProductException;
import com.example.product.server.service.ProductService;
import com.google.common.collect.Lists;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);
        List<ProductInfoOutput> productInfoOutputList  = productInfoList.stream().map(e -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(e, productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());

        //存储到redis
        saveProductStore2Redis(productInfoOutputList);
    }

    //纯数据库操作 减库存
    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = Lists.newArrayList();
        for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
            //判断商品是否存在
            if (!productInfoOptional.isPresent()) {
                throw new ProductException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            //判断库存是否足够
            ProductInfo productInfo = productInfoOptional.get();
            Integer decreaseResult = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            if (decreaseResult < 0) {
                throw new ProductException(ExceptionEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(decreaseResult);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }

    private static final String PRODUCT_STORE_PREFIX = "product_store_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //存储到redis中
    public void saveProductStore2Redis(List<ProductInfoOutput> productInfoOutputList){
        for (ProductInfoOutput productInfoOutput: productInfoOutputList) {
            String key = String.format(PRODUCT_STORE_PREFIX, productInfoOutput.getProductId());
            stringRedisTemplate.opsForValue().set(key, String.valueOf(productInfoOutput.getProductStock()));
        }
    }
}
