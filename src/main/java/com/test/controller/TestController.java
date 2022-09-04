package com.test.controller;

import com.test.service.ITestService;
import com.test.service.impl.BoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

/**
 * @Author: 吴成伟
 * @date: 2022/8/7 17:44
 * @Description: TODO
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ITestService testService;

    @GetMapping("/")
    public void speak(){
        testService.speak();
    }

    @Autowired
    private BoxServiceImpl boxService;
    @GetMapping("/testbox")
    public void test() throws IOException {
        File file = new File("D:\\Python\\project\\project0\\pc\\pc01\\down\\img-umei\\1.jpg");
        boxService.uploadFile("169494271897","a1/b1",file,"");
    }
}
