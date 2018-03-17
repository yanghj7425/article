# <center> 第 14 章 类信息 </center> #
运行时类型信息是的你可以在程序运行时发现和使用类型信息。

## 为什么需要 RTTI
Runtime type information ： 运行时，识别一个对象类型。<br>
面向对象编程中的基本目的时： 让带啊只能操作对基类的引用。由于动态绑定，通过泛化的引用来调用，也能产生正确的行为。

## Class 对象
- Java 使用 Class 对象来执行其 RTTI 。
- 每个类都有一个 Class 对象，运行这个程序的 Java 虚拟机( JVM ) ，使用“类加载器”的子系统。
- 所有的类都是在对其第一次使用的时候，动态加载到 JVM 中的。
- Class 的 newInstance() 方法是实现“虚拟构造器”的一种途径。使用 newInstance() 方法创建的类，必须有默认构造器。

### 类字面常量
Java 还提供了使用**类字面常量**来生成 Class 引用。
- 类字面常量不仅可以应用于普通类，也可以用于接口、数组、基本数据类型。

**注意**： 使用 “.class” 来创建 Class 对象的引用时，不会自动初始化该对象，首次调用的时候才会初始化。为了使用类而做的准备工作实际包含三个步骤：
1. 加载，这是由类加载器执行的。该步骤查找字节码，并从这些字节码中创建一个 Class 对象。
2. 链接。在链接阶段将验证类中的字节码，为静态域分配存储空间，如果必需的话还要解析这个类创建的对其他来的引用。
3. 初始化。如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块。 
```java
 Class<FactoryToy> ftClass = FactoryToy.class;
 FactoryToy factoryToy = ftClass.newInstance();
```
- Class.forName() 调用的对象立即就会进行初始化。

### 泛化的Class引用
向 Class 引用添加泛型语法的原因仅仅是为了提供编译期类型检查。

## 类型转换前先做检查
Java 中还有 instanceof 关键字。返回一个布尔值，告诉我们对象是不是某个特定类型的实例。
```java
if(x instanceof Dog){
    // 如果 x 是一个 Dog 实例。
}
```
### 动态的 instanceof
Class.isInstance() 方法提供了一种动态测试对象的途径。

## instanceof 与 Class 的等价性
在查询类信息时，以 instanceof 的形式( instanceof 或者 isInstance ) 的形式与直接比较 Class 对象有一个重要的差别。
- instanceof 保持的类型的概念，指的是：是这个类吗？或者是这个类的派生类吗？
- equals 和 == 一样没有考虑继承的因素。

## 反射：运行时的类信息
RTTI 和 反射之间的真正区别
 - RTTI，编译器在编译时打开和检查 .class 文件。
 - 反射，编译时 .class 文件时不可获取的。
    - Class.forName() 生成的结果在编译时是不可知的，因此所有的方法特征签名信息都是在执行时被提取出来的。

### 空对象
空对象，你可以假设所有的对象都是有效的，而不必浪费编程精力去检查 null。
- 空对象最有用之处在于它更靠近数据，因为对象表示的是问题空间内的实体。 即使空对象可以响应“实际”对象可以响应的所有消息，你仍需要某种方式去测试其是否为空。
- 空对象都是单例，因此这里将其作为静态 final 实例创建。
实例代码： src/com/yhj/chapter14

## 接口与类型信息
通过使用反射，仍旧可以到达并调用所有方法，甚至是**private**方法！如果知道方法名，就可以在其 Method 对象上调用 setAccessible(true)。
```java
static void callHiddenMethod(Object o, String methodName) throw Exception{
    Method m = o.getClass().getDeclaredMethod(methodName);
    m.setAccessible(true);
    m.invoke(a);
}

```