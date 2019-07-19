package com.example.order.server.service;


import com.example.order.server.dto.OrderDTO;

public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 修改订单状态为入库
     * */
    void changeOrderStatus(String orderId);

    /**
     * 完结订单(只能卖家操作)
     *
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
