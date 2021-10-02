package com.alibabademo.alibaba.SingletonEntity;


//This is just to practice the Singleton class I learnt from YouTube video tutorials
public class SingletonDemo {

    //creating a private static reference to SingletonDemo
    private static SingletonDemo singletonDemo = null;

    //making the constructor private
    private SingletonDemo(){}

    //creating a public method to access Singleton object
    public static SingletonDemo getSingletonDemo(){

        if(singletonDemo == null){
            //It is synchronized to ensure only one thread can access the object at a time
            synchronized (SingletonDemo.class){
                singletonDemo = new SingletonDemo();
            }
        }
        return singletonDemo;
    }
}
