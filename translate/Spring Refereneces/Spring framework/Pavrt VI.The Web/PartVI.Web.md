# <center>Part VI. The Web</center>
这部分的参考手册覆盖Spring框架支持的表示层(特殊的基于Web的表示层`presentation tier`) 包括在web 引用中支持 `WebSocket-sytle` 消息。<br>
Spring框架有他自己的Web框架，`Spring Web MVC`,第一章里面覆盖了`Spring MVC`的相关知识，随后的章节关注`Spring` 框架跟其它Web记住的集成，接下来是`Spring`框架的 `MVC protlet` 框架，然后本节在 26 章综合的总结了`Spring` 框架对 `WebSocket`的支持。

## 22. Web MVC 框架
### 22.1 Spring MVC 框架的介绍
`Spring` 的 Web 模型-视图-控制 框架是围绕着 `DispacherServlet`设计的。它分发`requests`到 `handlers`，可以配置`handler`的映射，视图解析，定位，时区，主题也支持文件的上传下载。默认的 handler 时基于`@Controller`和`@RequestMapping`注解，提供了一个广泛灵活的处理方法。从引入`Spring 3.0` 开始，`@Controller`机制(mechanism)也允许你创建一个`RESTful`网站和应用，通过`@PathVariable`注解和其他的特性。<br>

>“面向扩展开放……” Spring Web MVC 设计的原则的关键和在一个普通的Spring 应用中一个关键的原则时 “面向扩展开放，面向修改封闭”。<br>
Spring MVC 中一些在核心类里面的方法被标记为 `final`的。作为一个开发者你的不能腹泻这些方法来支持你自己的行为。这不是武断的做法，但是特别要记住这个原则。<br>
 ……<br>
 你不能添加 `advice`到一个 `final`方法，当你使用 Spring MVC 的时候。 例如, 你不能添加 `advice` 给`AbstractController.setSynchronizeOnSession()` 方法。请参考 “Understanding AOP proxies” 学习跟多关于AOP代理的信息和为什么不能添加一个`advice`给一个 final 方法。<br>


在 `Spring MVC`中你可以使用任何的对象作为一个指令或者`form-backing`(以表格为后盾的对象)对象。你不需要实现框架的特性接口或者继承一个类。Sping 的数据绑定时高度灵活的： 例如，它对待类型不匹配作为一个效验错误，会被应用评估，为不是作为系统错误。因此你不需要简单的复制你业务对象的属性，无类型的 String 在你的表单对象简单的处理无效的提交，或者转变String的属性。相反的，通常最好是直接绑定业务对象。<br>
Spring 的视图解析是极度灵活的， 一个`Controller` 是一个经典可靠的对于一个模型`Map`伴随数据和先择的视图名称，但是它也可以直接写一个`response`流来完成请求。视图名称解析是一个高度可配置的统统扩展名或者接受会话头的内容，通过Bean 名称，一个属性文件或者甚至一个经典的`ViewResolver`来实现。模型（MVC 中的 M）是一个 Map 接口，这样允许完全抽象的视图技术。你可以直接和基于模板渲染的技术集成，例如： JSP ，Velocity 和Freemarker，或者直接产生XML， JSON ，Atom和许多其他类型的内容。模型 `Map` 简单的转变到一个适合的格式，例如 JSP 的 `request attributes`，一个`Velocity`模板模型。

**Spring MVC 的特性**
> *Spring Web Flow*<br>
Spring web Flow（SWF）目标是最好的解决 Web 应用程序的管理。<br>
SWF 集成了已经存在的框架 Spring MVC 和 JSF，在Servlet 环境和Portlet环境。如果你有一个进程它从一个会话模型转变为一个与纯求情模型想法中受益，那么SWF 可能解决这个问题。<br>
SWF 允许你捕获逻辑页面流作为它自己的模块，在不同的情况下这回高度可重用。同样的，适合构建网页模块，这将指引用户通过控制器导航，驱动业务进程。

