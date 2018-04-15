@(Spring)[Part III. What’s New in Spring Framework 4.x]
#Part III. Core Technologies

这部分的参考文档覆盖的所有技术，都是Spring框架的组成部分。
其中最主要的是Spring框架中的反转控制（IOC）容器。Spring框架的IoC容器的彻底的处理是紧随其后的是Spring的全面覆盖的面向方面编程(aop)技术。Spring框架有它自己的AOP框架，它的概念是容易理解的，他成功的解决了80%的企业级AOP编程的需求。
……
## IoC 容器
### Spring IOC 容器和 Java bean 的介绍
这章覆盖了Spring 框架的 IoC 的实现规则。IoC 因依赖注入儿众所周知。这也是定义它自己依赖的方式的一种方式，就是这样和其他的对象跟它一起工作，只有通过构造函器的参数，参数传递给一个工厂方法或者属性，在它的构造器或者工厂方法返回之后设置一个实例。当它创建类的时候容器注入这些依赖关系。这个过程是反转的基础，因此叫做反转控制（IoC），类它自己控制实例或者它依赖的位置通过直接构造类，或一个机制如服务定位器模式。<br>
org.springframework.beans 和 org.springframework.context 包是 Spring 框架Ioc的基础， BeanFactory 接口提供了一个高级的配置机制关于管理任何类型的对象。ApplicationContext 是 BeanFactory 的一个子接口，他更容易的集成了Spring AOP 的特性： 消息资源控制，公共事件和应用层指定的上下文例如：WebApplicationContext 被用在 Web 应用中。<br>
总而言之，BeanFactory 提供了框架的配置和基础的功能，ApplicationContext 添加了更多企业级的特性。ApplicationContext 是一个 BeanFactory 的一个完美超类，它仅仅被用在这章描述 Spring 的 Ioc 容器。更多用 BeanFactory 代替 ApplicationContext 的信息，参考7.16 BeanFactory。

在 Spring 中，对象在应用中主要的形式和被 IOC 容器管理叫做 Bean，一个 Bean 是一个对象，它是一个实例，一个集合和任何被 Ioc 容器管理的。另外，一个 bean 不过是在你应用中众多对象中的一个。Beans ，他们之间的依赖关系呗映射到一个配置的数据文件里面被容器使用。
## 7.2容器总览
org.springframework.context.ApplicationContext 接口代表了 Spring 的 Ioc 容器和负责人实例化、配置、集成上述提到的 bean。容器得到他的实例对于每一个通过读配置数据文件实例、配置、装配的对象。数据配置文件描述在 XML 文件、Java 注解、或者 Java 代码。它允许你表达你的对象然后组成你的应用并且丰富对象之间的依赖接口。<br>
一些实现了 ApplicationContext 接口被 Spring 提供为没有内建的。在单例应用中，通常是创建一个 ClasspathXmlApplication 或者FileSystemXmlApplicationContext 实例。XML 已经形成传统的格式，为了配置的数据文件你可以指示容器使用 Java 注解或者代码作为数据元格式通过提供少量的配置文件起声明使能支持这些添加的元数据格式。<br>
在大多数应用的场景，显示的用户代码对于一个实例或者多个实例不是必须要的。例如，在 web 应用场景中，简单的 8 行样本文件 web 描述文件在 web.xml 中已经是足够的。如果你正在用 Spring Tool Suite 这样的开发环境这样的样本配置文件可以可以在点几下鼠标或者少量的按键后容易的被创建。<br>
在 ApplicationContext 被创建和初始化之后，你的应用类和配置数据将被整合，你拥有一个配置完整和可以执行的系统和应用。

### 配置元数据
Spring 的 IOC 容器需要一个格式的来配置元数据；这个元数据代表你作为一个应用开发者告诉 Spring 容器怎样在应用中实例化、配置和集成对象。

