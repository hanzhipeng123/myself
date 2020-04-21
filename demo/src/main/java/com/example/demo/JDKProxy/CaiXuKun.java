package com.example.demo.JDKProxy;

/**
 * @Author zhipeng.han@luckincoffee.com
 * @date 2020/4/21 10:20
 * @description :
 */
public class CaiXuKun implements Star{
    @Override
    public String sing(String name) {
        //System.out.println("鸡你太美");
        return name+"牛逼";
    }

    @Override
    public String dance(String name) {
        //System.out.println("跳");
        return name+"跳鸡你太美";
    }
}
