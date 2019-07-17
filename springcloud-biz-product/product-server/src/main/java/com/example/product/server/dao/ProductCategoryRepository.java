package com.example.product.server.dao;

import com.example.product.server.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    /***查询类目*/
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
