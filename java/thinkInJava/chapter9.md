# <center> 第 9 章 接口 </center>

> 与创建类一样，但创建接口需要用 `implenmets` 关键字替代`class` 关键字。接口也可以包含域，但是这些**域**是隐式的 `static` 和 `final` 的。

## 完全解耦

- 只要一个方法操作的是类而非接口，那么你就只能使用这个类及其子类。
- 策略模式：创建一个能够个根据所传递的参数对象的不同儿具有不同行为的方法。
  - 策略就是传递进去的参数对象，它包含要执行的代码。

## java 中的多重继承

- 使用接口的核心原因： 1. 为了能向上转型为多个基类。 2. 防止客户端程序员创建该类，并确保这仅仅是建立一个接口。
  > **注意 ：** 如果要创建不代任何方法定义和成员变量的基类，那就选择接口。如果知道某事物为一个基类，那首先因该设计成接口。

## 接口中的域

### 初始化接口中的域

> 在接口中定义的域不能是 “空 final ”，但是可以被非常量表达式初始化。他们的值被存储在该接口的静态存储区域内。

```java
public interface RandVals{
    Random RAND= new Random(47);
    int RAND_INT= RAND.nextInt(10);// 被非常量表达式初始化
}
```

### 接口嵌套

> 实现一个 private 接口是有一种方式，它可以强制该接口中的方法定义不要添加任何类型的信息（不允许向上转型）。

```java
class A{
    private interface D{
        void f();
    }
    private class DImp2 implements D{
       @Override
        public void f() {
           System.out.println("DImp@");
        }
    }
    private D  dRef;
    public void reciveD(D d){ //  有与 private 接口相关的使用权
        dRef = d;
        dRef.f();
    }
    public D getD(){ // 与 private 接口有关
        return new DImp2();
    }
    public static void main(String[] args) {
        A  a =  new A();
       a.reciveD(a.getD());
    }
}
```

- getD() 方法，只有一种方法可以成功，就是将返回值交给有使用权的对象（它本身）。

## 接口与工厂

- 生成遵循某个接口的对象的典型方式就是**工厂方法**设计模式。
  1. 定义接口。
  2. 实现接口。
  3. 定义工厂接口，返回（1）中定义的接口。
  4. 实现工厂接口，返回遵循（2）所定义接口的对象。
     > 实例程序 : chapter9/factorymethod

## 总结

抽象性都应该是应真正的需求而产生的。当必须时，你应该重构接口儿不是到处添加额外级别的间接性，并由此带来的额外的复杂性。

> 恰当的原则是：应该优先考虑类而不是接口，从类开始，如果接口的必须性变得非常明确，那么就进行重构。**接口是一个重要的工具，但是容易被滥用**。
