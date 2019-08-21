# 1. <center> PartV. Data Access </center> 

这部分的参考文档关注的是数据访问、数据访问层和业务逻辑层或者服务层之间的交互。<br><br>

Spring 的 综合事务管理支持覆盖一些细节，Spring 框架可以接入各种覆盖了数据访问框架和基数的支持。

## 1.1. 目录

* [事务管理](#事务管理)
    * [Spring 事务管理介绍](#spring事务管理介绍)
    * [Spring&nbsp;框架事务模型的高级特性]()
        * [全局事务](#全局事务)
        * [本地事务](#本地事务)
        * [Spring的一致编程模型](#spring的一致编程模型)
    * [明晰&nbsp;Spring&nbsp;框架事务抽象](#明晰spring框架事务抽象)
    * [同步资源事务](#同步资源事务)
        * [高级同步的方法](#高级同步的方法)
        * [低水平的同步方法](#低水平的同步方法)
        * [数据源事务代理意识](#数据源事务代理意识)
    * [声明式事务管理](#声明式事务管理)
## 1.2. 事务管理

### 1.2.1. [Spring事务管理介绍]()

对综合事务管理的支持是使用 Spring 的一个令人激动的理由。Spring 框架多事务管理始终如一的抽象带来了下面的优势：
- 在访问不同的事务 API 的时候使用统一的编程模型，如 Java Transaction(JTA)、JBDBC、Hibernate、Java Persistence API(JPA)、和 Java Data Objects (JDO)。
- 支持声明事务管理。
- 对于编程事务管理与 JTA 等复杂的事物 相比 API 更简单。
- 与 Spring 的数据访问层抽象极好的集成。

下面的部分描述了 Spring 框架的附加事务和技术。(这章也包括讨论最佳实战、应用服务集成、和解决通用问题)

- [Spring 框架的高级事务支持模型]()描述了你为什么应该使用 Spring 框架的抽象事物替代 EJB 容器的事物管理或者选择通过特有的 API 驱动一个本地事务，如：hibernate。
- [明晰 Spring 框架事务抽象]() 轮廓，核心类和抽象怎样从不同种类的数据源配置获得 DataSource。
- [同步事务资源]() 描述了应用代码如何确保资源正确的创建、重用和清理。
- [声明事务管理]() 描述了对于声明事务管理的支持。
- [编程事务管理]() 覆盖支持了编码(这是明确的编码)事务管理。
- [绑定事务事件]() 描述了你应该怎样在一个事务中应用一个事件。

### 1.2.2. [Spring&nbsp;框架事务模型的高级特性](#目录)

传统的，Java EE 开发者对事务管理有 2 个选择：全局事务和本地事务。两者都有深厚的局限性。全局和本地事务管理在下面两部分复习。接下来讨论 Spring 框架的事务管理是怎样解决了全局和本地事务模型的局限性。

#### 1.2.2.1. [全局事务](#全局事务)

全局事务允许你和多个事务型资源一起工作：典型的关系型数据库和消息队列。应用服务器通过 JTA 管理全局事务，这样使用 JTA 的 API 是愚蠢的（部分原因是由于他的异常模式）。因此，JTA 使用的 UserTransaction 一般需要从 JNDI 中获取，意味着你需要为了使用 JTA 而使用 JNDI。明显的，使用全局事务会限制应用代码重用的潜力，正如 JTA 通常是在应用服务器的环境下使用。<br>

在这之前，通过 EJB CMT (Container Managed Transaction) 使用全局事务时比较喜欢的方式。CMT 是声明事务管理的一种形式（作为编程事务的一种区分）。EJB CMT 移除了事务相关的 JDNI 的需求，当然 EJB 本身需要使用 JNDI。它移除了太多根本不需要些的 Java 代码来控制事务。一个严重的缺点是 CMT 依赖 JTA 和应用服务器环境。所以，它仅仅是可用的如果选择一个在 EJB 中实现业务逻辑。EJB 一个不太好的是通常都太大了，以至于不是一个吸引人的选择，尤其是对于声明事务管理面临一个信服的方案时。

#### 1.2.2.2. [本地事务](#目录)

本地事务时一个资源特性，例如一个事务和 JDBC 链接关联。本地事务可能更加容易使用，但是有一个严重的劣势：不能跨越多个事务性资源工作。比如：编码管理的事务使用了 JDBC 链接的时候不能和全局的 JTA 事务一起工作。因为应用服务器没有调用事务管理，它不能帮助确保正确的跨越多个资源（对于大多数的应用使用单一的事务资源时不值得的）。本地事务的另一个缺陷是编程模型由侵入性攻击。

#### 1.2.2.3. [Spring的一致编程模型](#目录)

Spring 解决了全局和本地事务的不利之处。它使在任何应用开发使用一致的编程模型。你写一次代码，它可以从不同的事务管理策略在不同的环境中受益。Spring 框架提供了声明事务管理和编程事务管理。大部分的使用者喜欢声明事务管理，这里也是在大多数情况下推荐使用的。<br><br>

用编程事务管理，开发者和 Spring 框架的事务抽象一起工作，这样可以在任何事务基础之运行。首选声明事务模型，开发者代表性的写一点儿或者不用写代码关联事务管理。因此不依赖 Spring 框架的事务 API 或者任何事务的 API。

> 为了事务管理你需要一个应用服务器吗 ?

Spring 框架的事务管理支持改变传统的规则当一起企业级的 Java 应用开发去要一个应用服务器的时候。<br><br>

特别的，通过 EJB 你不需要一个应用服务器对于声明事务管理。实际上，如果你的应用服务器有强大的 JTA 功能，你可以决定 Spring 框架的声明事务管理在更有利的和更有生产性的编程模型，如 EJB CMT 之后。<br><br>

通常，如果你的应用需要处理事务跨越多个资源你就需要一个有 JTA 功能的应用服务器，这个对大部分应用都不是必须的。许多高端的应用使用单个的、高度可伸缩的数据库替代。单例的事务管理，如：Atomikes Transactions 和 JOTM 的其它选择。当然，你可能需要一个应用服务的，比如: JAVA Message Service(JMS)、Java EE COntecot Architectrue( JCA )。<br> <br>

Spring 的框架给你选择你的应用何时完全加载到你的应用服务器。只能选择使用 EJB CMT 或 JTA 编码实现类似于 JDBC 链接型的本地事务已经是过去式了，并且如果想要代码运行在全局、连接者管理的事务将要面临巨大的返工。使用 Spring 框架，只需要统一的 bean 定义在你的配置文件，而不是你的代码需要修改。

### 1.2.3. [明晰Spring框架事务抽象](#目录)

Spring 事务抽象的关键是：事务策略（transaction strategy）的概念。transaction strategy 被定义在 `org.springframework.transaction.PlatformTransactionManager` 接口：
```java
public interface PlatformTransactionManager {
    TransactionStatus getTransaction(
    TransactionDefinition definition) throws TransactionException;
    void commit(TransactionStatus status) throws TransactionException;
    void rollback(TransactionStatus status) throws TransactionException;
}

```
这是一个主要的服务提供者接口（SPI），但是它在你的应用代码里面可以通过编程的方式实现。因为 PlatformTransactionManager 是一个接口，必要的时候它很容易被欺骗或者剔除。它没有依赖 JNDI 这样的查找策略。PlatformTransactionManager 的实现类就像在 Spring 框架的 IOC 容器中定义其它的对象一样。这样的好处是使 Spring 框架事务抽象比较值得，甚至和你使用 JTA 一起工作的时候。事务型的编码比直接使用 JTA 的方式更容易测试。<br>

与 Spring 的哲学一样，TransactionException 可以被 PlatformTransactionManager 的任何方法抛出，这是一个非检查异常（是的，继承自 java.lang.RuntimeException 类）。事务基础的故障总是致命的。在极少数的情况下，应用代码实际上可以从失败的事务中恢复过来，应用开发者仍然可以选择捕获并且处理 TransactionException。特别的指出开发者不是强迫这样做的。<br>
getTransaction(...) 方法返回一个 TransactionStatus 对象，它需要一个 TransactionDefinition 参数。返回的 TransactionStatus 可能待代表一个新的状态，如果匹配的事务在当前的调用栈里面就可以代表一个已经存在的事务。后面一种情况的含义是：与 Java EE 事务上下文一样，一个 TransactionStatus 被分配给一个相关的线程执行。<br><br>

**TransactionDefinition 接口定义**<br>
- 独立性 (Isolation): 从其它工作的事务中分离出来的事务的程度。例如, 这个事务是否可以看见其它事务中未提交的写入数据吗？
- 传递性 (Propagation): 典型的，左右在一个事务里面执行的代码作用域都那个事务里面。然而，当事务的上下文已经存在时，你可以选择在事件里面指定执行方法的行为。例如，代码可以继续运行在已经存在的事物(一般情况下)，或者存在的事务被挂起，新事务被创建。Spring 提供了从 EJB CMT 中所有的熟悉的事务传递选项。
- 超时时间(Timeout)：在时间截至之前这个事务运行多长时间，并且自动回滚通过底层的事务基础。
- 只读状态(Read-only Status):当你的代码只是读取并不会修改数据的时候就使用只读事务。只读事务在一些情况下时非常有用的优化，例如，当你使用 Hibernate。

这些设置反应了标准事务的概念。如果有必要，参考讨论事务独立水平和其它核心事务概念的资料。理解这些概念是使用 Spring 框架或者任何事物管理解决方案的基础。<br>

TransactionStatus 接口为编码控制事务的执行和查询事务状态提供了一个简单的方式。这些概念应该是熟悉的，他们是共同的事务 API：
```java
public interface TransactionStatus extends SavepointManager {
    boolean isNewTransaction();
    boolean hasSavepoint();
    void setRollbackOnly();
    boolean isRollbackOnly();
    void flush();
    boolean isCompleted();
}

```
无论选择 Spring 的声明事务还是编程事务，正确的定义 PlatformTransactionManager 是基本的。你通常可以通过依赖注入来定义这个实现。<br>

PlatformTransactionManager 的实现通常需要一些他们工作环境的知识比如：JDBC、JTA、Hibernate 等等。下面的例子展示了你开业定义一个本地 PlatformTransactionManager 实现（这个例子和普通的 JDBC 工作）。<br>

定义一个 JDBC 数据源

```xml
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="${jdbc.driverClassName}" />
    <property name="url" value="${jdbc.url}" />
    <property name="username" value="${jdbc.username}" />
    <property name="password" value="${jdbc.password}" />
</bean>

```

相关的 PlatformTransactionManager 的定义将参考 DataSource 的定义。它将像这样：

```xml
<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>
```

如果你在是使用 Java EE 容器中使用 JTA，你是用的数据源从 JNDI 获取的，与 Spring 的 JtaTransactionManager 合作。这样看起来像 JTA　JNDI　的查找版本：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:jee="http://www.springframework.org/schema/jee"
    xsi:schemaLocation="
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/jee
    http://www.springframework.org/schema/jee/spring-jee.xsd">
        <jee:jndi-lookup id="dataSource" jndi-name="jdbc/jpetstore"/>
        <bean id="txManager" class="org.springframework.transaction.jta.JtaTransactionManager" />
    <!-- other <bean/> definitions here -->
</beans>

```

JtaTransactionManager 不需要知道 DataSource，或者其他特殊的数据源，因为它是用的容器的全局事务管理机制。

> **注意：**<br> 
上面定义的数据源 dataSources 使用的是 jee 命名空间 `<jndi-lookup/>` 标签。

你也可以容易的是用 Hibernate 的本地事务，正如下面的例子所示。在这种情况下，你需要定义一个 Hibernate LocalSessionFactoryBean，你的应用代码将要获得这个 Hibernate session 实例。<br>

DataSource 的定义和之前展示的 local JDBC 例子非常相似。
> **注意：**<br>
如果数据源，通过 JNDI 查找，使用了任何一个非 JTA 事务管理，并由 Java EE 容器管理。那么它将是非事务性的，因为 Spring 框架而不是 Java EE 容器将要管理事务。

txManager bean 是 HibernateTransactionManager 类型的一种情形。同样的方式 DataSourceTransactionManager 需要参考数据源，HibernateTransactionManager 需要参考 SessionFactory。<br>

如果你使用 Hibernate 和 Java EE 容器管理 JTA 事务，那么你因该简单的和之前的 JDBC 的例子一样使用 JtaTransactionManager。

>**注意:**<br>
如果你使用 JTA，那么你的事务管理将看起来一样，尽管你是用的数据访问技术可能是 JDBC、Hibernate JPA 或者其他支持的技术。这是实际上是因为 JTA 的事务是一个全局的事务，它可以支持任何的事务资源。<br>

在这所有的情形下，应用代码不需要改变。你可以改变事务的管理方式仅仅通过改变配置文件，甚至从本地事务改变到全局事务，反之亦然。

### 1.2.4. [同步资源事务](#目录)
你现在因该清楚你怎样创建不同的事务管理，他们与资源之间的联系和需要同步到事务（例如： DataSourcesTransactionManager 到一个 JDBC DataSource、Hibernate TransactionManager 到 Hibernate SessionFactory 等等）。这部分描述应用代码怎样直接或间接的是用持久化的 API 比如 JDBC、Hibernate 或者 JDO 来确保资源的创建、重用、清理。这部分通过 PlatformTransactionManager 也讨论事务同步是怎样被触发的。

#### 1.2.4.1. [高级同步的方法](#目录)
推荐的方法是使用 Spring 最高级别基于模板的持久化 API 或使用具有事务感知工厂的本地 ORM API 或代理管理本地资源工厂。这些事务敏感解决内部处理资源的创建、重用、清理、选择资源事务同步和异常映射。因此用户数据访问代码没有这些任务的地址，但是可以只专注于没有模板的持久化逻辑。一般的，你是用本地的 ORM API或者通过 JdbcTemplate 采用一个 template 接近 JDBC 访问。这些解决方案的细节是后面的章节的内容。

#### 1.2.4.2. [低水平的同步方法](#目录)

类级别的，比如：DataSourceUtils（对于 JDBC）、EntityManagerFactoryUtils（对于 JPA）、SessionFactory（对于 Hibernate）、PersistenceManagerFactoryUtils（对于 JDO）等等，都是现在已存在的低水平的。当你想要应用代码直接用本地持久化 API 处理资源，你是用这些类来恰当的确保本身已经被 Spring 框架管理的实例已经获得。事务被同步，出现在进程里的异常被恰当的映射到一致的 API 里。<br><br>

比如，在是用 JDBC 的情况下，替代传统的 JDBC 在 DataSource 上调用 getConnection() 的方法。相反，像下面这样使用 `org.springframework.jdbc.datasource.DataSourceUtils` 类:<br>
`Connection conn = DataSourceUtils.getConnection(dataSource);` <br>

如果存在的事务已经有一个连接同步（处于连接状态），这个实例将被返回。否则，这个方法调用触发器创建一个新的连接，这会（随意的）同步任何已经存在的事务，并且使在同一个事务里面对于子序列重用成为可能。已经提到的，任何的 SQLException 被包装进 Spring 框架的 CannotGetJdbcConnectionException，一个 Spring 框架级别的非检查异常 DataAccessExceptions。这个方法提供给你跟多的信息然后你可以更加容易的从 SQLException 中获得异常信息，并确保
跨数据库移植的可能，甚至跨不同的存储技术。<br>

这个方法也工作在没有 Spring 事务管理的时候（事务同步是可选的），所以你可以使用它无论是否使用了 Spring 的事务管理。<br>

当然，一档你是用了 Spring 的 JDBC、JPA 或 hibernate 支持，你通常将不会使用 DataSourceUtils 或者其他的帮助类，因为你将更开心的工作通过 Spring 的抽象，而不是直接使用关系型的 API。比如，如果你使用 Spring JdbcTemplate 或 jdbc.object 包是为了你使用 JDBC 的精简，正确的连接检索出现在幕后的场景，你将不需要写任何特殊的代码。

#### 1.2.4.3. [数据源事务代理意识](#目录)
TransactionAwareDataSourceProxy 类是最低水平的存在。这是一个对目标 DataSource 的代理，它封装了目标 DataSource 为了添加 Spring 的事务管理。在这方面，和一个由 JavaEE 服务提供的事务性的 JNDI DataSource 非常相似。 <br>

使用这个类几乎从来是不需要的和不满意的，除非当代码必须被调用并且通过标准的 JDBC 数据源接口来实现。在这样的情况下，这个代码是可用的，但是参与 Spring 管理的事务。它更喜欢你写新的代码通过上面提到的高水平的抽象。

### 1.2.5. [声明式事务管理](#目录)
>**注意：**<br>
大多数 Spring 框架的使用者选择声名事务管理。这样的选择对代码的影响最小，今后最符合的观点就是非侵入性的轻量级容器。<br>
Spring 框架的声名事务管理和 EJB CMT 很相似你可以指定事务行为下降到每一个独立的方法。这样做是可以的，如果需要可以在事务的上下文中调用 setRollbackOnly() 方法。两种不同类型的事务管理类型：
 - 和 EJB CMT 不同的，Spring 框架的声名事务管理和 JTA 有关系可以工作在任何环境。它可以和 JTA 事务或者使用 JBDC、JPA、Hibernate、JDO 的本地事务一起工作，只需要调整一下配置文件。<br><br>
 - 你可以应用 Spring 框架的声名事务管理到任何类，不只是 EJB 这样特指的类。
 - Spring 框架提供了声名式回滚规则，这是 EJB 没有的。在编程和声名式的支持中都提供了回滚规则。
 - Spring 框架允许你定制通过 AOP 定制事务的行为。比如：你可以在事务回滚之后插入一个定制的行为。你也可以添加任意的 advice，还有事务 advice。EJB CMT，你不能用 setRollbackOnly() 来影响容器的事务管理。
 - 正如做高端应用服务器，Spring 框架不支持远程调用时的事务传播。如果你需要这个特性，推荐你使用 EJB。然而，使用这个特性之前请仔细考虑，因为通常我们不想要事务跨过远程调用。

 > TransactionProxyFactoryBean 用在哪儿？<br>
 声名事务配置在 Spring2.0 及以上的版本大不同与之前版本的 Spring。主要的区别是不再需要配置 TransactionProxyFactoryBean。<br>
 Spring2.0 之前的配置风格仍然 100% 有效，考虑新的 `<tx:tags/>` 为你简单的定义 TransactionProxyFactoryBean。

 回滚规则的概念是重要的：他们使你指定哪一种 exception（和 throwables）应该触发自动回滚。你可以在配置文件里面指定这些声明而不是在 java 代码里面。所以虽然你仍然可以在 TransactionStatus 上调用 setRollbackOnly() 方法来回滚当前事物，大多数的时候你可以指定一个 MyApplicationException 这样必须回滚的规则。这样选择的的优势是：业务对象不应该添加到基础事务结构里面。比如，比较有代表性的是不需要导入 Spring 事务 API 或 Spring 其他API。<br><br>

虽然 EJB 容器默认在一个系统异常（通常是一个运行时异常）自动回滚事务，EJB CMT 不会在一个应用的异常（一个 被检查异常而不是 java.rmi.RemoteException）。但是 Spring 对声明事务管理默认遵守 EJB 的习俗（只有在非检查异常时才会自动回滚），这个特性对定制一个行为非常有用。

#### 1.2.5.1. [了解Spring框架声明事务的实现](#目录)

不要满足于告诉你简单的在你的 classes 上使用 @Transactional 注解，添加一个 @EnableTransactionManagement 到你的配置中，然后你希望明白它整体是怎么工作的。这部分将要解释 Spring 框架如果发生于事务有关系的事件时声明事务内部的工作问题。<br><br>

要掌握 Spring 框架声明事务支持最重要的概念是通过 AOP 代理启用，然后事务的 advice 是通过 元数据（当前的 XML 或者 注解）驱动的。AOP 和事务元数据的整合产生了一个 AOP 代理，使用 TransactionInterceptor 和一个合适的 PlatformTransactionManager 实现类链接来驱动事务围绕方法调用。<br><br>

#### 1.2.5.2. [声明式事务实现的列子]

考虑下面的接口，它是一个绑定的实现。这个例子使用 Foo 和 Bar 类作为占位符为了你可以关注与事务的使用没有关注在个别的 domain 模型。这个例子的目的，实际上 DefaultFooServive 类在每一个实现体的方法的每一个方法抛出一个 UnSupportOperationException 实例是非常好的。它允许你在响应一个 UnsupportedOperationException 时查看事务的创建和回滚。

```java
// the service interface that we want to make transactional
package x.y.service;
public interface FooService {
    Foo getFoo(String fooName);
    Foo getFoo(String fooName, String barName);
    void insertFoo(Foo foo);
    void updateFoo(Foo foo);
}

```


```java
// an implementation of the above interface
package x.y.service;
public class DefaultFooService implements FooService {
    public Foo getFoo(String fooName) {
        throw new UnsupportedOperationException();
    }
    public Foo getFoo(String fooName, String barName) {
        throw new UnsupportedOperationException();
    }
    public void insertFoo(Foo foo) {
        throw new UnsupportedOperationException();
    }
    public void updateFoo(Foo foo) {
        throw new UnsupportedOperationException();
    }
}


```

假设 FooService 接口开始的两个方法，getFoo(String) 和 getFoo(String, String), 必须以 read-only 的语义执行事务上下文；其他的方法 insertFoo(Foo) 和 updateFoo(Foo)，必须以 read-write 的语义执行事务。

```xml
<!-- from the file 'context.xml' -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:aop="http://www.springframework.org/schema/aop" 
    xmlns:tx="http://www.springframework.org/schema/tx" xsi:schemaLocation="
http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/tx
http://www.springframework.org/schema/tx/spring-tx.xsd
http://www.springframework.org/schema/aop
http://www.springframework.org/schema/aop/spring-aop.xsd">

    <aop:aspectj-autoproxy />

    <!-- this is the service object that we want to make transactional -->
    <bean id="fooService" class="x.y.service.DefaultFooService"/>
    <!-- the transactional advice (what 'happens'; see the <aop:advisor/> bean below) -->
    <tx:advice id="txAdvice" transaction-manager="txManager">
        <!-- the transactional semantics... -->
        <tx:attributes>
            <!-- all methods starting with 'get' are read-only -->
            <tx:method name="get*" read-only="true"/>
            <!-- other methods use the default transaction settings (see below) -->
            <tx:method name="*"/>
        </tx:attributes>
    </tx:advice>
    <!-- ensure that the above transactional advice runs for any execution
of an operation defined by the FooService interface -->
    <aop:config>
        <aop:pointcut id="fooServiceOperation" expression="execution(* x.y.service.FooService.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="fooServiceOperation"/>
    </aop:config>
    <!-- don't forget the DataSource -->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"/>
        <property name="url" value="jdbc:oracle:thin:@rj-t42:1521:elvis"/>
        <property name="username" value="scott"/>
        <property name="password" value="tiger"/>
    </bean>
    <!-- similarly, don't forget the PlatformTransactionManager -->
    <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!-- other <bean/> definitions here -->
</beans>

```

在配置之前检查。你想要创建一个服务对象，事务性的 fooService bean。事务的语义被应用在封闭的 `<tx:advice/>` 定义种。`<tx:advice/>` 定义读作“…… 左右以 `get` 开头的方法执行的时候都默认只读的事务，所有其他的方法执行的时候默认拥有的事务配置(read-write)” 。`<tx:adivice/>` 标签的 transaction-manager 属性是设置 PlatformTransactionManager 的名称，这将驱动事务，在这个例子中是 `txManager` bean。<br><br>

> 提示：<br>
如果你想要有联系的 PlatformTransactionManager 的 name 是 transactionManager 你可以在一个事务 advice (`<tx:advice/>`) 中混合 transaction-mananger 属性。如果不是，你必须使用 transaction-manager 属性像之前的例子一样显示指定。<br><br>

`<aop:config/>` 的定义确保了 txAdvice 定义的 bean 在程序中合适的点被执行（fooServiceOperation）。首先，你定义一个切点匹配定义在 FooService 接口中的操作。然后，你用 advisor 分配 txAdivce 切点。结果表明在执行 fooServiceOperation 的时候，被 txAdvice 定义的 advice 将要被执行。<br><br>

用 `<aop:pointcut/>` 元素定义一个 AspectJ 切点表达式。详情参看 11 章。<br>

通常事务化整个 service 层是必要的。这样做最简单的方式是改变切点表达式去匹配你 Service 层的任何操作。比如：
```xml
<aop:config>
    <aop:pointcut id="fooServiceMethods" expression="execution(* x.y.service.*.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="fooServiceMethods"/>
</aop:config>
```

现在我们来分析配置文件，你也许也这样问你自己：“这些配置文件实际上做了什么？”<br>

上面的配置文件将从 fooService 的定义创建事务代理对象。代理将要被配置为事务性的 advice，为了当一个合适的方法在代理上被调用的时候，事务的启动、挂起、标记为只读等等，依赖于事务的配置将和方法关联。考虑下面的程序来测试驱动上面的配置。

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context.xml"})
@EnableAspectJAutoProxy
public class AppTest   {
	
	@Autowired
	private FooService fooService;
	
	@Test
	public void  test(){
		if(fooService != null){
			fooService.insertFoo(new Foo());
		}
	}
}

```


#### 1.2.5.3. 回滚一个声名式事务

前面的部分指明了怎样在你的应用中指定一个事务、典型的 Service 层。这部分展示给你怎样用一种简单的方式展现事务的回滚。<br>

关于事务的回滚，再通知节点 `<tx:advice/>`  和属性节点 `<tx:attributes/>` 内部通过子节点 `<tx:method/>` 设置。
- `<tx:method/>` 节点可以设置以下属性：
    - name：标识响应事务的方法。eg: get* ，表示以 get 开头的方法都响应。
    - read-only：事务是否为只读，默认为 false
    - propagation：传递性
    - isolation：独立性
    - no-rollback-for：对于某种异常不回滚
    - rollback-for：指定某种异常发生时，回滚
    - timeout：超时时间

Spring 框架默认对所有非检查异常（RuntimeException）执行回滚操作，对所有检查异常（Exception）不执行回滚操作。<br><br>

关于异常的回滚，也可以在 `try-catch` 子句处理。
```java

public void transactionRollBack() {
		Assert.notNull(fooService, "fooService can`t be  null");
		try{
			fooService.insertFoo(new Foo());
		}catch(UnsupportedOperationException e){
			TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();
		}
}

```

在大部分情况下，我们的 Service 层可能不会在同一个包中，而且可能需要相同的方法响应不同的异常类型，这时候就可以在 `<aop:config/>` 节点内定义多个切点 `<aop:pointcut/>` 和通知 `<aop:advisor/>`，来映射到不同的 `<tx:advice/>` 以满足需求。

#### 1.2.5.4. [使用@Transactional注解](#目录)

在有很多个 Service 的情况下可以使用 @Transactional 注解和注解驱动配置来简化配置文件（`<aop:config>` 配置）。
```xml
<tx:annotation-driven transaction-manager="txManager"/>
```

如果 PlatformTransactionManager 的 bean id 为 transactionManager，`transaction-manager` 属性就可以省略，否则需要显示的指定 transaction-manager 的值。<br>
> 如果使用 javaConfig 的方法，只需要在 @Configuration 注解的类上简单的添加 @EnableTransactionManagement 注解。<br><br>

- `<tx:annotation-driven/>` 节点可以设置一下属性
    - transaction-manager : 上面介绍过了
    - proxy-target-class: 是否用类代理，默认 false
    - mode：代理模式，默认 proxy

@Transactional 可以用在接口和方法上，或者 public 方法上。如果用在非 public 方法上，那要使用 基于 aspectj 的代理模式（织入）。<br><br>

在方法上使用的注解，会覆盖在类或者接口上已经配置过的注解信息。如：事务只读状态、传递性等。


#### 1.2.5.5. [编程式事务](#目录)

编程事务，文档上说的比较少。可以参考其他博客。
 
介绍的就不说了，先看代码（其实也是官网的代码，我只是自己跑了一遍）

```java
public class SimpleService {

	private final TransactionTemplate transactionTemplate;

	private static final Logger logger  = LoggerFactory.getLogger(SimpleService.class);

	public SimpleService(PlatformTransactionManager transactionManager) {
		Assert.notNull(transactionManager, "transactionManager can`t be null");
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
	
	public Object someServiceMethod() {
		return transactionTemplate.execute(new TransactionCallback<Object>() {
			public Object doInTransaction(TransactionStatus status) {
				updateOperation1();
				return resultOfUpdateOperation2();
			}
		});
	}
	
	private Object resultOfUpdateOperation2() {
		logger.debug(getClass().getSimpleName() + " resultOfUpdateOperation2");
		return "resultOfUpdateOperation2";
	}

	private void updateOperation1() {
		logger.debug(getClass().getSimpleName() + " updateOperation1");
	}

}

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:context.xml" })
public class FooServiceTest {

	private SimpleService simpleService;

	@Test
	public void programmaticTransactionTest() {
		simpleService.someServiceMethod();
	}
｝
```

添加配置文件：
```xml
	<bean id="simpleService" class="com.yhj.service.impl.SimpleService">
		<constructor-arg name="transactionManager" ref="txManager"></constructor-arg>
		<!-- <property name="transactionTemplate" ref="txManager"></property> -->
	</bean>
```

这里注入的时候一直给我一个 NPE，然后重启 idea 就好了真是那句话：什么也不管丢给你一堆 NPE。 


哎，今天第二次打这部分。第一次打完 mv 写成 rm 了，好痛苦……