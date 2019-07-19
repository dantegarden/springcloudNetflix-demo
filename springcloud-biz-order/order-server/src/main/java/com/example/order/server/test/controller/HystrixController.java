package com.example.order.server.test.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/test-hystrix")
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    @Autowired
    private RestTemplate restTemplate;

    /**测试熔断***/
    //超时配置 改为在yml里配置
//    @HystrixCommand(fallbackMethod = "getProductInfoListFallback" ,commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    })
    //断路器配置 改为在yml里配置
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name="circuitBreaker.enabled", value="true"),
//            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
//            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
//            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60")
//    })
    @HystrixCommand
    @GetMapping("/getProductInfoList/{number}")
    public String getProductInfoList(@PathVariable("number") Integer number){
        //传入偶数直接返回，否则就会请求服务
        if(number % 2 == 0){
            return "success";
        }
        String orderId = "157875196366160022";
        String url  = "http://biz-product/product/listForOrder";
        return restTemplate.postForObject(url, Arrays.asList(orderId), String.class);
    }

    public String getProductInfoListFallback(Integer number){
        return "太拥挤了，请稍后再试~";
    }

    @HystrixCommand
    @GetMapping("/getProductInfoList2")
    public String getProductInfoList2(){
        throw new RuntimeException();
    }

    public String defaultFallback(){
        return "默认降级: 太拥挤了，请稍后再试~";
    }
}
