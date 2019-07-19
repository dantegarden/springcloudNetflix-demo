package com.example.product.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDecreaseStockInput {
    private String orderId;
    private List<DecreaseStockInput> decreaseStockInputList;

    public OrderDecreaseStockInput(String orderId, List<DecreaseStockInput> decreaseStockInputList) {
        this.orderId = orderId;
        this.decreaseStockInputList = decreaseStockInputList;
    }
}
