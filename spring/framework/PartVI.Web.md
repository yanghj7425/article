# <center>Part VI. The Web</center>
这部分的参考手册覆盖Spring框架支持的表示层(特殊的基于Web的表示层`presentation tier`) 包括在web 引用中支持 `WebSocket-sytle` 消息。<br>
Spring框架有他自己的Web框架，`Spring Web MVC`,第一章里面覆盖了`Spring MVC`的相关知识，随后的章节关注`Spring` 框架跟其它Web记住的集成，接下来是`Spring`框架的 `MVC protlet` 框架，然后本节在 26 章综合的总结了`Spring` 框架对 `WebSocket`的支持。

## 22. Web MVC 框架
### 22.1 Spring MVC 框架的介绍
`Spring` 的 Web 模型-视图-控制 框架是围绕着 `DispacherServlet`设计的。它分发`requests`到 `handlers`，可以配置`handler`的映射，视图解析，定位，时区，主题也支持文件的上传下载。默认的 handler 时基于`@Controller`和`@RequestMapping`注解，提供了一个广泛灵活的处理方法。从引入`Spring 3.0` 开始，`@Controller`机制(mechanism)也允许你创建一个`RESTful`网站和应用，通过`@PathVariable`注解和其他的特性。<br>

>“面向扩展开放……” Spring Web MVC 设计的原则的关键和在一个普通的Spring 应用中一个关键的原则时 “面向扩展开放，面向修改封闭”。<br>
Spring MVC 中一些在核心类里面的方法被标记为 `final`的。作为一个开发者你的不能复写这些方法来支持你自己的行为。这不是武断的做法，但是特别要记住这个原则。<br>
 ……<br>
 你不能添加 `advice`到一个 `final`方法，当你使用 Spring MVC 的时候。 例如, 你不能添加 `advice` 给`AbstractController.setSynchronizeOnSession()` 方法。请参考 “Understanding AOP proxies” 学习跟多关于AOP代理的信息和为什么不能添加一个`advice`给一个 final 方法。<br>


在 `Spring MVC`中你可以使用任何的对象作为一个指令或者`form-backing`(以表格为后盾的对象)对象。你不需要实现框架的特性接口或者继承一个类。Sping 的数据绑定时高度灵活的： 例如，它将类型不匹配作为一个效验错误，会被应用评估，而不是不是作为系统错误。因此你不需要简单的复制你业务对象的属性，无类型的 String 在你的表单对象简单的处理为无效的提交，或者转变String的属性。相反的，通常最好是直接绑定业务对象。<br>
Spring 的视图解析是极度灵活的， 一个`Controller`对于一个`Map`模型的数据和选择视图名称是经典可靠的，但是它也可以直接写一个`response`流来完成请求。视图名称解析是一个高度可配置的通过扩展名或者接受会话头的内、通过Bean 名称、一个属性文件或者甚至一个经典的`ViewResolver`来实现。模型（MVC 中的 M）是一个 Map 接口，这样允许完全抽象的视图技术。你可以直接和基于模板渲染的技术集成，例如： JSP ，Velocity 和Freemarker，或者直接产生XML， JSON ，Atom和许多其他类型的内容。模型 `Map` 简单的转变到一个适合的格式，例如 JSP 的 `request attributes`，一个`Velocity`模板模型。

**Spring MVC 的特性**
> *Spring Web Flow*<br>
Spring web Flow（SWF）目标是最好的解决 Web 应用程序管理。<br>
SWF 集成了已经存在的框架 Spring MVC 和 JSF，在Servlet 环境和Portlet环境。如果你有一个进程它从一个会话模型转变为一个与纯求情模型想法中受益，那么SWF 可能解决这个问题。<br>
SWF 允许你捕获逻辑页面流作为它自己的模块，在不同的情况下这回高度可重用。同样的，适合构建网页模块，这将指引用户通过控制器导航，驱动业务进程。

