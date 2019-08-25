# <center> 第 15 章 泛型 </center>

一般的类和方法，只能使用具体的类型：要么是基本类型，要么是自定义的类型。如果要编写可以应用于多种类型的代码，这种刻板的限制对代码的束缚会很大。<br>

- 在面向对象的编程语言中，多态算是一种泛型机制。

## 简单泛型

泛型主要目的是用来指定容器需要持有生么类型的对象，而且由编译器来保证类型的正确性。

```java
// T 类型参数
public class Holder<T>{
    private T t ;
    public Holder(T t){
        this.t = t;
    }
    public T getT(){
        return t;
    }
}

```

### 一个元组类库

元组：将一组对象直接打包存储与其中的一个单一对象。这样，仅一次方法调用就能返回多个对象。二元组如下：

- final 声明能够保护 public 元素。

```java
public class TwoTuple<A, B>{
    public final A a;
    public final B b;
    public TwoTuple(A a, B b){
        this.a = a;
        this.b = b;
    }
}
```

## 泛型接口

泛型也可用于接口。

```java
public interface Generator<T>{
    T next();
}
```

## 泛型方法

泛型方法使得该方法能够独立于类而变化。基本指导原则：无论何时，只要你能做到，就应该尽量使用泛型方法。

- 对于 static 方法，无法访问泛型类的类型参数。所以，要使 static 方法能使用泛型的能力，就必须要使其成为泛型方法。
- 类型参数由方法的放回类型前面的类型参数列表指定。

```java
public class GenericMethod(){
    public <T> void f(T t){  //如果传入基本类型，自动打包机制会介入
        // t.getClass().getName()
    }
}

```

### 可变参数与泛型方法

泛型方法与可变参数列表能很好的共存:

```java
public class GenericVarargs{
    public <T> List<T> makeList(T... t){
        List<T> result = new ArrayList<T>();
        for(T item : t){
            result.add(item);
        }
        return result;
    }
}
```

### 杠杆利用类型参数推断

- 可以省去很多代码。

```java
public class New {
    public static <K, V> map<K, V>{
        return new HashMap<K,V>();
    }
}
public class SimplePat{
    public static void main(String [] s){
        Map<Person,List<? extends Pet>> petP = New.map(); // 编译器会自动推断数据类型。
    }
}
```

> 如上所示：如果把 New.map() 作为参数传递给方法的话，编译器不会执行类型推断。

## 擦出的神秘之处

Java 的泛型是通过擦出来实现的。如: List<String> 和 List<Integer> 实际上运行的是相同的类型，这两种形式都被擦出成“原生”的类型。

```java
Class c1 = new ArrayList<Integer>().getClass();
Class c2 = new ArrayList<String>().getClass();
System.out.println(c1 == c2);// :~ true
```

### C++ 的方式

- 给定泛型边界
  - 边界 <T extends HashF> 声明 T 必须具有类型 HashF 或者 HashF 的导出类型。

### 擦出的问题

- 泛型不能用于显式地引用运行时类型的操作之中。如：转型、instanceof、new 表达式
  - 所有有关类型参数的信息都丢失了。

### 边界处的动作

- 在泛型的创建数组中，使用 Array.newInstance() 时推荐的方式。
- 即使擦出在方法或者类内部移除了有关实际类型的信息，编译器依旧可以确保在方法或在类中使用的内部一致性。
- 边界：编译器在编译期执行类型检查并插入转型代码的地点。

### 泛型数组

- 成功创建泛型数组的唯一方式是：创建一个被茶树类型的新数组。
  - 由于类型擦出： 需要知道确切的类型信息。

```java
public class GenericArray<T>{
    private T[] array;
    public GenericArray(int size){
        array = (T[]) new Object(size); // array = (T[]) Array.newInstance(type, size); //: Class<T> type;
    }
}

```

## 通配符

- 数组对象可以保留有关它们包含的对象类型的规则。

