### Spring实战（装配Bean）
>这一节主要是看了Spring实战，自己总结下觉得有必要的，在这里Mark下……
#### *Spring* 采采取以下4种关键策略：
  1. 基于*POJO*的轻量级和最小侵入性（强迫应用继承它们的类或者实现它们的接口而导致应用与框架绑死）编程；
  2. 通过依赖注入和面向对象的接口实现松耦合；
  3. 基于切面和惯例进行声明式编程；
  4. 通过切面和目标减少样版式代码；
  
#### 耦合具有两面性：
1. 紧密耦合的代码难以测试、难以复用、难以理解并典型的暴露出"打地鼠"的*Bug*特性；
2. 完全没有耦合的待么什么也做不了。
 -  通过 **DI**，对象的依赖关系将由系统中负责协调各对象的第三方组件在创建对象的时候设定。

#### 自动装配Bean
 - 可以在类上使用`@Component`注解，这个注解表明这个类会被作为组件，并告知*Spring*为该类创建Bean.
 -  在配置类（在一个类上使用了`@Configuration`注解）上使用`@ComponentScan`注解，该注解会默认扫描与配置类相同的包下的所有子包内的带有`@Component`注解的类；等同于xml文件中的`<context :component-scan />`标签。
  
####   设置组件扫描的基础包
 > 将配置类放在单独的包中，将其与应用代码分开。

- 使用`basePackage`属性：
 - *eg:* `@Component(basePackage={"xxx","yyy"})` 
	  会扫描 `xxx`和`yyy`包下的所有类。

- 使用`basePackageClasses` 属性：
   - *eg:* `@Component(basePackageClasses={"xxx.class","yyy.class"})`
   将扫描类 xxx 和 yyy 包下的所有类。

#### 通过添加注解实现自动装配：
> &nbsp; 通过在类的 **构造器上添加`@Autowired`注解** 表明 Spring 在创建这个 Bean 的时候，会通过这个构造器来进行实例化，并且会传入一个可设置的 Bean 。
> &nbsp; **注：** 该注解也可用在任何类型的Setter方法上。
> &nbsp; 如果没有配置Bean，在创建上下文的时候Spring会抛出异常。此时可以将`@Autowires`的 `required`属性设置为*false*。
&nbsp; &nbsp; &nbsp; &nbsp; *eg:* `@Autowired(required=false)`


##### 声明简单Bean
>在*JavaConfig* 中声明 *bean* ，使用`@Bean`这个注解，会告诉Spring 返回一个Bean，该Bean默认**ID** 为带有`@Bean` 注解的方法名。
>也可是使用`@Bean`注解的name属性指定ID：`@Bean(name="xxx")`。<br>

- 注入：
    注意的是使用：
    
```java
	@Bean
	public CDpaly cDpaly(ComPactDisc compactDisc){
		return new  CDpaly(compactDisc);
	}
```
>采用这种方式时，不要求`ComPactDisc` 在同一个配置类中，其不管以何种方式，只要注册到了Spring的上下文中即可。