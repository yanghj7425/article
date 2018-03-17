# <center> 第 11 章 持有对象 </center> #
- 如果一个程序只包含固定数量的且声明周期已知的对象， 那么这是一个非常简单的程序。
    - 不能依靠创建命名的引用来持有每一个对象。
## 泛型和类型安全的容器
- 使用 java SE5 之前，编译器允许你向容器中添加一个不正确的类型。
- ArrayList 保存的是 Object 对象，所以可以通 add 方法添加不同的对象，但是如果要正确取出的时候需要强制转型。
```java
public class ArrayTest {
	public class A {
		void aOut() {
			System.out.println("A");
		}
	}

	public class B {
		void bOut() {
			System.out.println("B");
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ArrayTest s = new ArrayTest();
		ArrayList array = new ArrayList();
		array.add(s.new B());
		array.add(s.new A());
		ArrayTest.B b = (B) array.get(0);
		ArrayTest.A a = (A) array.get(1);
	//	ArrayTest.A a = array.get(1); //: ~ Type mismatch: cannot convert from Object to ArrayTest.A
		b.bOut();
		a.aOut();
		System.out.println(a); //输出 散列码
	}
}
``` 
- 输出的散列码是无符号十六进制，通过 hashCode() 产生。

##  基本概念
- java 容器类库的用途就是“保存对象”，并将其划分为两种不同的概念。
    1. Collection。一个独立元素的序列，这些元素都服从一条或多条规则。
    2. Map。一组成对的“键值对”对象，允许你使用的键来查找对象值。
## 添加一组元素
在 java.util 包中的 Arrays 和 Collections 类都有很多中方法，可以在 Collection 中添加一组元素。
    - Arrays.asList() 方法接收一个数组或者一个用逗号分隔的元素列表，并将其转变为 List 对象。底层是数组，不能执行 add() 或 delete() 操作。

##容器打印
默认的打印会调用容器提供的 toString() 方法。
- ArrayList 和 LinkList 都是 List 类型，能按照插入的顺序保存元素。两者的不同之处不仅在执行某些类型操作是的性能，而且 LinkList 包含的操作也多于 ArrayList。
- HashSet、TreeSet、LinkedHashSet 都是 Set 类型。HashSet 采用最快的技术来获取元素；LinkedHashSet 按照添加的顺序保存；TreeSet 采用比较结果升序保存结果。
    - Set 类型，不能出现相同的相。
    - 对应 Map 类型，是通过 “键值对” 来保存。

## List
List 承诺可以将元素位于在特定的序列中。是一种可修改序列。<br>
有两种基本类型:
 - 基本的 ArrayList，长于随机访问元素，但是在 List 中间插入和移除元素比较慢。
 - LinkedList，通过较低的代价在 List 中间添加和删除，提供优化的顺序访问。对于随机访问比较慢，但是操作比 ArrayList 多。

### 基本操作
1. indexOf() :  如果有一个对象引用，可以发现该对象在 List 中的索引编号，如果没有返回： -1 。
2. subList():   从较大的 List 中截取一个片段。返回的是原 List 的一个“视图”，所有操作都会映射到原 List 中， 反之亦然。
3. retainAll()、removeAll()、indexOf() 都依赖 equals() 方法。

## 迭代器
迭代器是一个对象，用来便利并选择序列中的对象。
1. 使用 iterator() 方法要求容器返回一个 Iterator。并准备好返回序列的第一个元素。
2. 使用 next() 方法获取系列中的下一个元素。
3. 使用 hasNext() 方法检查序列中是否还有元素。
4. 使用 remove() 方法将迭代器新近返回的元素删除。

### ListIterator
ListIterator 是一个更强大的 Iterator 的子类型，只能用于各种 List 类的访问，并且**可以双向移动**。

## LinkedList
LinkedList 也像 ArrayList 一样实现了基本的 List 接口。
- LinkedList 还可以作为栈、队列、双端队列用法。
## Stack
“栈” 指“后进先出”的容器。
- LinkedList 做栈用。
```java
public class Stack<T> {
    private LinkedList<T> storage = new LinkedList<T>();

    public void push(T t) {
        storage.addFirst(t);
    }

    public T peek() {
        return storage.getFirst();
    }

    public T pop() {
        return storage.removeFirst();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    public String toString() {
        return storage.toString();
    }
}

```

## Set
- TreeSet 将元素存储在 红-黑 树的数据结构中，而 HashSet 使用的是散列数列。


## Map


## Queue
- LinkedList 提供了支持队列的方法，并且实现了 Queue 接口。
```java
 Queue<Integer> queue = new LinkedList<Integer>();
```

### PriorityQueue
PriorityQueue 添加到 Java SE5 中，当调用 offer() 方法来插入一个对象得时，对象会被序列排序。默认得序列就是当前对象在队列中得自然顺序，也可以使用 Comparator 来修改排序规则。

## Foreach 与迭代器
foreach 语法主要用于数组，也可以用于 Collection 对象。
- Java SE5 引入得 Iterator 接口，该接口包含一个能产生 Iterator 对象得 iterator() 方法，foreach 用 iterator() 方法在序列中移动。