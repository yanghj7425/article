@(Spring)[Part III. What’s New in Spring Framework 4.x]
#Part III. Core Technologies

这部分的参考文档覆盖的所有技术，都是Spring框架的组成部分。
其中最主要的是Spring框架中的反转控制（IOC）容器。Spring框架的IoC容器的彻底的处理是紧随其后的是Spring的全面覆盖的面向方面编程(aop)技术。Spring框架有它自己的AOP框架，它的概念是容易理解的，他成功的解决了80%的企业级AOP编程的需求。
……
## IoC 容器
### Spring IOC 容器和 Java bean 的介绍
这章覆盖了Spring 框架的 IoC 的实现规则。IoC 因依赖注入儿众所周知。这也是定义它自己依赖的方式的一种方式，就是这样和其他的对象跟它一起工作，只有通过构造函器的参数，参数传递给一个工厂方法或者属性，在它的构造器或者工厂方法返回之后设置一个实例。当它创建类的时候容器注入这些依赖关系。这个过程是反转的基础，因此叫做反转控制（IoC），类它自己控制实例或者它依赖的位置通过直接构造类，或一个机制如服务定位器模式。
org.springframework.beans 和org.springframework.context 包是 Spring 框架Ioc的基础， BeanFactory 接口提供了一个高级的配置机制关于管理任何类型的对象。ApplicationContext 是 BeanFactory 的一个子接口，他更容易的集成了Spring AOP 的特性： 消息资源控制，公共事件和应用层指定的上下文例如：WebApplicationContext 被用在 Web 应用中。
总而言之，BeanFactory 提供了框架的配置和基础的功能，ApplicationContext 添加了更多企业级的特性。ApplicationContext 是一个 BeanFactory 的一个完美超类，它仅仅被用在这章描述 Spring 的 Ioc 容器。更多用 BeanFactory 代替 ApplicationContext 的信息，参考7.16 BeanFactory。

在 Sping 中，对象在应用中主要的形式和被 IOC 容器管理叫做 Bean ，一个 Bean 是一个对象，它是一个实例，一个集合和任何被 Ioc 容器管理的。另外，一个 bean 不过是在你应用中众多对象中的一个。Beans ，他们之间的依赖关系呗映射到一个配置的数据文件里面被容器使用。
##7.2容器总览
org.springframework.context.ApplicationContext 接口代表了 Spring 的 Ioc 容器和负责人实例化、配置、集成上述提到的 bean。容器得到他的实例对于每一个通过读配置数据文件实例、配置、装配的对象。数据配置文件描述在 XML 文件、Java 注解、或者 Java 代码。它允许你表达你的对象然后组成你的应用并且丰富对象之间的依赖接口。
一些实现了 ApplicationContext 接口被 Spring 提供为没有内建的。在单例应用中，通常是创建一个 ClasspathXmlApplication 或者FileSystemXmlApplicationContext 实例。 XML 已经形成传统的格式，为了配置的数据文件你可以指示容器使用Java注解或者代码作为数据元格式通过提供少量的配置文件起声明使能支持这些添加的元数据格式。
在大多数应用的场景，显示的用户代码对于一个实例或者多个实例不是必须要的。例如，在 web 应用场景中，简单的 8 行样本文件 web 描述文件在 web.xml 中已经是足够的。如果你正在用 Spring Tool Suite 这样的开发环境这样的样本配置文件可以可以在点几下鼠标或者少量的按键后容易的被创建。
