# <center> 开始 AngularJS </center>

## 起步
[实例代码](./src/chapter1.1.html)<br>
解释：
1. 引入 angular.js  文件。
2. 把一个 html 文件用 ng-app 指令定义为一个 angular 应用。
    ```html
        <html ng-app="myApp">
    ```
    - 应用名称为 ： myApp。
3. 使用 ng-init 初始化变量。
    ```html
        <body ng-init="name = 'world'">
    ```
    - 初始化 name 属性为字符串 ： world。
4. 用 ng-model 指令绑定从模型到 html 的数据。 
    ```html
       <input ng-model="name" />
    ```
    - ng-model 绑定的数据会根据 name 的值发生变化而动态的修改。
5. 现实当前模型里面的数据
    ```html
        <span>Hello, {{ name }}</span>
    ```

## angular 通常的设计方式
[实例代码](./src/chapter1.2.html)<br>
解释：
1. angular.module(...): 使用一个数组作为第二个参数来创建一个新的 model，这个数组被用来提供模块所需要的依赖。
2. .service(...): 创建一个 angular service 然后返回一个 module 链。
3. .controller(...): 创建一个 angular controller 然后返回一个 module 链。
4. .config(...)：使用这个方法来配置模块加载的时候需要执行的工作。
5. .run(...)：确保代码在启动运行时携带一个数组作为参数。使用这样的方法来注册一个 work，使其在注册器加载完成之后执行。
        - 第三个参数时一个具体的函数，第一个和第二个参数让 Angular 知道第三个参数指定的函数执行的时候所需要的依赖关系。
6. ng-class: ngClass 指令可以动态的设置一个 class。
7. ng-cloak: 避免没有被渲染的 angular 模板在 angular 完全加载之前被现实。
8. ng-controller：声明一个控制器。
9. ng-repeat：迭代集合并且克隆现实 DOM 模板。