```java
Fruit [] fruit = new Apple[10];
fruit[0] = new Apple();
```

- Apple 的 list 在类型上不等价于 Fruit 的 list。
  1. 不能把一个涉及 Apple 的泛型赋值给一个涉及 Fruit 的泛型。

```java
// compile Error: incompatible types
List<Fruit> list = new ArrayList<Apple>();

```

- 通配符允许在两个类型之间创建某种类型的向上转型的关系。
  - 执行了这种向上转型，就将丢失向其中传递任何对象的能力，Object 也不行。

```java
    List<? extends Fruit> list = new ArrayList<Apple>();
    //Compile Error:
    list.add(new Apple());
```

**注意：** 并不意味着将持有任何类型的 Fruit；而是该引用没有具体指定的类型。

- 编译器直接拒绝对参数列表中涉及通配符的方法。 如上： list.add(); 此时 add() 方法的参数实际为 ：`? extends Fruit`。

### 逆变

使用：超类型通配符。声明通配符是由某个特定的任何基类界定的，方法指定`<? super MyClass>` 或者使用类型参数 `<? super T>`。 不能声明：`<? super MyClass>`。

```java
    public static void main(String[] args) {
        List<Fruit> list = new ArrayList<Fruit>();
        writeTo(list, new Apple());
        System.out.println(list);
    }

    static <T> void writeTo(List<? super T> list, T item) {
        list.add(item);
    }
```

- List 参数是 `<? super T>`，因此这个 List 持有从 T 导出某种 **具体类型**。

### 无边界通配符

无边界通配符 <?> : 意味着任何事物，允许参数是任何类型。

- List<?> 看起来等价于 List<Object>，而 List 也是 List<Object>。
  1. List 表示 “持有任何 Object 类型的原生 List”。
  2. List<?> 表示 “具有 **某种特定类型** 的非原生 List，只是不知道是什么类型”。

### 捕获转换

<?> 对编译器来说可能会推导出实际的类型参数。使得这个方法可以回转，并调用确切类型的方法。

```java
public class Fruit {
    public void type(){
        System.out.println("Fruit");
    }
}

public class Apple extends Fruit {
    @Override
    public void type() {
        System.out.println("Apple");
    }
}

public class GenericsAndCovariance {
    public static void main(String[] args) {
        List<Fruit> list = new ArrayList<Fruit>();
        writeTo(list, new Apple());
        writeTo(list, new Fruit());
        Iterator<Fruit> itr = list.iterator();
        while (itr.hasNext()) {
            Fruit fruit = itr.next();
            fruit.type();
        }
    }

    static <T> void writeTo(List<? super T> list, T item) {
        list.add(item);
    }
}/*Output

Apple
Fruit
*///:~

```

## 问题

阐述在使用 Java 泛型的时候会出现的各种问题。

### 任何基本类型不能作为类型参数

- 自动包装机制会介入

### 实现参数话接口

- 由于擦出，一个类不能实现一个泛型接口的两种变体。会被擦出为相同的类型。

### 转型和警告

- 使用带有泛型类型参数的转型或 instanceof 不会由任何效果。会被擦出为第一个边界，默认为 Object。

### 重载

由于擦出的原因，重载将产生相同的类型签名。

```java
void f(List<T> t){}
void f<List<W> w>{}
```

### 基类劫持接口 <sub>P<sub>404</sub></sub>

- 父类实现了一个泛型参数的接口，现在导出类也需要实现同样的接口但是泛型参数不一样，则不能通过编译。

## 自限定的类型

> SelfBounded 类接受泛型参数 T，而 T 由一个边界限定，这个边界就是拥有 T 作为其参数的。

```java
 class SelfBounded<T extends SelfBounded<T>>{

 }
```

- 可以保证类型参数必须与正在被定义的类相同；_自限定限制_ 只能作用于 **继承关系**。
