package com.example.order.server.controller;

import com.example.demo.biz.common.bean.Result;
import com.example.order.server.converter.OrderForm2OrderDTOConverter;
import com.example.order.server.dto.OrderDTO;
import com.example.order.server.enums.ExceptionEnum;
import com.example.order.server.exception.OrderException;
import com.example.order.server.form.OrderForm;
import com.example.order.server.service.OrderService;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result<Map<String, Object>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
            throw new OrderException(ExceptionEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空，orderForm={}", orderForm);
            throw new OrderException(ExceptionEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDTO);

        return Result.ok(ImmutableMap.of("orderId", result.getOrderId()));
    }
}
