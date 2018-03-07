# <center> 第二章 装配 Bean </center> #
- 在 spring 中，对象无需自己查找或创建与其所关联的其他对象。

## Spring 配置的可选方案
- Spring 提供了三种主要的装配方案
    1. 在 XML 中进行显示配置
    2. 在 Java 中进行显示配置
    3. 隐式的 bean 发现机制和自动装配

### 自动化装配Bean
 - 可以在类上使用 `@Component` 注解，这个注解表明这个类会被作为组件，并告知 *Spring* 为该类创建 Bean。
 - 在配置类（在一个类上使用了 `@Configuration` 注解）上使用 `@ComponentScan` 注解，该注解会默认扫描与配置类相同的包下的所有子包内的带有 `@Component` 注解的类；等同于xml文件中的 `<context :component-scan />` 标签。

 ### 为组件扫描的 bean 命名
 Spring 会根据类名为其指定一个 ID（类名的第一个首字母小写），也可以自己指定 ID，把期望的 ID 传递给注解 `@Component`。
 ```java
    @Component(“expectID”)
    public class SgPaper{
         /**/   
    }
 ``` 
  
###  设置组件扫描的基础包
 > 将配置类放在单独的包中，将其与应用代码分开。
- 在配置类（ 使用了注解 `@Configuration`的类 ）使用注解`@ComponentScan`
- 使用 `basePackages` 属性：
 - *eg:* `@ComponentScan(basePackages={"xxx","yyy"})` 
	  会扫描 `xxx` 和 `yyy` 包下的所有类。

- 使用 `basePackageClasses` 属性：
   - *eg:* `@ComponentScan(basePackageClasses={"xxx.class","yyy.class"})`
   将扫描类 xxx 和 yyy 包下的所有类。

### 通过添加注解实现自动装配：
> 通过在类的 **构造器上添加 `@Autowired` 注解** 表明 Spring 在创建这个 Bean 的时候，会通过这个构造器来进行实例化，并且会传入一个可设置的 Bean 。<br>
> **注：** 该注解也可用在任何类型的 Setter 方法上。
>  如果没有配置Bean，在创建上下文的时候 Spring 会抛出异常。此时可以将 `@Autowired` 的 `required` 属性设置为*false*。
 *eg:* `@Autowired(required=false)`


### 声明简单Bean
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

## 通过 Java 代码来装配 bean

### 创建配置类
-  创建 Java 配置类的关键在于为其添加 `@Configuration` 注解。
```java
@Configuration
public class ConfigClass{
} //:~ 配置类
```
- 在配置类上再添加 `@ComponentScan` 注解，并且通过它的 basePackages 或basePackageClasses 属性就可以指定扫描的类。

### 声明简单的 bean
- @Bean 注解会告诉 Spring 这个方法会返回一个对象，默认情况下对象的 ID 和方法名一样。也可以通过 name 属性指定。
```java
@Bean
public CDPlayer setBsdd(){

}
```
> 这个方法返回的是一个 ID 为 setBsdd 的 CDPlayer 对象。

### 借助 JavaConfig 实现注入
如上，在方法 setBsdd() 添加了注解 @Bean 当 方法 setBsdd 被调用的时候 Spring 会组织调用者，并直接返回已经创建好的 bean 而不是每次都去创建。

- 如果在配置类中引用了其他的 bean 可以通过方法来引用，也可以通过已经注入的 bean 的 ID　来引用。
```java
@Bean
public CDPlayer cdPlayer(){
    return new CDPlayer();
}

@Bean 
public CDPlayer antherPlayer(){
    return new CDPalyer(setBsdd());
}//:~  通过方法来引用


@Bean
public CDPlayer antherPalyer(CDPlayer setBsdd){
    return new CDPalyer(setBsdd); //:~ 通过ID 来引用
}

```

## 通过 XML 来配置规范
在使用 JavaConfig 的时候总是需要创建一个带有 `@Configuration` 注解的类。而在 XML 的方式中则需要一个以 `<beans>` 为元素根的 XML 文件。
### 声明一个简单的 <bean>
在上述的 XML　中声明一个 bean `<bean id="sdfsdf" class="xxxx.yyyyy">` 如果没有显示的指定 ID ，那 bean 的 ID 将会以 `xxxx.yyyy#0`的形式出现，‘#0’ 是一个计数器。

### 借助构造器注入初始化 bean



## 导入和混合配置
### 在 JavaConfig 中引用 XML 配置
- 在加载 JavaConfig 的同时，加载 XML 中的配置。
```java
@Configuration
@Import(CDPlayerConfig.class) // 导入 Java 配置
@ImportResource("classpath:cd-config.xml") //导入 XML 配置
public class SoundSystemConfig(){

}

```

### 在 XML 中混合配置
- 在 XML 使用 `<import>` 元素来引入 XML 配置文件。
    - `<import resource="cd-config.xml">` 
- 在 XML 中用`<bean>` 元素来引入 JavaConfig 配置。
    - `<bean class="soundsystem.Config">`


## 小结
容器负责管理应用中组件的声明周期，它会创建这些组件并保证它的依赖能够得到满足。