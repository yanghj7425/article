# 1. Spring AOP

<!-- TOC -->

- [1. Spring AOP](#1-spring-aop)
  - [1.1. Spring 的切点 API](#11-spring的切点api)
    - [1.1.1. 概念](#111-概念)
    - [1.1.2. 节点操作](#112-节点操作)
    - [1.1.3. AspectJ 切点表达式](#113-aspectj切点表达式)
      - [1.1.3.1. 实用的切点实现](#1131-实用的切点实现)

<!-- /TOC -->

## 1.1. Spring 的切点 API

### 1.1.1. 概念

Spring 的切点模型使得切点可以重用，并独立于通知类型。使得目的不同的通知可以用同样切点。
`org.springframework.aop.Pointcut` 接口是一个核心接口，用于定位到特定的类和方法。完整的接口展示如下：

```java
public interface Pointcut {
    ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
}
```

Pointcut 接口分为两部分：允许重用的类和匹配的方法，细粒度的组合操作。
ClassFilter 接口用于限制切点提供的类的集合。如果 `matches()` 方法返回 true，所有的目标类都将被匹配：

```java
public interface ClassFilter {
    boolean matches(Class clazz);
}
```

MethodMatcher 接口通常更重要。完整的接口展示如下：

```java
public interface MethodMatcher {
    boolean matches(Method m, Class targetClass);
    boolean isRuntime();
    boolean matches(Method m, Class targetClass, Object[] args);
}
```

`matches(Method, Class)` 用于测试给定的方法是否与目标类匹配。 为了避免这个测试在每一次方法调用的时候被执行，这个测试评估会在 AOP 代理创建的时候被执行。对于一个给定的方法如果 2 个参数的 matched 方法返回 true，isRuntime() 方法对 MethodMatcher 也返回 true，3 个参数的 matches 方法将会在每一个方法调用的时候被执行。这使得切点可以在目标通知被执行之前实时查看传递给调用方法的参数。

大部分 MethodMatchers 方法是静态的，意味着他们的 `isRuntime()` 方法返回 false。这样的情况下，3 个参数的 matches 方法将永远不会被调用。

> 提示：
> 如果可能，尝试使切点为静态的，在 AOP 创建的时候允许 Spring AOP 框架缓存切点计算的结果。

### 1.1.2. 节点操作

Sping 支持的切点操作有：特指的（notably）、并集（union）、交集（intersection.）。

- 并集：其它的方法也可以匹配其它多个切点。
- 交际：两个切点都匹配的方法。
- 并集通常更加有用。
- 切点可以使用 `org.springframework.aop.support.Pointcuts` 类或者相同包下 `ComposablePointcut` 类的静态方法压缩。然而，使用 AspectJ 切点表达式通常更简单。

### 1.1.3. AspectJ 切点表达式

自从 2.0，通过 Spring 的 `org.springframework.aop.aspectj.AspectJExpressionPointcut` 使用切点最重要的方式。这样的切点可以使用 AspectJ 库支持来解析一个 AspectJ 表达式的字符串。

#### 1.1.3.1. 实用的切点实现

Spring 提供了几个方便使用的切点实现。一些可以开箱即用；另外一些在应用的特殊切点去继承他们成为一个子类。

- 静态切点
  - 静态的切点是基于方法和目标类的，不能考虑方法的参数。静态切点是足够的对于大多数用法。当方法第一次调用的时候，Spring 可以对一个静态切点进行一次评估。在每个方法被调用的时候不需要再次评估。
- 正则表达式切点
  - 一个明显的指定静态切点的方式是通过正则表达式。