**注意**<br>
> 基于 XML 的元数据不是唯一的配置方式。Spring Ioc 容器已经完全从某一种形式的元数据中解耦。目前，许多的开发者在 Spring 应用中选择使用 基于 Java 配置的方式.<br>

1. XML 配置通过在 `<beans>` 元素内使用 `<bean>` 元素来定义。

2. Java 配置通过使用 @Configuration 注解类，在类内部使用 @Bean 注解方法。
    - 关于 Java 配置的注解：
        - @Configuration
        - @Bean
        - @Import
        - @DependsOn

### 实例化一个容器
实例化一个 Ioc 容器是简单的，ApplicaationContext 构造器是一个实际的资源字符串允许从多种外部资源加载配置元数据，如：本地文件系统，Java ClassPath 等等。
```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```

### 合并基于 XML 的配置元数据
可以使用 ApplicationContext 构造器从所有的 XML 碎片（多个 XML 文件）中加载 bean 的定义。构造器可以持有多个 Resources 路径，就像上面这样。另外也可以使用 `<import/>` 元素:

```xml
<!-- 文件名称 ： beans.xml -->
<beans>
    <import resource="services.xml"/>
    <import resource="resources/messageSource.xml"/>
    <import resource="/resources/themeSource.xml"/>
    <bean id="bean1" class="..."/>
    <bean id="bean2" class="..."/>
</beans>
```
> **注意:**<br>
所有的路径都是相对于定义文件做的导入。beans.xml 和 servives.xml 在同一个路径下。<br>
使用 `../` 引用一个父目录里面的文件是可能的，但是不推荐。<br>
尤其是不推荐使用 `classpath:` URL(如：`classpath:../services.xml`),这样运行时会选择最近的根路径然后再查找父路径。Classpath 配置更改可能会选择不同加载的不同的一个不正确的目录。<br>
如果使用绝对路径：比如，`file:c:/config.xml`。 一般来说：最好保持一个对于这些资源路径的相对引用，可以通过`${}`占位符，JVM 在运行时解析属性。 


### 容器的使用
Application 使你可以读到 bean 的定义和访问他们通过下面的方式：
```java
// create and configure beans
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);
// use configured instance
List<String> userList = service.getUsernameList();

```

## Bean 的概念

容器的 bean 被绑定元数据创建，例如，xml 里面的 `<bean/>` 定义的。在容器内部这些 bean 被定义为一个包含元数据的 BeanDefinition 对象，通常包含以下信息：
- 一个指定的包名：通常定义一个指定的实现类 bean。
- bean 的行为配置元素，bean 在容器里面的状态（作用域、生命周期回掉 等等）。
- bean 之间的依赖关系。
- 在新创建的对象中配置其他的设置，比如，用于管理连接池的连接数、连接池的大小。

此外，Spring 也允许用户注册一个已经存在的被其他容器创建的对象。通过 getBeanFactory() 访问 ApplicationContext 的 BeanFactory，它返回的 BeanFactory 实现了的 DefaultListableBeanFactory 支持通过 registerSingleton() 和 registerBeanDefinition() 方法注册。<br>

在 xml 的配置里面，使用 id 或 name 属性来指定一个标识符。如果想要给 bean 起别名，可以使用 name 属性。如果没有指定 id/name 容器会自动为 bean 生成一个唯一的名称。然而如果想要明确的通过名称来引用，就必须提供一个名称。<br>
通过 `<alias/>` 元素可以用不同的名称访问同一个对象：
```xml
<alias name="subsystemA-dataSource" alias="subsystemB-dataSource"/>
<alias name="subsystemA-dataSource" alias="myApp-dataSource" />
```
> 这样就有三个名称指向同一个对象。


### 通过构造器实例化
通过默认构造器实例化 bean ：
```xml
    <bean id="exampleBean" class="examples.ExampleBean"/>
    <bean name="anotherExample" class="examples.ExampleBeanTwo"/>
```

