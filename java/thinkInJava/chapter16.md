# <center> 数组  </center> 
> 在第 5 章末尾，学习了如何让初始化一个数组。

## 数组为什么特殊
1. 数组是一种效率最高的存储和随机访问对象引用的方式。线性结构导致随机访问速度非常快，但是大小被限定。
2. 数组可以持有基本类型，泛型则不能。

## 数组是第一级对象
数组标识符是一个引用，指向在堆中创建的一个真实对象，这个对象用以保存指向其他对象的引用。
- length 是数组的大小，而不是实际保存的元素个数。

## 返回一个数组
> 在 Java 中可以直接返回一个数组。
```java
public String[] retArray(){
    String[] arr = new String[10];
    return arr;
}
```
## 数组与泛型
- 数组与泛型不能很好的结合，不能实例化具有参数话类型的数组。
    ```java
        Peel<Banana> peels  = new Peel<Banana>[10]; // illegal
    ```
    - 数组必须知道确切的类型，基于擦出的泛型会擦出具体的类型信息。

### 参数化数组本身
- 参数化方法
```java
    class ClassParameter{
        public static <T> T[] f(T[] arg){
            return arg;
        }
    }

    class ParameterArrayMethods{
        Double[] doubles = {1,2,3,4,5,5,6,7};
        Double[] doubles2 = ClassParameter.f(doubles);//
    }
```

## Arrays 实用功能
java.util 类库中有 Arrays 类，有一套用于数组的 static 方法。
- equals() :  比较连个数组是否相等。 deepEquals 用于多维数组。
    - 长度相等、对应位置的元素也相等。
    - 对应基本类型需要使用对应包装类型的 equals 方法 ： Integer.equals()。
- fill() : 用同一个值/引用填充。
- binnarySearch() : 用于已经排序的数组中查找元素。
- asList() : 返回一个 List 容器。

### 复制数组
- System.arraycopy() : 不会执行自动包装和拆包，两个数组必须有确切的类型信息。
    - 拷贝数组比 for 循环快的多。


```sequence
title: 这是一个列子


```