Spring的 Web 模块 包括许多唯一 web 支持的特性：
- 角色分明。每一个角色——控制器，效验器，命令对象，表当对象，模板对象，`DispatcherServlet`，`handler mapping`,视图解析器等等。被一系列的序列化对象实现。
- `javaBeans` 作为框架和应用类直接强有力且直接的配置。这种配功能包括容易的引用上下文，例如 从 web 控制器到业务对象和验证器。
- 可适配的，非倾入式的和灵活的。你需要定义任意一个控制器的方法签名，尽可能的是员工众多参数注解中的一个（例如 ：`@RequestParam, @RequestHeader, @PathVariable`和其他的）作为一个方案。
- 业务代码的重用，不需要复制。使用已经存在的业务对象作为命令或者表当对象而不是镜像他们来扩展框架基础类的特性。
- 可定制的绑定和验证，类型不匹配作为一个应用水平的效验错误，一直是一个讨厌的值，当地日期和数字额绑定，因此我们不再只使用字符串表当对象，儿视手动解析和转变为一个业务对象。
- 可定制的控制器处理程序和视图解析器。控制器处理程序和视图解析的策略来自简单的基于` URL `的配置，到复杂的专门解决的策略。Spring 比web MVC 框架授权技术更加灵活。
- 灵活的模型转变，再任何视图技术中模型转变从 name 到 value 的映射支持更加容易集成。
- 可定制的语言环境，时区，和主题解析。支持有或没有 Spring 标签库的 JSP。支持 `JSTL` ，支持不需要额外桥梁的`Velocity`等等。
- Sping 的标签库作为一个简单而且富有能量的`JSP` 标签库是众所周知的。它提供了一写特性的支持，比如数据绑定和主题。自定义标签允许最大限度的灵活在标记代码的时候。
- `Beans`的整个生命周期是当前的`Http` 请求或者`Http`Session 作用域。这不是`Spring MVC `自己的特性，而是`Spring`所使用的`WebApplicationContext` 容器的特性。在这部分`bean`的作用域被描述为`request , session, global session, application, 和 WebSocket scopes`<br>

**其他 MVC 实现的可插拔性**<br>
不是用Spring MVC来实现对于某些项目来说可能更可取。许多团队期待利用他们已经存在技能和工具。例如：JSF。<br>
如果你不想要使用`Spring web mvc`，但是想利用 Spring 提供的其他的解决方案。那你可以容易的把你选择`web MVC`框架和`Spring`集成。通过他的`ContextLoaderListener`简单的启动一个Spring应用。然后访问它，从任何一个对象通过它的`ServeletContext`属性（或者 Spring 的辅助方法）。没有插件关联，所以没有必要专门的集成。从网页层指向视图，简单的把 Spring 作为一个库来使用，以启动应用上下文的实例为应用的入口点。<br>
你注册的`beans`和Spring的`services`是唾手可得的甚至不使用`Spring MVC`。在这个场景中Spring不与其他web 框架竞争。它只是解决了一些一个纯洁的MVC 框架没有涉及的领域。从 bean 的配置到数据访问和事务控制。所以你可以丰富你的应用用Spring的中间层或者数据访问层，甚至你只是想用，例如：`JDBC`和`Hibernate`的事务抽象。


### 22.2 The DispatcherServlet 

`Spring` 的 `MVC web` 框架跟其他许多的 `web MVC` 框架一样、请求驱动、围绕着一个`Servelt`中心，他分发请求到控制器和提供其他功能，这是促进`web`引用的发展。然而`Spring`的`DispatcherServlet`却做的更多。它完整的集成了 `Spring IOC`容器，比如允许你使用`Spring`具有的每一个特性。<br>
`Spring Web MVC DispatcherServlet` 的工作流处理如下：<br>
`DispatcherServlet` 其实是一个`Servlet`*它的继承自`HttpServlet`*，本身被声明在你的`web`应用中。你需要映射那些你想要`DispatcherServlet`处理的请求，通过使用`URL`映射。在 `Servlet 3.0+` 的环境中这里有一个`Java EE Servlet`的配置：<br>
<div style="height:240px;overflow:auto">

```java 

        import org.springframework.web.WebApplicationInitializer;
        import org.springframework.web.servlet.DispatcherServlet;

        import javax.servlet.ServletContext;
        import javax.servlet.ServletException;
        import javax.servlet.ServletRegistration;

        public class MyWebApplicationInitializer implements WebApplicationInitializer{

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                ServletRegistration.Dynamic registration = servletContext.addServlet("example", new DispatcherServlet());
                registration.setLoadOnStartup(1);
                registration.addMapping("/example/*");
            }
        }

```
</div>
<br>

