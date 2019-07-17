package com.example.order.server.controller;

import com.example.order.server.properties.EnvProperties;
import com.example.product.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    //以restTemplate方式请求服务
    @GetMapping("/getProductMsg")
    public String getProductMsg() {

        //第一种方式，直接使用restTemplate，url写死
//        RestTemplate restTemplate = new RestTemplate();
//        String rep = restTemplate.getForObject("http://localhost:8890/server/sayHello", String.class);


        //第二种方式，利用loadBalancerClient通过应用名获取url,再使用使用restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("biz-product");
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()) + "/server/sayHello";
        String rep = restTemplate.getForObject(url, String.class);

        //第三种方式，利用@LoadBalancer注解，可在restTemplate里使用应用名调用
//        String rep = restTemplate.getForObject("http://biz-product/server/sayHello", String.class);

        log.info("response={}", rep);
        return rep;
    }

    @GetMapping("/getProductMsgFeign")
    public String getProductMsgFeign() {
        String rep = productClient.productMsg();
        log.info("response={}", rep);
        return rep;
    }

    @Autowired
    private EnvProperties properties;
    /**测试配置中心**/
    @GetMapping("/env")
    public String getConfigEnv(){
        return properties.getLabel() + "-" + properties.getProfile() + ":" + properties.getVersion();
    }
}
