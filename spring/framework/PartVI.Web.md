# <center>Part VI. The Web</center>
这部分的参考手册覆盖 Spring 框架支持的表示层(特殊的基于Web的表示层 `presentation tier`) 包括在 web 引用中支持 `WebSocket-sytle` 消息。<br>
Spring 框架有他自己的 Web 框架，`Spring Web MVC`,第一章里面覆盖了`Spring MVC`的相关知识，随后的章节关注`Spring` 框架跟其它 Web 记住的集成，接下来是 `Spring` 框架的 `MVC protlet` 框架，然后本节在 26 章综合的总结了`Spring` 框架对 `WebSocket`的支持。

## 22. Web MVC 框架
### 22.1 Spring MVC 框架的介绍
`Spring` 的 Web 模型-视图-控制 框架是围绕着 `DispacherServlet` 设计的。它分发 `requests` 到 `handlers`，可以配置 `handler` 的映射，视图解析，定位，时区，主题也支持文件的上传下载。默认的 handler 时基于 `@Controller` 和 `@RequestMapping` 注解，提供了一个广泛灵活的处理方法。从引入 `Spring 3.0` 开始，`@Controller` 机制(mechanism)也允许你创建一个 `RESTful` 网站和应用，通过 `@PathVariable` 注解和其他的特性。<br>

>“面向扩展开放……” Spring Web MVC 设计的原则的关键和在一个普通的 Spring 应用中一个关键的原则时 “面向扩展开放，面向修改封闭”。<br>
Spring MVC 中一些在核心类里面的方法被标记为 `final` 的。作为一个开发者你的不能复写这些方法来支持你自己的行为。这不是武断的做法，但是特别要记住这个原则。<br>
 ……<br>
 你不能添加 `advice` 到一个 `final` 方法，当你使用 Spring MVC 的时候。 例如, 你不能添加 `advice` 给`AbstractController.setSynchronizeOnSession()` 方法。请参考 “Understanding AOP proxies” 学习跟多关于AOP代理的信息和为什么不能添加一个`advice`给一个 final 方法。<br>


在 `Spring MVC` 中你可以使用任何的对象作为一个指令或者 `form-backing` (以表格为后盾的对象)对象。你不需要实现框架的特性接口或者继承一个类。Spring 的数据绑定时高度灵活的： 例如，它将类型不匹配作为一个效验错误，会被应用评估，而不是不是作为系统错误。因此你不需要简单的复制你业务对象的属性，无类型的 String 在你的表单对象简单的被处理为无效的提交，或者转变String的属性。相反的，通常最好是直接绑定业务对象。<br>
Spring 的视图解析是极度灵活的， 一个 `Controller` 对于一个 `Map` 模型的数据和选择视图名称是经典可靠的，但是它也可以直接写一个 `response` 流来完成请求。视图名称解析是一个高度可配置的通过扩展名或者接受会话头的内、通过 Bean 名称、一个属性文件或者甚至一个经典的 `ViewResolver` 来实现。模型（MVC 中的 M）是一个 Map 接口，这样允许完全抽象的视图技术。你可以直接和基于模板渲染的技术集成，例如： JSP ，Velocity 和 Freemarker，或者直接产生 XML， JSON ，Atom 和许多其他类型的内容。模型 `Map` 简单的转变到一个适合的格式，例如 JSP 的 `request attributes`，一个`Velocity`模板模型。

**Spring MVC 的特性**
> *Spring Web Flow*<br>
Spring web Flow（SWF）目标是最好的解决 Web 应用程序管理。<br>
SWF 集成了已经存在的框架 Spring MVC 和 JSF，在Servlet 环境和 Portlet 环境。如果你有一个进程它从一个会话模型转变为一个与纯求情模型想法中受益，那么 SWF 可能解决这个问题。<br>
SWF 允许你捕获逻辑页面流作为它自己的模块，在不同的情况下这回高度可重用。同样的，适合构建网页模块，这将指引用户通过控制器导航，驱动业务进程。

