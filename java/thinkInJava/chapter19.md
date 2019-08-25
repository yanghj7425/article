# <center> 第 19 章 枚举类型 </center>

- 关键字 enum 可以将一组具名的值的有限集合创建为一种新的类型，而这些具名的值可以作为常规的程序组件使用。
- enum 除了不能继承，其余的几乎跟一个常规类一样。

## 基本 enum 特性

- 创建 enum 时，编译器会自动继承 java.lang.Enum。
- 编译器会自动提供 equals() 和 hashCode() 方法，可以直接使用 == 来比较 enum 实例。
- Enum 类实现了 Comparable 和 Serializable 接口。
- 可以添加新方法。

实例代码：

```java
public enum Shrubbery {
    GROUND("this is  ground"),
    UPPER(" this is upper"),
    LOWER("this is lower");

    private String description;


    private Shrubbery(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
```

> 为每一个 Enum 实例添加一个描述信息，此时需要一个只能在内部使用的构造函数（枚举，只能在内部使用构造器创建 enum 实例）。<br>
> 添加新方法时，要在最后一个枚举实例后面添加';'（分号）。

```java
public class Main {
    public static void main(String[] args) {
        for (Shrubbery s : Shrubbery.values()) {
            System.out.println(s + "\t" + s.getDescription());
            if (s.compareTo(Shrubbery.GROUND) == 0){
                System.out.println("compareTo");
            }
            if(s == Shrubbery.GROUND){
                System.out.println(" == ");
            }
        }
    }
}

```

## 在 switch 中使用 enum

> 先声明一个 enum 实例，然后把它作为 key 放入 switch 子句。

```java
public class SwitchEnum {
    Shrubbery s = Shrubbery.GROUND;
    private void switchChange() {
        switch (s) {
            case GROUND:
                s = Shrubbery.LOWER;
                break;
            case LOWER:
                s = Shrubbery.UPPER;
                break;
            case UPPER:
                s = Shrubbery.GROUND;
                break;
        }
    }
}

```

## 随机选取

```java
public class Enums{
    private Random rand = new Random(47);
    public static <T extends Enum<T>> T random(Class<T> ec){
        return random(ec.getEnumConstants());
    }
    public static <T> T random(T[] values){
        return values[rand.nextInt(values.length)];
    }
}
```

## 使用 EnumSet 替代标志

- 能更好的说明一个二进制位是否存在，并且无需担心性能。

## 使用 EnumMap

- EnumMap 是一种特殊的 Map，要求其中的 key 必须来自一个 enum。由于 enum 限制，EnumMap 内部由数组实现。

实例代码：

```java
public interface Command {
    void  action();
}
```

> 定义一个接口

```java
public class EnumMapMain {
    public static void main(String[] args) {
        EnumMap<Shrubbery, Command> map = new EnumMap<Shrubbery, Command>(Shrubbery.class);
        map.put(Shrubbery.GROUND, new Command() {
            @Override
            public void action() {
                System.out.println("GROUND Commond");
            }
        });
        for (Map.Entry<Shrubbery, Command> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().action();
        }
    }
}
```

- 上述实例演示了命令模式的用法。
  1.  通常，命令模式首先需要一个只有单一方法的接口，然后从该接口实现具有各自不同行为的多个子类。

## 常量相关的方法

> Java 的 enum 允许程序员为 enum 实例编写方法，从而为每个 enum 实例赋予不同的行为 ： 也称**表驱动方法**。

```java
public class TableDriveCode {
    public enum Table {
        ROW {
            void detail() {
                System.out.println("This is Row");
            }
        },
        COL {
            void detail() {
                System.out.println("This is Col");
            }
        };
       abstract void detail();
    }
    public static void main(String[] args) {
        for (Table t : Table.values()) {
            t.detail();
        }
    }
}

```

- 枚举类中需提供相应的方法签名，才能通过枚举的实例调用各自的**驱动方法**。
  1. 除抽象方法外，具名的方法也可以被覆盖。

## 枚举分发
