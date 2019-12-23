package com.hariko.design;

public class SingletonPattern {
}


class SingletonPatternOne{

    private SingletonPatternOne(){

    }

    private static class InstanceHolder{
        private final static SingletonPatternOne singletonPatternOne = new SingletonPatternOne();
    }

    public static SingletonPatternOne getInstance(){
        return InstanceHolder.singletonPatternOne;
    }
}

class SingletonPatternTwo{
    private SingletonPatternTwo(){

    }

    private enum  InstanceHolder{
        INSTANCE;

        private final SingletonPatternTwo singletonPatternTwo;

        InstanceHolder(){
            singletonPatternTwo = new SingletonPatternTwo();
        }

        public SingletonPatternTwo getInstance(){
            return singletonPatternTwo;
        }
    }

    public SingletonPatternTwo getInstance(){
        return InstanceHolder.INSTANCE.getInstance();
    }
}

class SingletonPatternThree{
    private SingletonPatternThree(){

    }

    private static SingletonPatternThree instance;

    public static SingletonPatternThree getInstance(){
        if(null == instance){
            synchronized (SingletonPatternThree.class){
                if(null == instance){
                    instance = new SingletonPatternThree();
                }
            }
        }

        return instance;
    }
}