Spring 的 Web 模块 包括许多唯一 web 支持的特性：
- 角色分明。每一个角色——控制器、效验器、命令对象、表单对象、模板对象、`DispatcherServlet`、`handler mapping`、视图解析器等等。被一系列的序列化对象实现。
- `javaBeans` 作为框架和应用类直接强有力且直接的配置。这种配功能包括容易引用的上下文，例如 从 web 控制器到业务对象和验证器。
- 可适配的，非倾入式的和灵活的。你需要定义任意一个控制器的方法签名，尽可能的是员工众多参数注解中的一个（例如 ：`@RequestParam, @RequestHeader, @PathVariable`和其他的）作为一个方案。
- 业务代码的重用，不需要复制。使用已经存在的业务对象作为命令或者表当对象而不是镜像他们来扩展框架基础类的特性。
- 可定制的绑定和验证，类型不匹配作为一个应用水平的效验错误，一直是一个讨厌的值，当地日期和数字额绑定，因此我们不再只使用字符串表当对象，儿视手动解析和转变为一个业务对象。
- 可定制的控制器处理程序和视图解析器。控制器处理程序和视图解析的策略来自简单的基于` URL `的配置，到复杂的专门解决的策略。Spring 比 Web MVC 框架授权技术更加灵活。
- 灵活的模型转变，再任何视图技术中模型转变从 name 到 value 的映射支持更加容易集成。
- 可定制的语言环境，时区，和主题解析。支持有或没有 Spring 标签库的 JSP。支持 `JSTL` ，支持不需要额外桥梁的 `Velocity` 等等。
- Spring 的标签库作为一个简单而且富有能量的 `JSP` 标签库是众所周知的。它提供了一写特性的支持，比如数据绑定和主题。自定义标签允许最大限度的灵活在标记代码的时候。
- `Beans` 的整个生命周期是当前的 `Http` 请求或者 `HttpSession` 作用域。这不是`Spring MVC `自己的特性，而是`Spring`所使用的`WebApplicationContext` 容器的特性。在这部分`bean`的作用域被描述为`request , session, global session, application, 和 WebSocket scopes`。<br>

**其他 MVC 实现的可插拔性**<br>
不是用 Spring MVC 来实现对于某些项目来说可能更可取。许多团队期待利用他们已经存在技能和工具。例如：JSF。<br>
如果你不想要使用 `Spring web mvc`，但是想利用 Spring 提供的其他的解决方案。那你可以容易的把你选择 `Web MVC`框架和 `Spring` 集成。通过他的 `ContextLoaderListener` 简单的启动一个 Spring 应用。然后访问它，从任何一个对象通过它的 `ServeletContext` 属性（或者 Spring 的辅助方法）。没有插件关联，所以没有必要专门的集成。从网页层指向视图，简单的把 Spring 作为一个库来使用，以启动应用上下文的实例为应用的入口点。<br>
你注册的 `beans` 和 Spring 的 `services` 是唾手可得的甚至不使用 `Spring MVC` 。在这个场景中 Spring 不与其他 web 框架竞争。它只是解决了一些一个纯洁的 MVC 框架没有涉及的领域。从 bean 的配置到数据访问和事务控制。所以你可以丰富你的应用用 Spring 的中间层或者数据访问层，甚至你只是想用，例如：`JDBC` 和 `Hibernate` 的事务抽象。


### 22.2 The DispatcherServlet 

`Spring` 的 `MVC web` 框架跟其他许多的 `web MVC` 框架一样、请求驱动、围绕着一个`Servelt`中心，他分发请求到控制器和提供其他功能，这是促进 `web` 引用的发展。然而 `Spring` 的 `DispatcherServlet` 却做的更多。它完整的集成了 `Spring IOC` 容器，比如允许你使用 `Spring` 具有的每一个特性。<br>
`Spring Web MVC DispatcherServlet` 的工作流处理如下：<br>
`DispatcherServlet` 其实是一个`Servlet` *它的继承自`HttpServlet`*，本身被声明在你的 `web` 应用中。你需要映射那些你想要`DispatcherServlet` 处理的请求，通过使用 `URL` 映射。在 `Servlet 3.0+` 的环境中这里有一个 `Java EE Servlet` 的配置：<br>
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

在上面的列子中，所有以 `/example` 开头的请求将被一个叫 `example` 的 `DispacherServlet` 实例处理。<br>
`WebApplicationInitializer` 是一个 `Spring MVC` 提供的接口，他确保你的基础代码配置是可以被检测到的和自动初始化任何 `Servlet 3` 的容器。一个叫做 `AbstractAnnotationConfigDispatcherServlet` 的抽象类实现了 `WebApplicationInitializer` 这个接口甚至使它更容易注册 `DispatcherServlet` 通过简单的说明他的 `servlet` 映射和罗列出配置类，这是一个推荐的方式设置你的 `Spring MVC` 应用。请参考 *基于代码的容器初始化* 查看更多详细信息。<br>
实际上 `DispatcherServlet` 是一个`Servlet`(他继承自基类`HttpServlet`)，你也可以在你的 `web` 应用中像这样声明你的 `web.xml` 文件。你需要映射你想让 `DispatcherServlet` 处理的请求，通过使用在同一个 `web.xml` 文件中使用 `URL` 映射。这是一个标准的 `Java EE Servlet` 配置；下面这个例子展示了 `DispatcherServlet` 声明和映射：<br>

下面的 `web.xml` 等价于上面基于代码的例子：
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

正如在`7.15`的详情部分，* `ApplicationContext` 的附加功能*，`ApplicationContext`实例在`Spring`中可以被审视(scoped)。在 `Web MVC` 框架里，每一个 `DispatcherServlet` 有他自己的 `WebApplicationContext`，他们继承的所有 `bean` 已经被定义在*根`WebApplicationContext`*上。*根 `WebApplicationContext` *应该包含所有基础的 `beans`，*根 `WebApplivationContext` *应该在其他的`contexts`和`Servlet`实例中被共享。这些继承的 `beans` 可以在特殊的 `servlet-scope` 中被复写，你也可以为一个给定的 `Servlet` 实例定义新的 `servlet-scope beans` 。<br>

