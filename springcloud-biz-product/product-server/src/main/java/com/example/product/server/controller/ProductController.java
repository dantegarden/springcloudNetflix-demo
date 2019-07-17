package com.example.product.server.controller;

import com.example.demo.biz.common.bean.Result;
import com.example.product.server.dto.CartDTO;
import com.example.product.server.entity.ProductCategory;
import com.example.product.server.entity.ProductInfo;
import com.example.product.server.service.ProductCategoryService;
import com.example.product.server.service.ProductService;
import com.example.product.server.vo.ProductInfoVO;
import com.example.product.server.vo.ProductVO;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /***
     * 查询所有在架的商品
     * 获取类目type列表
     * 查询类目
     * 构造数据
     * */
    @GetMapping("/list")
    public Result<ProductVO> list() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        List<ProductVO> productVOList = Lists.newArrayList();
        for (ProductCategory category : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());
            List<ProductInfoVO> infoList = Lists.newLinkedList();
            for (ProductInfo product : productInfoList) {
                if (product.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVO vo = new ProductInfoVO();
                    BeanUtils.copyProperties(product, vo);
                    infoList.add(vo);
                }
            }
            productVO.setProductInfoVOList(infoList);
            productVOList.add(productVO);
        }
        return Result.ok(productVOList);
    }

    /**
     * 获取商品列表 被消费
     **/
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList) {
        return productService.fiindList(productIdList);
    }

    /**
     * 减库存 被消费
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList) {
        productService.decreaseStock(cartDTOList);
    }
}
