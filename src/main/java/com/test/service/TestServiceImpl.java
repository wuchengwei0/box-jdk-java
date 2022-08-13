package com.test.service;

import cn.hutool.core.util.IdUtil;
import com.box.sdk.BoxConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 吴成伟
 * @date: 2022/8/7 17:45
 * @Description: TODO
 */
@Service
@Scope("prototype")
public class TestServiceImpl implements ITestService {

    private static List<String> list = new ArrayList<>();

    private ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();

    static {
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test4");
    }


    @Async
    @Override
    public void speak() {
//        System.out.println("list size is :"+list.size());
//        for (String s : list) {
//            System.out.print(s+"\t");
//        }
        String uuid = IdUtil.randomUUID();
        String format = new SimpleDateFormat("HH:mm:ss").format(new Date()) + " " + new Random().nextInt(10);
        System.out.println(format + " is:"+uuid + "\tThreadName is"+Thread.currentThread().getName());
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println("hashCode is :"+hashCode());
        threadLocal.set(new HashMap<>());
        threadLocal.get().put("uuid",uuid);
        te(format);
        System.out.println();
    }
    public void te(String args){
        System.out.println(args+" is:"+threadLocal.get().get("uuid"));
    }
    public void ds(){
    }
}