根据 `DispatcherServlet` 的初始化，`Spring MVC` 会在你 web 应用的 `WEB-INF`目录下寻找一个叫做`[servlet-name]-servlet.xml`的文件，并且创建这里面定义的 `bean`。覆盖任何在全局域中用同一个名字定义`bean`。<br>

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

在上面的配置中，你需要一个叫作 `/WBE-INF/golfing-servlet.xml` 的文件在你应用中；这个文件包括了你所有 `Spring Web MVC` 指定的组件（`beans`）。你也可以改变这个配置文件的精确的路径，通过一个`Servlet`初始化参数（详情看下面）:<br>
对于单一`DispatcherServlet`的场景，只有一个根`context`是可能的。<br>
这可以被配置通过设置一个空的 `contextConfigLocation servlet` 参数，正如下面这样：<br>
<div style="height:300px;overflow:auto;">

```xml

<web-app>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <!-- 主要是加载驱动应用后端的中间层和数据层组件，为是父上下文-->
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

`WebApplicationContext` 是 `ApplicationContext` 的一个扩展计划它有很多对于 `web` 应用必须的特性。它不同于正常的`ApplicationContext` 在这方面它有解析主题的能力，它知道哪一个 `Servlet` 被关联（有一个到 `ServletContext` 的 link ）。`WebApplicationContext` 被约束在 `ServletContext` 中。如果你需要访问它，你可以通过使用 `RequestContextUtils` 类的静态方法随时查看`WebApplicationContext`。

注意到这样，我们可以同样用 `Java` 代码配置来实现：<br>
```java
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class GolfingWebApplication extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // 主要是加载驱动应用后端的中间层和数据层组件，为是父上下文
        // GolfingAppConfig defines beans that would be in root-context.xml
        return new Class[]{ GolfingWebApplication.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //这里的配置文件主要是启用组件扫描，配置视图解析器，静态资源的处理
        // GolfingWebConfig defines beans that would be in golfing-servlet.xml
        return new Class[] { GolfingWebApplication.class};
    }

    @Override
    protected String[] getServletMappings() {
        //配置ServletMapping路径
        return new String[] {
                "/golfging/**"
        };
    }
}

