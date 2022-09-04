package com.test.Chain;

/**
 * @Author: 吴成伟
 * @date: 2022/8/14 14:53
 * @Description: TODO
 */
public class Main {
    public static void main(String[] args) {
        User user = User.builder().id(70).userName("shz").email("123123").build();
        System.out.println(user.toString());
    }
}
