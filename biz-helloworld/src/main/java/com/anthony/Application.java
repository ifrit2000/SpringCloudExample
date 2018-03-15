package com.anthony;

import com.anthony.feign.BizDoSth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication
@RestController
@EnableFeignClients
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${server.port}")
    String port;

    @Value("${code}")
    String code;

    @Autowired
    BizDoSth bizDoSth;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "helloWorld:" + port+code;
    }

    @RequestMapping("/do/{message}")
    public String helloWorld(@PathVariable String message) {
        return "helloWorld:" + port+code+"\n"+bizDoSth.helloWorld(message);
    }

    //没有这个bean,zipkin不会有追踪图
    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }
}