### 通过静态工厂方法实例化
创建的类可以通过 factory-method 属性指定方法名称来通过指定的静态工厂方法来实例化：<br>
xml 配置：
```xml
    <bean id="clientService"
    class="examples.ClientService"
    factory-method="createInstance"/>
```

Java Demo:
```java
public class ClientService {
    private static ClientService clientService = new ClientService();
    private ClientService() {}
    public static ClientService createInstance() {  //静态工厂方法
        return clientService;
    }
}

```

### 使用实例工厂方法来实例化
和静态工厂方法类似，实例工厂方法从容器中调用一个 bean 的非静态方法来创建一个新的 bean。为了使用这种机制，保持 class 属性为空，factory-bean 属性里面指定当前容器中被调用的 bean 对象，factory-method 指定方法名称。
- 一个实例化工厂可以实例化多个 bean。<br>
xml 配置：
```xml
<!-- the factory bean, which contains a method called createInstance() -->
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
<!-- inject any dependencies required by this locator bean -->
</bean>
<!-- the bean to be created via the factory bean -->
<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>
```
Java Demo：
```java
public class DefaultServiceLocator {
    private static ClientService clientService = new ClientServiceImpl();

    public ClientService createClientServiceInstance() { // 非静态方法
     return clientService;
    }
}

```

## 依赖
一个典型的企业级应用不会由单一的一个对象组成。甚至一个简单的应用也会由多个对象一起工作。

### 依赖注入
依赖注入是通过定义对象来定义他们的依赖关系，这样，其他的对象就可以一起工作，只有通过构造器参数、工厂方法参数或属性设置一个对象，在对象被构造或者工厂方法返回之后。当 bean 被创建的时候，容器就会注入这些依赖。

### 构造器注入
构造器的注入是通过容器调用一个有参数的构造器完成的，每一个参数代表一个依赖关系。

#### 构造器参数解析
构造器使用参数类型来匹配当前的构造器。如果在构造器参数定义上没有歧义，在 bean 被构建的时候将按照构造器参数的顺序实例化：
```java
package x.y;
public class Foo {
    public Foo(Bar bar, Baz baz) {
    // ...
    }
}
```
没有歧义存在，假设 Bar 和 Baz 不存在继承关系。就可以像下面这样配置：
```xml
<beans>
    <bean id="foo" class="x.y.Foo">
        <constructor-arg ref="bar"/>
        <constructor-arg ref="baz"/>
    </bean>
    <bean id="bar" class="x.y.Bar"/>
    <bean id="baz" class="x.y.Baz"/>
</beans>

```
当一个类被引用，类型匹配就可以出现。当使用简单类的时候，比如 `<value>true</value>`，没有类型值得帮助 Spring 不能通过类型匹配：
```java
package examples;
public class ExampleBean {
// Number of years to calculate the Ultimate Answer
    private int years;
    // The Answer to Life, the Universe, and Everything
    private String ultimateAnswer;
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}

```
如果明确指定了构造器参数的类型，容器可以通 type 属性类类型匹配一些简单类型：
```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg type="int" value="7500000"/>
    <constructor-arg type="java.lang.String" value="42"/>
</bean>
```
使用下标属来明确指定构造器的参数：
```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
</bean>
```

### 基于 setter 的赖注入
基于 setter 的依赖注入是通过在 bean 调用无参构造器或者无参数的静态工厂方法实例化你的 bean 之后调用 setter 方法。<br>
下面的例子展示了你可以单纯的通过 setter 注入来实现依赖注入：
```java
public class SimpleMovieLister {
    // the SimpleMovieLister has a dependency on the MovieFinder
    private MovieFinder movieFinder;
    // a setter method so that the Spring container can inject a MovieFinder
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }   
// business logic that actually uses the injected MovieFinder is omitted...
}
```
ApplicationContext 对 bean 的管理支持构造器注入和 setter 注入。也可以通过构造器注入容器内已经存在的依赖。
- 在 setter 方法中使用 @Required 注解可以使属性成为必须的依赖。
- Spring 推荐使用构造器注入它使一个实现的应用组件作为一个不可变的对象并确保必须的依赖不为空。
- setter 注入使可以选择的依赖项的首选。
    - 它可以设置一个默认值。
    - setter  注入使对象可以重新配置或者重新注入。