在上面的列子中，所有以`/example`开头的请求将被一个叫`example`的`DispacherServlet`实例处理。<br>
`WebApplicationInitializer` 是一个`Spring MVC `提供的接口，他确保你的基础代码配置是可以被检测到的和自动初始化任何`Servlet 3`的容器。一个叫做`AbstractAnnotationConfigDispatcherServlet`的抽象类实现了`WebApplicationInitializer`这个接口甚至使它更容易注册`DispatcherServlet`通过简单的说明他的 `servlet` 映射和罗列出配置类，这是一个推荐的方式设置你的`Spring MVC` 应用。请参考 *基于代码的容器初始化* 查看更多详细信息。<br>
实际上`DispatcherServlet` 是一个`Servlet`(他继承自基类`HttpServlet`)，你也可以在你的`web` 应用中像这样声明你的`web.xml`文件。你需要映射你想让`DispatcherServlet`处理的请求，通过使用在同一个`web.xml`文件中使用`URL`映射。这是一个标准的`Java EE Servlet` 配置；下面这个例子展示了`DispatcherServlet`声明和映射：<br>

下面的`web.xml`等价于上面基于代码的例子：
<div style="height:240px; overflow: auto;">

```xml
    <web-app>
        <servlet>
            <servlet-name>example</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <load-on-startup>1</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>example</servlet-name>
            <url-pattern>/example/*</url-pattern>
        </servlet-mapping>
    </web-app>

```
</div><br>

正如在`7.15`的详情部分，*`ApplicationContext` 的附加功能*，`ApplicationContext`实例在`Spring`中可以被审视(scoped)。在`Web MVC`框架里，每一个`DispatcherServlet`有他自己的`WebApplicationContext`，他们继承的所有`bean`已经被定义在*根`WebApplicationContext`*上。*根`WebApplicationContext`*应该包含所有基础的`beans`，*根`WebApplivationContext`*应该在其他的`contexts`和`Servlet`实例中被分享。这些继承的`beans`可以在特殊的`servlet-scope`中被复写，你也可以为一个给定的`Servlet`实例定义新的`servlet-scope beans`。<br>

根据`DispatcherServlet`的初始化，`Spring MVC` 会在你web应用的 `WEB-INF`目录下寻找一个叫做`[servlet-name]-servlet.xml`的文件，并且创建这里面定义的 `bean`。覆盖任何在全局域中用同一个名字定义`bean`。<br>

思考下面`DispatcherServlet`的`Servlet`配置：<br>
<div style="height:240px; overflow:auto">

 ```xml

 <web-app>
    <servlet>
        <servlet-name>golfing</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>golfing</servlet-name>
        <url-pattern>/golfing/*</url-pattern>
    </servlet-mapping>
</web-app>

 ```
</div><br>

在上面的配置中，你需要一个叫作`/WBE-INF/golfing-servlet.xml`的文件在你应用中；这个文件包括了你所有`Spring Web MVC`指定的组件（`beans`）。你也可以改变这个配置文件的精确的路径，通过一个`Servlet`初始化参数（详情看下面）:<br>
对于单一`DispatcherServlet`的场景，只有一个根`context`是可能的。<br>
这可以被配置通过设置一个空的`contextConfigLocation servlet`参数，正如下面这样：<br>
<div style="height:300px;overflow:auto;">

```xml

<web-app>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/root-context.xml</param-value>
    </context-param>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value></param-value><!--空的参数-->
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
</web-app>

```
</div><br>

`WebApplicationContext` 是`ApplivationContext`的一个扩展计划它有很多对于`web`应用必须的特性。它不同于正常的`ApplicationCOntext`在这方面它有解析主题的能力，它知道哪一个`Servlet`被关联（有一个到`ServletContext`的 link ）。`WebApplicationContext`被约束在`ServletContext`中。如果你需要访问它，你可以通过使用`RequestContextUtils`的静态方法随时查看`WebApplicationContext`。


483 
Note that we can achieve the same with java-based configurations:s