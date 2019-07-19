package com.example.order.server.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot 的启动类启动时，默认扫描其所在的根目录及其子目录。
 * 如果注入的bean不在启动类所在的目录或者其子目录下的话，默认扫描是找不到的，
 * 这时候就需要在启动类上添加扫描的目录
 * 把common模块里的bean也扫描到
 * <p>
 * EnableFeignClients扫描别的服务所提供的FeignClient
 **/

@Configuration
@ComponentScan(basePackages = {"com.example.biz.common", "com.example.product"})
@EnableFeignClients(basePackages = {"com.example.product.client"})
public class CommonConfig {
}