```

**特殊的 Bean 类型**<br>
`Spring` 的 `DispatcherServlet` 使用特殊的 `beans` 来处理请求和渲染适当的视图。这些 `beans` 是 `Spring MVC` 的一部分。你可以在你的应用中通过简单的配置选择使用他们中的一个或者多个特殊的 `bean`。然而，如果你不做任何配置，`Spring MVC` 维护着一个默认的    `bean` 使用列表。 更多的信息查看下一节，现在查看 `DispatcherServlet` 所依赖的这些特殊的 `bean` 类型。<br>
`WebApplicationContext` 中特殊的 `bean`。<br>
 - `HandlerMapping` : 映射来自一个请求处理器和一个前置和后置列表（处理器拦截），基于一个标准，具体的详情要看`HandlerMapping`具体的实现。最流行的实现方式是支持注解的控制器，但是也存在其他好的实现方式。
 - `HandlerAdapter` : 帮助 `DispatcherServlet `调用一个处理器映射一个请求，尽管那个处理器已经被调用了。例如：调用一个基于注解的控制器需要解析各种注解。因此 `HandlerAdapter` 主要的目的是把 `DispatcherServlet` 从这些细节中保护起来。
- `HandlerExceptionResolver` : 映射异常到视图也允许跟多复杂的移除捕获代码。
- `ViewResolver` : 决定逻辑根据字符串名称到一个实际的视图。
- `LocateResolver & LocaleContextResolver` : 决定一个正在使用的客户端并且为了能够提供国际化视图尽可能的使用他们的时区。
- `ThemeResolver` :决定你的 `web` 应用可以使用的主题，例如：提供私人的布局。
- `MultipartResolver` : 解析多部分的请求，例如支持处理文件从 `HTML` 表单上传。
- `FlashMapManager` : 存储和检索 `FlashMap `的 `input `和 `output`，这可以被用来从一个属性到林外一个属性传递属性，一般通过重定向`redirect`。   

**默认的 `DispatcherServlet` 配置**<br>
前面提到的对于每一个特殊的`bean`，默认使用`DispatcherServlet`包含的一个默认`list`的实现。这个信息保存在包`org.springframework.web.servlet`中的`DispatcherServlet.properties`。<br>
所有特殊的 beans 有一些他们自己的合适的默认的值。虽然迟早你将要定做一个或定做一个或者多个这些 beans 提供的属性。例如，一个非常普通的配置一个 InternalResourceViewResolver 设置它的 prefix 属性为视图文件的父路径。<br>
无论细节，这里要明白一个重要的概念。一旦在你的 WebApplicationContext 中配置了一个特殊的类 (例如 InternalResourceViewResolver )，你实际上覆盖了这个 list 的默认实现，否则这个特殊类将被使用。例如，如果你配置了一个 InternalResourceResolver ,默认列表里面的 ViewResolver 的实现会被忽略。<br>
在 22.16 部分，“Spring MVC 配置” 将学到其他的选择，关于 Spring MVC 的配置包括 Java 配置和 XML 配置，两者都提供一个简单的开始并且假设对 Spring MVC 的工作原理一无所知。无论你怎样选择配置你的应用，这部分介绍的概念是基本的并且对你有帮助。<br><br>
**DispatcherServlet 处理队列**<br>
你设置一个 DispatcherServlet 之后，一个请求来了为了这个特殊的 DispatcherServlet，这个 DispatcherServlet 开始处理请求按照下面的步骤：<br>
- 在一个请求中 WebApplicationContext 被找到并且作为一个属性绑定在请求中，在过程中控制器和其他元素可以被使用。它被约束在默认的 key  DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE 之下。
- 当处理请求时，区域解析请求被绑定在请求中为了在区域解析中启用元素（渲染视图、准备数据、等等）。如果你不需要区域解析，你不需要它。
- 主题解析被绑定到请求中为了使能元素。例如，视图决定使用那一个主题，如果你没用到主题，你可以忽略它。]
- 如果你指定一个多文件的解析器，请求将被多文件检查；如果多文件被发现，请求被包装进一个 MultipartHttpServletRequest 在用其他元素进一步处理的过程中。
- 搜索一个合适的处理程序。如果处理器被发现，与处理器相关的执行链被执行（预处理器、后置处理器、和控制器），为了准备模型和渲染。
- 如果模型被返回，视图被渲染。如果没有模型返回，（可能是前置处理器或后置处理器中断了请求，也可能是其它安全原因），没有视图被渲染，因为请求已经完成了。
控制器异常解析器被声明在 WebApplicationContext 中，挑选在处理请求过程中抛出的异常。使用这些异常解析允许你定义通用的行为到异常地址。<br><br>
作为一个特殊的 Servelt API，Spring 的 DispatcherServlet 也支持返回最近的修改日期。确定一个特殊请求最后修改日期的过程是简单的：DispatcherServlet 查找一个合适的控制器映射并且测试这个被发现的控制器是否实现了 LastModified 接口。如果实现了，LastModified 接口的 long
getLastModified(request) 方法的值会被返回给客户端。<br><br>
- 你可以定做个人的 DispatcherServlet 实例通过添加 Servlet 初始化参数(init-param 元素) 到 Servlet 声明在 web.xml 文件中。<br><br>看下面，列出支持的参数：
    1.  contextClass : 实现了 WebApplicationContext，它的实例化 context 被这个 Servlet 使用。默认的，XmlWebApplicationContext 是可用的。 
    2. contextConfigLocation：一个字符串通过 context 实例（尤其是 contextClass）表明 context 在哪里可以被找到。这个字符串可能有多个字符串组成（用一个逗号做分隔符）为了支持多个 context。在 beans 的多个context 对象情况下，被定义两次的优先使用最新的位置。
    3. namespace: WebAppLicationContext 默认的命名空间是 [servlet-name]-servlet。

## 实现控制器
控制器提供访问应用的行为你通常通过服务接口定义。控制器拦截用户的输入和转变它到一个模型里面，这样通过视图展示给用户。Spring 通过一种非常抽象的方式实现了一个控制器，这允许你创建多个种类的控制器。<br><br>
Spring 2.5 为 MVC 控制器介绍了一个基于注解的编程模型，使用注解例如：@RequestMapping、@RequestParam、@ModelAttribute、等等。提供的这些注解是可用的对 Servlet MVC 和 Portlet MVC。控制器在这种方式下被实现不必要扩展指定的基类或者实现特定的接口。此外，他们通常不会直接依赖于 Servlet 或者 Portlet API，虽然你可以容易的配置访问 Servlet 或者 Portlet 实例。<br><br>
**提示**<br>
> 大量的 Web 应用利用本节所描述的注解，包括：MvcShowcase、MvcAjax、MvcBasic、PetClinic、和其他。
```java
@Controller
public class HelloWorldController{
    @RequestMapping("/helloworld")
    public String helloWorld(Model model){
        model.addAttribute("message","hello world");
        return "helloWorlds";
    }
}
```
正如你看见的，@Controller 和 @RequestMapping 注解允许灵活的方法名称和签名。这这个特殊的例子里方法接受一个 Model 和以 String 的形式返回一个视图名称，但是各种其他的方法参数和返回值可以被使用，在这部分的后面解释。@Controller 和 @RequestMapping 和一些其他的注解形式是是实现 Spring MVC 的基础。这些注解的文档和他们在 Servlet 环境中最常用。

### 用 @Controller 定义一个控制器
@Controller 注解表明一个特殊的类以*控制器*的角色服务。Spring 不需要你扩展任何控制器基类或者参考 Servlet API。然而如果你需要，你仍然可以参考 Servlet 的特性。<br><br>
对于一个注解类，@Controller 注解扮演一个原型注解，表明它的角色。分发器扫描这样的注解类映射方法和发现 @ReuqestMapping 注解。<br><br>
你可以明确定义注解控制器 bean，使用 Spring 标准的 bean 定义在分发器的上下文中。然而，原型的 @Controller 允许被自动发现，Spring 通常支持在 classpath 中发现组件类和自动注册 bean 的定义。<br><br>
为了使能自动发现例如：控制器注解，你添加组件扫描在你的配置中。使用 spring-context 模式 就像下面 XMl 脚本展示的：
```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="org.springframework.samples.petclinic.web"/>
    <!-- ... -->
    </beans>

