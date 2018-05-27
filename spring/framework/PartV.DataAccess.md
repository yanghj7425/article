# <center> PartV. Data Access </center> 

这部分的参考文档关注的是数据访问、数据访问层和业务逻辑层或者服务层之间的交互。<br><br>

Spring 的 综合事务管理支持覆盖一些细节，Spring 框架可以接入各种覆盖了数据访问框架和基数的支持。

## 目录

* [事务管理](#事务管理)
    * [Spring 事务管理介绍](#spring事务管理介绍)
    * [Spring&nbsp;框架事务模型的高级特性]()
        * [全局事务](#全局事务)
        * [本地事务](#本地事务)
        * [Spring的一致编程模型](#spring的一致编程模型)
    * [明晰&nbsp;Spring&nbsp;框架事务抽象](#明晰spring框架事务抽象)
    * [同步资源事务](#同步资源事务)
## 事务管理

### [Spring事务管理介绍]()

综合事务管理支持是使用 Spring 的一个令人激动的理由。Spring 框架多事务管理始终如一的抽象带来了下面的优势：
- 在访问不同的事务 API 的时候使用统一的编程模型，如 Java Transaction(JTA)、JBDBC、Hibernate、Java Persistence API(JPA)、和 Java Data Objects (JDO)。
- 支持声明事务管理。
- 对于编程事务管理与 JTA 等复杂的事物 相比 API 更简单。
- 与 Spring 的数据访问层抽象极好的集成。

下面的部分描述了 Spring 框架的附加事务和技术。(这章也包括讨论最佳实战、应用服务集成、和解决通用问题)

- [Spring 框架的高级事务支持模型]()描述了你为什么应该使用 Spring 框架的抽象事物替代 EJB 容器的事物管理或者选择通过特有的 API 驱动一个本地事务，如：hibernate。
- [明晰 Spring 框架事务抽象]() 轮廓，核心类和抽象怎样从不同种类的数据源配置获得 DataSource。
- [同步事务资源]()描述了应用代码如何确保资源正确的创建、重用和清理。
- [声明事务管理]()描述了对于声明事务管理的支持。
- [编程事务管理]() 覆盖支持了编码(这是明确的编码)事务管理。
- [绑定事务事件]() 描述了你应该怎样在一个事务中应用一个事件。

### [Spring&nbsp;框架事务模型的高级特性](#目录)

传统的，Java EE 开发者对事务管理有 2 个选择：全局事务和本地事务。两者都有深厚的局限性。全局和本地事务管理在下面两部分复习。接下来讨论 Spring 框架的事务管理是怎样解决了全局和本地事务模型的局限性。

#### [全局事务](#全局事务)

全局事务允许你和多个事务型资源一起工作：典型的关系型数据库和消息队列。应用服务器通过 JTA 管理全局事务，这样使用 JTA 的 API 是愚蠢的 ( 部分原因是由于他的异常模式)。因此，JTA 使用的 UserTransaction 一般需要从 JNDI 中获取，意味着你需要为了使用 JTA 而使用 JNDI。明显的，使用全局事务会限制应用代码重用的潜力，正如 JTA 通常是在应用服务器的环境下使用。<br>

在这之前，通过 EJB CMT (Container Managed Transaction)使用全局事务时比较喜欢的方式。CMT　是声明事务管理的一种形式(作为编程事务的一种区分)。EJB CMT 移除了事务相关的 JDNI 的需求，当然 EJB 本身需要使用 JNDI。它移除了太多根本不需要些的 Java 代码来控制事务。一个严重的缺点是 CMT 依赖 JTA 和应用服务器环境。所以，它仅仅是可用的如果选择一个在 EJB 中实现业务逻辑。EJB 一个消极的是通常都太大了，以至于不是一个吸引人的选择，尤其是对于声明事务管理面临一个信服的方案时。

#### [本地事务](#目录)

本地事务时一个资源特性，例如一个事务和 JDBC 链接关联。本地事务可能更加容易使用，但是有一个严重的劣势： 不能跨越多个事务性资源工作。比如：编码管理的事务使用了 JDBC 链接的时候不能和全局的 JTA 事务一起工作。因为应用服务器没有调用事务管理，它不能帮助确保正确的跨越多个资源( 对于大多数的应用使用单一的事务资源时不值得的)。本地事务的另一个缺陷是编程模型由侵入性攻击。

#### [Spring的一致编程模型](#目录)

Spring 解决了全局和本地事务的不利之处。它使在任何应用开发使用一致的编程模型。你写一次代码，它可以从不同的事务管理策略在不同的环境中受益。Spring 框架提供了声明事务管理和编程事务管理。大部分的使用者喜欢声明事务管理，这里也是在大多数情况下推荐使用的。<br><br>

用编程事务管理，开发者和 Spring 框架的事务抽象一起工作，这样可以在任何事务基础之运行。首选声明事务模型，开发者代表性的写一点儿或者不用写代码关联事务管理。因此不依赖 Spring 框架的事务 API 或者任何事务的 API。

> 为了事务管理你需要一个应用服务器吗 ?

Spring 框架的事务管理支持改变传统的规则当一起企业级的 Java 应用开发去要一个应用服务器的时候。<br><br>

特别的，通过 EJB　你不需要一个应用服务器对于声明事务管理。实际上，富国你的应用服务器有强大的 JTA 功能，你可以决定 Spring 框架的声明事务管理在更有利的和更有生产性的编程模型，如 EJB CMT  之后。<br><br>

通常，如果你的应用需要处理事务跨越多个资源你就需要一个有 JTA 功能的应用服务器，这个对大部分应用都不是必须的。许多高端的应用使用单个的、高度可伸缩的数据库替代。单例的事务管理，如： Atomikes Transactions 和 JOTM 的其它选择。当然，你可能需要一个应用服务的 比如 : JAVA Message Service(JMS)、Java EE COntecot Architectrue( JCA )。<br> <br>

Spring 的框架给你选择你的应用何时完全加载到你的应用服务器。只能选择使用 EJB CMT 或 JTA 编码实现类似于 JDBC 链接型的本地事务已经是过去式了，并且如果想要代码运行在全局、连接者管理的事务将要面临巨大的返工。使用 Spring 框架，只需要统一的 bean 定义在你的配置文件，而不是你的代码需要修改。

### [明晰Spring框架事务抽象](#目录)

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
- 只读状态(Read-only Status):当你的代码只是读取并不会修改数据的时候就就使用只读事务。只读事务在一些情况下时非常有用的优化，例如，当你使用 Hibernate。

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

PlatformTransactionManager 的实现通常需要一些他们工作环境的知识比如：JDBC、JTA、Hibernate 等等。下面的例子展示了你开业定义一个 本地 PlatformTransactionManager 实现（这个例子和普通的 JDBC 工作）。<br>

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

### [同步资源事务](#目录)
你现在因该清楚你怎样创建不同的事务管理，他们与资源之间的联系和需要同步到事务（例如： DataSourcesTransactionManager 到一个 JDBC DataSource、Hibernate TransactionManager 到 Hibernate SessionFactory 等等）。这部分描述应用代码怎样直接或间接的是用持久化的 API 比如 JDBC、Hibernate 或者 JDO 来确保资源的创建、重用、清理。这部分通过 PlatformTransactionManager 也讨论事务同步是怎样被触发的。

#### [高级同步的方法](#目录)
推荐的方法是使用 Spring 最高级别基于模板的持久化 API 或使用具有事务感知工厂的本地 ORM API 或 代理管理本地资源工厂。这些事务敏感解决内部处理资源的创建、重用、清理、选择资源事务同步和异常映射。因此用户数据访问代码没有这些任务的地址，但是可以只专注于没有模板的持久化逻辑。一帮的，你是用本地的 ORM API或者通过 JdbcTemplate 采用一个 template 接近 JDBC 访问。这些解决方案的细节是后面的章节的内容

#### [低水平的同步方法](#目录)