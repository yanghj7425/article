# <center> 第五章 组件 </center>
- `@`: 一个字符串参数
- `$onInit()`: 在每一个控制器元素构造完成之后调用控制器，这是一个放置控制器初始化代码的好地方。
- `$onChanges(obj)`: 每当更新的时候调用。
- `$onDestory()`: 当一个控制器的 scope 被销毁的时候调用，调用这个方法释放外部资源、watches、event handlers。



> `$ctrl` 是控制器默认的别名，如果没有显示的指定。