```
### 用 @RequestMaping 映射请求
你使用 @RequestMaping 注解映射到 URLs 例如 /appointments 到一个类或者一个特殊的 处理方法。典型的类水平的注解映射一个特殊的请求路径到一个控制器，用额外的方法水平的注解为一个 HTTP 请求方法限制私有的映射，如（“GET”， “POST”）或者 HTTP 请求条件参数。<br>
下面从一个简单的 Petcare 例子来展示 Spring MVC 的控制器怎样用于应用中的：
```java
@Controller
@RequestMapping("/appointments")
public class AppointmentsController {
    private final AppointmentBook appointmentBook;
    @Autowired
    public AppointmentsController(AppointmentBook appointmentBook) {
        this.appointmentBook = appointmentBook;
    }
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Appointment> get() {
        return appointmentBook.getAppointmentsForToday();
    }
    @RequestMapping(path = "/{day}", method = RequestMethod.GET)
    public Map<String, Appointment> getForDay(@PathVariable @DateTimeFormat(iso=ISO.DATE) Date day,
    Model model) {
        return appointmentBook.getAppointmentsForDay(day);
    }
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public AppointmentForm getNewForm() {
        return new AppointmentForm();
    }
    @RequestMapping(method = RequestMethod.POST)
    public String add(@Valid AppointmentForm appointment, BindingResult result) {
        if (result.hasErrors()) {
            return "appointments/new";
        }
        appointmentBook.addAppointment(appointment);
        return "redirect:/appointments";
    }
}
```
在上面的列子，@RequestMapping 被使用在多个地方。第一次使用在类水平，它表明这个控制器的所有方法都与 路径 `/appointments`由关系。get() 方法有一个更深一层的 @RequestMappings 提炼：它只接受 GET 请求，意味着对于 `/appointments` HTTP GET 将调用这个方法。<br>

** 由于翻译太慢了， 从这里开始就只记录部分内容 2018-03-18**

- @RequestMapping 在类水平上的注解可以不要，省略后所有的路径都是简单的绝对路径。

```java
@Controller
public class ClinicController {
    private final Clinic clinic;
    @Autowired
    public ClinicController(Clinic clinic) {
        this.clinic = clinic;
    }
    @RequestMapping("/")
    public void welcomeHandler() {
    }
    @RequestMapping("/vets")
    public ModelMap vetsHandler() {
        return new ModelMap(this.clinic.getVets());
    }
}
```
上面的离职没有特殊的 GET、PUT、POST等等，因为 @RequestMapping 是默认的。 使用 @RequeatMapping(method=GET) 或者 使用 @GetMapping 来限制映射。

### 由 @RequestMapping 组成的变体
Sping 4.3 介绍了由 @RequeatMapping 组成的方法后级注解，为了帮助简单的映射普通的 HTTP 方法和更好的表达方法控制器的语意。
- @GetMapping
- @PostMapping
- @PutMapping
- @DeleteMapping
- @PatchMapping

### @Controller 和 AOP 代理
一些情况下一个控制器在运行时可能需要一个 AOP 代理装饰。一个例子是 如果你选择在控制器上直接有一个 @Transaction 注解。这样的情况下，对于特殊的控制器，我们推荐使用基于类的代理。这是控制器典型的选择。然而如果控制器实现了一个不是 Spring 上下文回调的接口，你可能需要显示的配置基于类的代理。例如：从`<tx :annotation-driven/>` 改变为`<tx:annotation-driven proxy-target-class="true">`。

### 矩阵变量
矩阵变量可以出现在路径的任何部分，每个矩阵变量之间用“;”(semicolon)分割。比如：`"/cars;color=red;year=2013"`。多个值之间也可以用","(comma)分割 `color=red,green`。<br>
如果 URL 期待一个矩阵变量，请求映射模式必须用一个 URL 模板代表。这样确保请求可以被正确的匹配不管被替代的矩阵变量是否安装提供的顺序。<br>
下面的例子期待一个矩阵变量 ‘q’：
```java
// GET /pets/42;q=11;r=22
@GetMapping("/pets/{petId}")
public void findPet(@PathVariable String petId, @MatrixVariable int q) {
    // petId == 42
    // q == 11
}
```
自从所有的路径语法可以包含矩阵变量，在某些情况下你需要特别指定矩阵变量期待的位置：
```java
// GET /owners/42;q=11/pets/21;q=22
@GetMapping("/owners/{ownerId}/pets/{petId}")
public void findPet( @MatrixVariable(name="q", pathVar="ownerId") int q1, @MatrixVariable(name="q", pathVar="petId") int q2) {
    // q1 == 11
    // q2 == 22
}