### 依赖解析过程
容器会这样执行去解决依赖问题：
- ApplicationContext 会被配置元数据描述的 bean 创建和初始化。配置元数据可以通过 xml、Java code、或者注解。
- 对于每一个 bean 它的依赖关系通过属性、构造参数、静态工厂方法的参数表达。当这个 bean 被实际创建的时候这些依赖关键会被提供给 bean。
- 每一个属性或构造器参数是被实际定义的值设置的，或者是引用容器里面其他的 bean。
- 每一个属性或者构造器参数会从指定的格式转变回属性或构造器参数实际的类型。默认的，Spring 提供默认的值转变从一个 String 到一个编译类型，比如：int、long、String、boolean等等。
> **循环依赖**<br>
使用构造器注入的时候会产生一个循环依赖的场景：A 依赖 B，B 依赖 A。如果这样配置 Spring 在运行时会抛出一个 BeanCurrentlyInCreationException。<br>
**解决办法：** 使用 setter 注入， 虽然不推荐，但是使用 setter 注入可以配置一个循环依赖。

- spring 的 bean 默认采用预实例化和单例的方式：
    - 在这个 bean 实际被实例化之前预先分配一些时间和空间，这样可以发现一些 ApplicationContext 配置过程中的问题。可以改变默认的行为 lazy-initialize 而不是 pre-instantiated；或者改变作用域，为一个非单例的。

#### 依赖注入的列子
- xml 配置的元数据基于 setter 方法的注入：<br>
    xml 实例：<br>
    ```xml
    <bean id="exampleBean" class="examples.ExampleBean">
        <!-- setter injection using the nested ref element -->
        <property name="beanOne">
            <ref bean="anotherExampleBean"/>
        </property>
        <!-- setter injection using the neater ref attribute -->
        <property name="beanTwo" ref="yetAnotherBean"/>
        <property name="integerProperty" value="1"/>
    </bean>
    <bean id="anotherExampleBean" class="examples.AnotherBean"/>
    <bean id="yetAnotherBean" class="examples.YetAnotherBean"/>

    ```
    java 实例: <br>

    ```java
        public class ExampleBean {
            private AnotherBean beanOne;
            private YetAnotherBean beanTwo;
            private int i;
            public void setBeanOne(AnotherBean beanOne) {
                this.beanOne = beanOne;
            }
            public void setBeanTwo(YetAnotherBean beanTwo) {
                this.beanTwo = beanTwo;
            }
            public void setIntegerProperty(int i) {
                this.i = i;
            }
        }

    ```
- xml 配置的元数据基于构造器的注入：<br>
    xml 实例：<br>
    ```xml
        <bean id="exampleBean" class="examples.ExampleBean">
        <!-- constructor injection using the nested ref element -->
            <constructor-arg>
                <ref bean="anotherExampleBean"/>
            </constructor-arg>
            <!-- constructor injection using the neater ref attribute -->
            <constructor-arg ref="yetAnotherBean"/>
            <constructor-arg type="int" value="1"/>
        </bean>
        <bean id="anotherExampleBean" class="examples.AnotherBean"/>
        <bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
    ```
    java 实例：
    ```java
        public class ExampleBean {
            private AnotherBean beanOne;
            private YetAnotherBean beanTwo;
            private int i;
            public ExampleBean(
            AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
                this.beanOne = anotherBean;
                this.beanTwo = yetAnotherBean;
                this.i = i;
            }
        }

    ```
