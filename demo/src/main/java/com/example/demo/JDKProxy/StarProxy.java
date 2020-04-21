package com.example.demo.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author zhipeng.han@luckincoffee.com
 * @date 2020/4/21 10:23
 * @description :
 */
public class StarProxy implements InvocationHandler {

    /**
     * 目标类,也就是被代理对象
     */
    private Object target;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这里可以做增强
        Object object = method.invoke(target, args);

        return object;
    }

    /**
     * 生成代理类
     * @return
     * @description :
     * 方法CreatProxyObj返回的对象才是我们的代理类，它需要三个参数，
     * 前两个参数的意思是在同一个classloader下通过接口创建出一个对象，
     * 该对象需要一个属性，也就是第三个参数，它是一个InvocationHandler。
     * 需要注意的是这个CreatProxyedObj方法不一定非得在我们的StarProxy类中，
     * 往往放在一个工厂类中。
     */
    public Object creatProxyObj(){
        Object object = Proxy.newProxyInstance(this.target.getClass().getClassLoader(),
                this.target.getClass().getInterfaces(), this);
        return object;
    }
}