```
一个矩阵变量可以被定义做一个可选择的和一个特殊的默认值：
```java
// GET /pets/42
@GetMapping("/pets/{petId}")
public void findPet(@MatrixVariable(required=false, defaultValue="1")  int q) {
    // q == 1
}

```
所有的矩阵变量可以在一个 Map 里面获得:
```java
// GET /owners/42;q=11;r=12/pets/21;q=22;s=23
@GetMapping("/owners/{ownerId}/pets/{petId}")
public void findPet(
@MatrixVariable MultiValueMap<String, String> matrixVars, @MatrixVariable(pathVar="petId") MultiValueMap <String, String> petMatrixVars) {
    // matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
    // petMatrixVars: ["q" : 11, "s" : 23]
}

```
**注意**：要使矩阵变量可用，你必须设置 RequestMappingHandlerMapping 的 removeSemicolonContent 属性为 false ，默认为 true。
> 提示 ：MVC 的 java 配置和 MVC 的命名空间都为开启对矩阵变量的使用提供的选项。 <br> ......


## 定义一个 @RequestMapping 处理方法
@RequestMapping 处理方法有非常灵活的特性。支持的方法参数和返回值在下面的部分被叙述。大部分的参数可以被以任意的顺序使用，唯一不同的是 BindingResult 参数。这在下一节被叙述。

### 支持的方法参数类型
以下是支持的方法参数：
- request 或 response 对象(Servlet API)。选择任意指定的请求或响应类型，例如：ServletRequest 或 HttpServletRequest。
- session 对象(Servlet API)。 一种 HttpSession 类型，一个这种类型的参数强制存在与一个交叉回话中。这样一个结果，这样的参数永远不会是 null。

> 提示：Session 访问可能不是线程安全的。尤其是在一个 Servlet 环境中。考虑设置 RequestMappingHandlderAdapter 在 Session 的同步锁标识为 true 。如果多个请求被允许同时访问一个 session 。
- org.springframework.web.context.request.WebRequest 或 org.springframework.web.context.request.NativeWebRequest 允许通用的 request 参数访问和 request/session 属性访问，没有绑定到 native Servlet/Portlet API。
- java.util.Locale 为了当前请求的区域，取决于最近的可用的区域，配置 LocaleResolver/LocaleContextResolver  在 MVC 的环境中可以生效。
- org.springframework.http.HttpMethod 为了 HTTP 请求 方法。
- java.security.Principal 包含当前的已经认证的作者。
- @PathVariable 注解参数为了访问 URL 模板参数。
- @MatrixVariable 注解参数为了访问位于 URL 路径中的 name-value 部分。
- @RequestParam 注解参数为了访问特殊的 Servlet 请求参数。参数值被转变导已经声明的方法参数类型。
- @RequestHeader 注解参数为了访问特殊的 Servlet 请求 HTTP 的 headers。参数值被转变导已经声明的方法参数类型。
- @RequestBody 注解参数为了访问特殊的 Servlet 请求 HTTP 的 body。参数值通过 HttpMessageConverters 被转变导已经声明的方法参数类型。
- @RequestPart 注解参数为了访问 “multipart/form-data” 的内容。文件上传下载。
- @SessionAttribute 注解参数为了访问已经存在的、永久的 session 属性与 model 属性通过 @SessionAttribute 临时存储与 session 作为 Controller 的工作流。
- @RequestAttribute 注解参数为了访问请求属性。
- HttpEntity<?> 参数访问 Servlet 请求 HTTP header 和 contents。请求流通过HttpMessageConverter 转变导 entity body。
- org.springframework.web.servlet.mvc.support.RedirectAttributes 到一个特殊的确切的属性集合在使用 redirect 的情况还要添加 flash 属性。
- 命令或者表单对象绑定 request 参数到 bean 的属性(通过 setters) 或直接到域，用一个可以定制的转变类型，依赖于 @InitBinder 方法或 HandlerAdapter 的配置。
- org.springframework.validation.Errors / org.springframework.validation.BindingResult 在命令对象或表单对象之前效验结果。
- org.springframework.web.bind.support.SessionStatus 标记处理完成的状态句柄，这个触发清空集成在 @SessionAttributes 注解在处理器水平的 session 属性。
- org.springframework.web.util.UriComponentsBuilder 一个构建器预处理 URL 到当前请求的 host、port、scheme、context path、和文本部分的Servlet 映射关系。

Errors 或 BindingResult 参数不得不遵循 model 对象,作为一个方法签名 model 对象会被立即绑定。可能会有不止一个 model 对象, Spring 将为每一个对象创建单独的 BindingResult 实例。

### 支持方法返回值类型
1. 用 @RequestParam 绑定到 request 参数到一个方法中。
    - 参数默认是必须的，但是可以设置他的 required 属性为 false。如：@RequestParam(name="id",required="false").
    - 当 @RequestParam 注解使用到一个 Map<String, String> 或 MultiValueMap<String, String> 参数，这个Map 会被所有的请求参数填充。
2. 用 @RequestBody 映射 request body。<br>
 @RequestBody 方法注解表明这个方法参数会被绑定到 HTTP request body 中。
```java
@PutMapping("/something")
public void handle(@RequestBody String body, Writer writer) throws IOException {
    writer.write(body);
}
```
可以使用 HttpMessageConverter 转变 request body 到一个方法参数。HttpMessageConverter 是可靠的转变 HTTP 请求消息到一个对象和从一个对象到 HTTP response body。

### jackSon 序列化视图支持
过滤掉将要被序列化到 HTTP 响应中的上下文对象是有用的。为了提供这样的能力，Spring MVC 已经编译了支持 JackSon 的序列化视图。<br>
为了使用 @ResponseBody 控制器或控制器方法返回 ResponseEntity，简单的添加一个 @Jackson 注解，用一个类做参数指定被使用的视图类或者接口:
```java
@RestController
public class UserController {
    @GetMapping("/user")
    @JsonView(User.WithoutPasswordView.class)
    public User getUser() {
        return new User("eric", "7!jd#h23");
    }
}
public class User {
    public interface WithoutPasswordView {};
    public interface WithPasswordView extends WithoutPasswordView {};
    private String username;
    private String password;
        public User() {
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @JsonView(WithoutPasswordView.class)
    public String getUsername() {
        return this.username;
    }
    @JsonView(WithPasswordView.class)
    public String getPassword() {
        return this.password;
    }
}
```
**注意:**<br>
尽管 @JsonView 允许指定多于一个类被指定，用在控制器的方法上仅仅支持一个明确的类参数。考虑使用一个接口混合如果你需要使能多个视图。<br><br>

### jackson JSON 的支持
为了使能 JSONP 支持 @ResponseBody 和 ResponseEntity 方法。声明一个 @ControllerAdvice 类继承 AbstractJsonpResponseBodyAdvice 如下所示构造器的参数表明 JSONP 查询参数。

```java
@ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
    public JsonpAdvice() {
        super("callback");
    }
}

