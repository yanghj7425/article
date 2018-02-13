
# JQuery 学习 1

作为一个即将毕业大四的人来说 不会使用 JQuery  真是一件非常悲催的事情了。

一步一步来 ~
## 初探
创建一个 js 文件 1.js：
```
$(document).ready(function() {
	alert('hello');
	console.log("hello I`m here , where are you ");
	$('div.poem-stanza').addClass('highlight'); //通过Class来访问
	// $('#fred').addClass('highlight'); //通过 ID 来访问
});
```
这里其实是调用了一个匿名函数（我这么叫）， ` $(document).ready( parm );`  这里的参数可以传入一个已经定义好的方法。

> 上面的 demo  是给 div 标签中 class 属性为 poem-stanza 的域 和 ID 值为 fred 的域设置高亮， 也有相应的 removeClass 方法。


## 元素选择

- `$('p')` 取得文档中所有的段落<br>
- `$('#some_id')`取得所有*ID*为`some_id`的元素<br>
- `$('.some_class')`取得所有类为`some_class`的元素<br>		 



#### 元素的 `hover`属性
当鼠标移入的的时候附加的CSS效果
实例CSS 如下：
```css
.selected {
  font-weight: bold;

}
.selected :hover{
   background: blue;
}
```
通过Jquery动态添加`selected`样式后鼠标移入后所选ID的背景色就会被换为蓝色。

JQuery 方法如下：

```javascript
 $("#switcher button").on('click',function(){
        $(this).toggleClass('selected');
        $('body').removeClass();
        if(this.id == 'switcher-large'){
            $('body').addClass('large');
        }else if(this.id == 'swithcer-narrow'){
            $('body').addClass('narrow');
        }
    });

```
为元素ID为`switcher` 下的所有`<button>`组件添加`click`事件，通过判断id来确定具体的执行操作。`toggleClass('class_name')` 方法可以判断当前Id是否具有`class_name`样式来执行具体的添加和删除操作。

#### JQuery 的`.hover()`方法

`JQuery`的`.hover()`方法接受两个函数参数，第一个是鼠标移入时执行，第二个是鼠标移除是执行。

```javascript
   $("#switcher").hover(function() {
        console.log('mouseEnter');
    }, function() {
        console.log('mouseOut');
    });
```
>当鼠标移入移出id为`switcher`的元素时，`Console`口输相应信息

#### `JQuery` 冒泡事件
>在Jquery获取事件时，首先是最顶层元素获取，并依次往下传递；在处理事件的时，首先当前发生事件的元素捕获处理并依次往上传递。
 *所以在响应事件处理的时候应加上判断*

实例：

```javascript
 $('#switcher button').click(function(event) {
        if(event.target == this){ //这样就保证了只有id为switcher的元素才会响应
            console.log(event.target.id);
        }
    });
```

>这样就只有出发事件的元素才会响应。<br>`.target`方法可以获取`DOM`中首先获取到事件的元素；`this` 引用的是处理`DOM`事件的元素。 