page 481
Spring’s web module includes many unique web support features:# <center>Part VI. The Web</center>
这部分的参考手册覆盖Spring框架支持的表示层(特殊的基于Web的表示层`presentation tier`) 包括在web 引用中支持 `WebSocket-sytle` 消息。<br>
Spring框架有他自己的Web框架，`Spring Web MVC`,第一章里面覆盖了`Spring MVC`的相关知识，随后的章节关注`Spring` 框架跟其它Web记住的集成，接下来是`Spring`框架的 `MVC protlet` 框架，然后本节在 26 章综合的总结了`Spring` 框架对 `WebSocket`的支持。

## 22. Web MVC 框架
### 22.1 Spring MVC 框架的介绍
`Spring` 的 Web 模型-视图-控制 框架是围绕着 `DispacherServlet`设计的。它分发`requests`到 `handlers`，可以配置`handler`的映射，视图解析，定位，时区，主题也支持文件的上传下载。默认的 handler 时基于`@Controller`和`@RequestMapping`注解，提供了一个广泛灵活的处理方法。从引入`Spring 3.0` 开始，`@Controller`机制(mechanism)也允许你创建一个`RESTful`网站和应用，通过`@PathVariable`注解和其他的特性。<br>

>“面向扩展开放……” Spring Web MVC 设计的原则的关键和在一个普通的Spring 应用中一个关键的原则时 “面向扩展开放，面向修改封闭”。<br>
Spring MVC 中一些在核心类里面的方法被标记为 `final`的。作为一个开发者你的不能腹泻这些方法来支持你自己的行为。这不是武断的做法，但是特别要记住这个原则。<br>
 ……<br>
 你不能添加 `advice`到一个 `final`方法，当你使用 Spring MVC 的时候。 例如, 你不能添加 `advice` 给`AbstractController.setSynchronizeOnSession()` 方法。请参考 “Understanding AOP proxies” 学习跟多关于AOP代理的信息和为什么不能添加一个`advice`给一个 final 方法。<br>


在 `Spring MVC`中你可以使用任何的对象作为一个指令或者`form-backing`(以表格为后盾的对象)对象。你不需要实现框架的特性接口或者继承一个类。Sping 的数据绑定时高度灵活的： 例如，它对待类型不匹配作为一个效验错误，会被应用评估，为不是作为系统错误。因此你不需要简单的复制你业务对象的属性，无类型的 String 在你的表单对象简单的处理无效的提交，或者转变String的属性。相反的，通常最好是直接绑定业务对象。<br>
Spring 的视图解析是极度灵活的， 一个`Controller` 是一个经典可靠的对于一个模型`Map`伴随数据和先择的视图名称，但是它也可以直接写一个`response`流来完成请求。视图名称解析是一个高度可配置的统统扩展名或者接受会话头的内容，通过Bean 名称，一个属性文件或者甚至一个经典的`ViewResolver`来实现。模型（MVC 中的 M）是一个 Map 接口，这样允许完全抽象的视图技术。你可以直接和基于模板渲染的技术集成，例如： JSP ，Velocity 和Freemarker，或者直接产生XML， JSON ，Atom和许多其他类型的内容。模型 `Map` 简单的转变到一个适合的格式，例如 JSP 的 `request attributes`，一个`Velocity`模板模型。

**Spring MVC 的特性**
> *Spring Web Flow*<br>
Spring web Flow（SWF）目标是最好的解决 Web 应用程序的管理。<br>
SWF 集成了已经存在的框架 Spring MVC 和 JSF，在Servlet 环境和Portlet环境。如果你有一个进程它从一个会话模型转变为一个与纯求情模型想法中受益，那么SWF 可能解决这个问题。<br>
SWF 允许你捕获逻辑页面流作为它自己的模块，在不同的情况下这回高度可重用。同样的，适合构建网页模块，这将指引用户通过控制器导航，驱动业务进程。

page 481
Spring’s web module includes many unique web support features: