# static、final、static final 引用

> 本人见识有限，若有问题欢迎拍砖、学习。此外这里主要是我发现一个问题，想让大家都看看，讨论讨论。

背景：之前看见项目里面有这么写单例的：

```java
public class A {

	private String name;

	private static  A a = new A();

	private A() {

	}

	public static  A factoryA() {
		return a;
	}
}
// omit getter and setter for name property
```
看到这段代码我就想到，之前自己都是一直用 static final 修饰的。通过 static final 定义的引用 可作为单例使用。这里，应该牵扯 2 个概念有必要提一下：

1. static 修饰的引用存储在堆中，会被线程共享。
2. final 修饰的引用不能指向其他对象（这个我有个疑问，所以才有了这篇文章）。

所以，线程安全的对象我就理解为单例了。（我潜意识里面，认为不能修改引用的对象就为单例，显然不对。额~ 感觉没描述清楚。。。）<br>

回到上面的代码，没有用 final 修饰。如果 引用 a 指向了其他对象，那通过 factoryA() 获取的方法就不能保证在系统中是同一个对象。所以我就在想怎么修改，第一个想到的就是反射，然后有了下面的代码：

```java
public static void main(String[] args) throws Exception {
		A a = A.factoryA();
		A b = A.factoryA();
		A c = null;

		a.setName("not be replace");
		Class<? extends A> clazz = A.class;

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			System.out.println(field.getName() + "\t" + field.get(a).getClass().getSimpleName());
			if (field.getName().equals("a")) {
				field.set(null, new A("you have been replace"));
				c = (A) field.get(null);
			}
		}

		A e = A.factoryA();

		System.out.println("a:\t" + a.getName());
		System.out.println("b:\t" + b.getName());
		System.out.println("c:\t" + c.getName());
		System.out.println("e:\t" + e.getName());

	}//:~out
	/**
	 * name	String 
	 * a	A
	 * a:	not be replace
	 * b:	not be replace
	 * c:	you have been replace
	 * e:	you have been replace 
	 *
	 */

```

结果很显然，static 修饰的域被修改了。（我也不确定，是不是代码的问题或我对放射理解的问题，但是这里看到的结果就是这样。）<br>

到这儿，我在 static 修饰符后面添加了 final 关键字。
```java
    private static final  A a = new A();
```
如此，上面的代码显然不能正常干活了。但是在 [stackoverflow](https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection)上找到了方法（这段代码是网上的）：

```java
	 static void setFinalStatic(Field field, Object newValue) throws Exception {
	        field.setAccessible(true);
	        Field modifiersField = Field.class.getDeclaredField("modifiers");

	        // wrapping setAccessible 
	        AccessController.doPrivileged(new PrivilegedAction() {
	            @Override
	            public Object run() {
	                modifiersField.setAccessible(true);
	                return null;
	            }
	        });

	        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	        field.set(null, newValue);
	    }
```

如下调用后：
```java
	setFinalStatic(A.class.getDeclaredField("a"), new A("you have been replace"));
		
		A e = A.factoryA();

		System.out.println("a:\t" + a.getName());
		System.out.println("b:\t" + b.getName());
	//	System.out.println("c:\t" + c.getName());
		System.out.println("e:\t" + e.getName());

```
结果表明，static final 修饰的引用在上面的方式下会被指向其他对象。所以，我的疑问就是：不是说 final 修饰引用不能指向其他对象，那么上面的试验结果怎么解释……