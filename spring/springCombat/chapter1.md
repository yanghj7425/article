# <center> 第一章 Spring 之旅 </center>

## 简化 Java 开发

- _Spring_ 采采取以下 4 种关键策略：

  1. 基于*POJO*的轻量级和最小侵入性（强迫应用继承它们的类或者实现它们的接口而导致应用与框架绑死）编程；
  2. 通过依赖注入和面向对象的接口实现松耦合；
  3. 基于切面和惯例进行声明式编程；
  4. 通过切面和目标减少样版式代码；

### 激发 POJO 的潜能

- 侵入式：强迫应用继承他们的类或者实现他们的接口从而导致应用于框架绑死。

### 依赖注入

- 耦合具有两面性：
  1. 紧密耦合的代码难以测试、难以复用、难以理解并典型的暴露出"打地鼠"的*Bug*特性；
  2. 完全没有耦合的待么什么也做不了。
  - 通过 **DI**，对象的依赖关系将由系统中负责协调各对象的第三方组件在创建对象的时候设定。

### 应用切片

> DI 能够让互相协作的软件组建保持松散的耦合，而面向切片编程（aspect-oriented programming，AOP）允许你把遍布应用各处的功能分离出来形成可重用的组件。

- 面向切面编程往往被定义为吃屎软件系统实现关注点的分离。
- AOP 能够使服务模块化，并以声明的方式将它们应用到它们需要影响的组件中。

## 容纳你的 Bean

- Spring 容器可归类两种类型：
  1. bean 工厂: 由 `org.springframework.beans.factory.eanFactory` 接口定义，是最简单的容器，提供最基本的 DI 支持。
  2. 应用上下文： 由`org.springframework.context.ApplicationContext` 接口定义，基于 BeanFactory 构建，提供应用框架级别的服务。

### 使用应用上下文

- `AnnotationConfigApplicationContext`：从一个或多个基于 Java 的配置类中加载 Spring 应用上下文。
- `AnnotationConfifWebApplicationContext` : 从一个或多个基于 Java 的配置类中加载 Spring Web 应用上下文。
- `ClassPathXmlApplicationContext` : 从类路径下的一个或者多个 XML 配置文件中加载上下文定义，把应用上下文的定义文件作为资源。
  - 在所有类路径下，包括 JAR 文件路径。
- `FileSystemXmlApplicationContext` : 从文件系统下的一个或者多个 XML 配置文件中加载上上下文定义。
  - 在指定路径下。
- `XmlWebApplicationContext` : 从 Web 应用下的一个或者多个 XML 配置文件中加载上下文的定义。

```java
ApplicatonContext context = new FileSystemXmlApplicationContext("c:knight.xml");
```

### bean 的声明周期

正确的理解 bean 的声明周期非常重要，因为你或许需要利用 Spring 提供的扩展点来自定义 bean 的创建过程。

1. Spring 对 bean 进行实力化。
2. Spring 将值和 bean 的引用注入到 bean 对应的属性中。
3. 如果 bean 实现了 BeanNameAware 接口，Spring 将 bean 的 ID 传递给 setBeanName() 方法。
4. 如果 bean 实现了 BeanFactoryAware 接口，Spring 将调用 setBeanFactory() 方法，将 BeanFacory 实例传入。
5. 如果 bean 实现了 ApplicationContextAware 接口，Spring 将调用 setApplicationContext() 方法，将 bean 所在的应用上下文的引用传送进来。
6. 如果 Bean 实现了 BeanPostProcessor 接口，Spring 将调用他们的 postProcessBeforeInitialization() 方法。
7. 如果 bean 实现了 InitializingBean 接口，Spring 将调用它的 afterPropertiesSet() 方法。类似地，如果 bean 使用 init-method 声明了初始化方法，该方法也会被调用。
8. 如果 Bean 实现了 BeanPostProcessor 接口，Spring 将调用他们的 postProcessAftertInitialization() 方法。
9. 此时，bean 已经准备就绪，可以被应用使用了，他们将一直驻留在应用上下文中，知道该上下文被销毁。
10. 如果 bean 实现了 DisposableBean 接口，Spring 将调用它的 distory() 接口方法。同样，如果 bean 使用的 destotry-method 声明销毁方法，该方法也会被调用
