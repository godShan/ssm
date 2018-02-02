package com.wys.test;


import com.wys.ssm.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/25/025.
 */
public class Test {

    public static void main(String args[]){
        List<User> voList = new ArrayList<>();
        voList.add(new User("admin","超级管理员"));
        voList.add(new User("wys","魏宇山"));

        voList.forEach(System.out::println);
    }
}