- 使用静态工厂方法注入：
    - 静态工厂方法使用时，可以用 `<constructor-arg>` 元素来指定参数。<br>
    
    xml 实例：
    ```xml
        <bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
            <constructor-arg ref="anotherExampleBean"/>
            <constructor-arg ref="yetAnotherBean"/>
            <constructor-arg value="1"/>
        </bean>
        <bean id="anotherExampleBean" class="examples.AnotherBean"/>

    ```
    java 实例：
    ```java
        public class ExampleBean {
        // a private constructor
            private ExampleBean(...) {
            //...
            }
            // a static factory method; the arguments to this method can be
            // considered the dependencies of the bean that is returned,
            // regardless of how those arguments are actually used.
            public static ExampleBean createInstance (
                AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
                ExampleBean eb = new ExampleBean (...);
                // some other operations...
                return eb;
            }
        }

    ```

## 依赖配置细节
你可以定义属性和构造参数引用其他被管理的 bean。在 xml 的配置中，支持`<property/>` 和 `<constructor-arg/>` 元素来支持这个功能。<br>
你可以配置一个 java.util.Properties 实例：
```xml
<bean id="mappings"
    class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <!-- typed as a java.util.Properties -->
    <property name="properties">
        <value>
        jdbc.driver.className=com.mysql.jdbc.Driver
        jdbc.url=jdbc:mysql://localhost:3306/mydb
        </value>
    </property>
</bean>
```
- Spring 容器通过 PropertyEditor 机制，转变 `<value/>` 元素内部的文本到一个 java.util.Properties 实例。 Spring 团队推荐使用嵌套 `<value/>` 元素来覆盖 value 属性。

### idref 元素
 idref 元素是一个简单的错误保证方式；通过其他在容器里面 bean 的 id 来引用元素到一个`<constructor-arg/>` 或 `<property/>` 元素。<br>
 xml 实例:
 ```xml
    <bean id="theTargetBean" class="..."/>
    <bean id="theClientBean" class="...">
        <property name="targetName">
            <idref bean="theTargetBean"/>
        </property>
    </bean>
 ```
 上面的实例和下面的实例是完全等价的：
 ```xml
    <bean id="theTargetBean" class="..." />
    <bean id="client" class="...">
        <property name="targetName" value="theTargetBean"/>
    </bean>
 ```
- 更加推荐使用 idref 标签，这个标签运行容器在发布时校验引用、名称是否确实存在。
> **注意**<br>
自从 4.0 beans xsd 以后 idref 元素就不提供 local 属性，不提供对一个常规的 bean 的引用。简单的改变已经存在的 idref local 引用为 idref bean 就可以升级为 4.0 语法。

一个共同的地方(最新的和 spring 2.0)，`<idref/>` 元素带来的值是在一个拦截器里面作为 ProxyFactoryBean 被定义的。使用 `<idref/>` 元素当你用拦截器去指定拦截元素的时候会保证你不会拼写错误。

### 内部 bean
一个 `<bean/>` 元素在 `<property/>` 或者  `<constructor-arg/>` 元素内部定义，叫作内部 bean。
```xml
<bean id="outer" class="...">
    <!-- instead of using a reference to a target bean, simply define the target bean inline -->
    <property name="target">
        <bean class="com.example.Person"> <!-- this is the inner bean -->
            <property name="name" value="Fiona Apple"/>
            <property name="age" value="25"/>
        </bean>
    </property>
</bean>

```
- 内部类总是匿名的，随着外部类的创建而创建的。

### 使用 depends-on
depends-on 属性可以现实的强制一个或者多个 bean ，在使用了 depends-on 的这个元素初始化之前。<br>
xml 实例,依赖单个 bean:
```xml
<bean id="beanOne" class="ExampleBean" depends-on="manager"/>
<bean id="manager" class="ManagerBean" />
```
xml 实例，依赖多个 bean:
```xml
<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
    <property name="manager" ref="manager" />
</bean>
<bean id="manager" class="ManagerBean" />
<bean id="accountDao" class="x.y.jdbc.JdbcAccountDao" />
```
> **注意：**<br>
依赖定义的 depends-on 依赖关系首先被销毁，在 bean 销毁之前。因此，depends-on 也可以控制关闭顺序。