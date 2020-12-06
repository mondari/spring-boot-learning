package com.mondari.proxy;

/**
 * 目标类
 */
public class TargetObject implements TargetInterface {

    @Override
    public void action() {
        System.out.println("这是目标类");
    }
}
