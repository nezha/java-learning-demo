
## 搞懂Java内部类

### 1、单例模式下的静态内部类

首先大家应该都知道，`《Effective Java》` 上所推荐使用静态内部类！这种写法仍然使用JVM本身机制保证了线程安全问题；由于 SingletonHolder 是私有的，除了 getInstance() 之外没有办法访问它，因此它是懒汉式的；同时读取实例的时候不会进行同步，没有性能缺陷；也不依赖 JDK 版本。

但是是不是可以直观的感受一下这种 `懒加载` 的模式。

**先上代码看一下**

```java
@Slf4j
public class Singleton {
    static {
        System.out.println("Out Class Load");
    }
    private static class SingletonHolder{
        static {
            System.out.println("Inner Class Load");
        }
        public static Singleton instance = new Singleton();
    }
    private Singleton(){
        System.out.println("Singleton initialized...");
    }

    public Singleton(String say){
        log.info("Singleton Say:{}",say);
    }
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }
}

public class SingletonTest {

    @Test
    public void testSingle(){
        //1. 没有调用静态内部类的时候，静态内部类不会加载到内存中。
        Singleton singleton = new Singleton("nezha");

        //2.通过静态内部类来实例化外部类，是线程安全的
        Singleton singleton1 = Singleton.getInstance();
    }
}
```

```text
Out Class Load
22:47:24.483 [main] INFO com.nezha.learn.innerclass.single.Singleton - Singleton Say:nezha
Inner Class Load
Singleton initialized...
23
22:47:24.508 [main] INFO com.nezha.learn.innerclass.single.Button - Start Button Class...
```

这个就比较明显了，静态内部类的静态代码块区域并没有初始化，需要等调用初始化的时候才加载到堆内存。

---

### 2、内部类的理解

话不多说、还是直接上代码！

```java
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

    //2.局部内部类，可以访问外部类属性，但是不运行修改局部变量params
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

public class Main {
    @Test
    public void testInnerClass(){
        //非静态内部类 创建方式1
        ClassOuter.InnerClass innerClass = new ClassOuter().new InnerClass();
        //非静态内部类 创建方式2
        ClassOuter outer = new ClassOuter();
        ClassOuter.InnerClass inner = outer.new InnerClass();
        //静态内部类的创建方式
        ClassOuter.StaticInnerClass staticInnerClass = new ClassOuter.StaticInnerClass();
        //非静态内部类修改外部类非静态属性
        inner.add();
        System.out.println(outer.getNoStaticInt());
        outer.testFunctionClass("Hello");
    }
}
```

> 最为主要的就几种方式和需要探讨的问题。

1. 普通内部类，是否可以访问、修改外部类属性
2. 局部内部类，是否可以访问外部类属性，是否可以修改传参值
3. 静态内部类是否可以访问外部类属性。

---

> 对于这些问题，通过上面的例子可以很清晰的看出：

1. 普通内部类，是可以访问外部内的所有静态和非静态属性
2. 局部内部类，可以访问外部类属性，但是不运行修改局部变量params(传参类型需要final类)
3. 静态内部类禁止访问外部的非静态属性


## 参考文献

1、https://juejin.im/post/5a903ef96fb9a063435ef0c8#heading-10