package com.nezha.learn.innerclass;

/**
 * @author: nezha <br>
 * @Title: ClassOuter <br>
 * @ProjectName: inner-class-demo <br>
 * @Description: xxxxx <br>
 * @Date: 2019/9/10 5:25 PM <br>
 */
public class ClassOuter {
    private int noStaticInt = 1;
    private static int STATIC_INT = 2;

    public void fun() {
        System.out.println("外部类方法");
    }
    public int getNoStaticInt(){
        return noStaticInt;
    }

    //1.普通内部类，是可以访问外部内的所有静态和非静态属性
    public class InnerClass {
        //static int num = 1; 此时编辑器会报错 非静态内部类则不能有静态成员
        public void fun(){
            //非静态内部类的非静态成员可以访问外部类的非静态变量。
            System.out.println(STATIC_INT);
            System.out.println(noStaticInt);
        }
        public void add(){
            noStaticInt++;
            //内部类可以调用外部类方法
            printHello();
        }
    }

    //2.局部内部类，可以访问外部内属性，但是不运行修改局部变量params
    public void testFunctionClass(final String params){
        class FunctionClass{
            private void fun(){
                System.out.println("局部内部类的输出");
                System.out.println(STATIC_INT);
                noStaticInt = noStaticInt + 1;
                System.out.println(noStaticInt);
                System.out.println(params);
                //params ++ ; // params 不可变所以这句话编译错误
            }
        }
        FunctionClass functionClass = new FunctionClass();
        functionClass.fun();
    }

    //3.静态内部类禁止访问外部的非静态属性
    public static class StaticInnerClass {
        //静态内部类可以有静态成员
        static int NUM = 1;
        public void fun(int x){
            System.out.println(STATIC_INT);
//            System.out.println(noStaticInt); //此时编辑器会报 不可访问外部类的非静态变量错
        }
    }

    public void printHello(){
        System.out.println("Say Hello ...");
    }
}