```
控制器依赖视图解析，当一个请求有一个 jsonp 或 callback 的查询参数，JSONP 自动开启。这些名称可以通过 jsonpParameterNames 属性定制。

## 异步处理请求
Spring MVC3.2 曾经介绍过基于 Servlet 3 的异步请求处理。代替返回一个值，通常控制器方法现在可以返回一个 java.util.concurrent.Callable 然后处理从 Spring 管理的线程中返回的一个值。同时 Servlet 的主线程是存在的，释放并且允许处理其他的请求。当 Callable 返回的时候，Spring MVC 调用的是 TaskExcutor 里面的单独的一个线程。通过 Callable 的返回值，请求被发送回 Servlet 的容器恢复执行。<br> ... <br>

## 处理器映射
 在之前的 Spring 版本，用户需要在 Web 应用上下文中定义一个或多个 HandlerMapping 来映射 web 请求到一个合适的处理器。在引入注解的控制器中，你通常不需要这么做因为 RequestMappingHandlerMapping 自动在所有@Controller 注解的 bean 中寻找@RequestMapping 注解。然而，请记住类扩展自 AbstractHandlerMapping 的 HandlerMapping 又一些属性，你可以用来定制他们的行为。

 ### 用一个 HandlerInterceptor 拦截请求
 Spring 处理程序的拦截机制包括处理程序的拦截器，当你想应用一个特殊的功能到某些请求时，这是非常有用的，比如 ： 检查权限。<br>
 拦截器被定位在处理程序中，必须从 `org.springframework.web.servlet` 包里面实现 HandlerInterceptor 。这个接口定义了三个方法：`preHandle(..)` 、 `postHandle(..)`、`afterCompletion(..)`。

 ## 视图解析
 所有的 MVC　框架对 web 应用都提供了一种方法解析视图。Spring 提供了视图解析器，它使你在浏览器中可以渲染模型，不同绑定特定的视图技术。<br>
 两个重要的接口 ViewResolver 和 View 这是 Spring 处理视图重要的方法。ViewResolver 提供了名称视图和逻辑视图之间的映射。View 接口预处理请求然后把请求递交给另外的视图技术。
### 视图解析链
Spring 支持多个视图解析器。因此你可以把多个视图解析器串成链，比如：在某些情况下复写特殊的视图。你可以通过添加多于一个视图解析器在你的应用上下文中创建视图解析器链。如果必须的，通过设置 order 属性来指定特殊的顺序。记住，越在链后面的视图解析器有越高的优先级。

### 重定向到视图
正如之前提到的，一个控制器通常返回一个逻辑视图名称，这是视图解析器解析到一个特别的视图技术。对于视图技术，比如： JSP 这是通过 Servlet 或 JSP 引擎处理，这样的解析通常是通过混合 InternalResourceViewResolver 和 InternalResourceView 来处理的，它发行一个内部重定向或引入通过 Servlet 的 API `RequestDispatcher.forward(..)` 方法或`RequestDispatcher.include()` 方法。<br>
有时是可取的在视图渲染之前发一个 HTTP 重定向回客户端。这是可取的，比如：当一个控制器已经被 POST 调用，响应实际上是被委托给另外一个控制器（比如，表单提交）。在这样的情况，一个正常的内部重定向意味着其他的控制器也将看见一样的数据，这是一个潜在的问题如果它可以把它和其他预期的值混淆。<br>
> 另外的一个在显示结果之前执行从定性的理由是：尽可能的消除用户提交表单的可能性。在这样的情形下，浏览器将首先发一个最初的 POST；浏览器将收到一个响应要重定向到不同的 URL；最后浏览器将执行向后面响应的 URL 执行一个 GET。因此在浏览器看来，当前的页面没有收到 POST 的影响，而是另一个 GET 的影响。这样的结果是，用户没有方式通过刷新来意外的提交重复的数据。刷新强制了一个 GET 的结果，而不是重新发送一个 POST 数据。

### 重定向视图
一种强制让一个控制器响应结果重定向是：控制器创建并返回一个 Spring 的 RedirectView 实例。这种情况下， DispatcherServlet 没有使用正常的视图解析机制。另外因为它已经被给了一个重定向视图，DispatcherServlet 只是简单的命令视图做它的工作。 RedirectView 反过来调用 HttpServletResponse.sendRedirect() 发送 HTTP 重定向到浏览器客户端。<br>
如果你使用 RedirectView，视图会被控制器自己创建。推荐配置重定向 URL 注入到控制器，这样就不会被放到控制器中，但是是以视图名称配置在上下文对象中的。

### 通过数据重定向目标
在重定向 URL 时，默认的所有模型的属性都被考虑到暴露在 URL 模板变量中。在剩下的属性中，原始的类型或集合/数组的类型都自动的作为查询参数添加。<br>
原始属性作为查询参数添加可能时一个期待得结果，如果一个模型实例是为了重定向特别准备的。然而被注解的控制的模型可能为了渲染的目的被添加了额外的属性。为了尽可能的避免有属性出现在 URL　里面　@RequestMapping 方法可以声明一个 RedirectAttributes 类型的属性，使用他指定确切的属性给 RedirectView 使用。如果方法被重定向，RedirectAttributes 的内容就会被使用。另外，模型的内容也是可用的。<br>
RequestMappingHandlerAdapter 提供了一个叫作 ignoreDefaultModelOnRedirect 的属性，可以用来表明如果一个控制器方法重定向默认的 Model 不因该被使用。替代控制的方法应该被声明为一个 RedirectAttributes 类型的属性或者如果不在属性里面则应该通过 RedirectView。MVC 命名空间和 MVC　Java　配置为了向后兼容保持这个属性为 false。然而，对于新应用我们推荐把他设置为 true。<br>
**注意** 当扩展一个重定向 URL 和不需要明确的添加 Model 和 RedirectAttributes 时，来自当前请求的 URL 模板变量自动可用。例如：
```java
@PostMapping("/files/{path}")
public String upload(...) {
// ...
    return "redirect:files/{path}";
}
```
另外的一种将数据传递给重定向的方法是通过 Flash Attributes。不像其他的重定向属性，flash 属性是被存储在 HTTP session 中。

### 重定向： prefix
虽然使用 RedirectView 可以很好的工作，如果控制器自己创建了 RedirectView，控制器重定向正在发生是一个不可避免的事实。这真的是次优的并且二者关系太紧密了。控制器不因该认真关心响应怎样得到处理。一般的它应该只操作一组已经被注入的视图名称。<br>
特殊的 `redirect` 前缀讯息你完这个。如果被返回的视图名称的前缀有一个 `redirect:` ，UrlBaseViewResolver 将识别出来并作为需要重定向的特殊的指示。剩下的 URL 将被当作重定向的 URL 对待。<br>
网络上的效果是一样的，如果控制器返回一个 RedirectView，但是现在控制器自己可以操作一组逻辑视图名称。一个逻辑视图名称，比如: redirect:/myapp/some/resources 将要重定向到相对当前 Servlet 上下文中，当这样 `redirect:http://myhost.com/some/arbitrary/path` 一个名称出现的时候将被重定向到一个绝对路径。<br>
**注意** 控制器是被 @ResponseStatus 注解的，注解的值将被优先响应通过 RedirectView。