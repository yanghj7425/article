

# angular 第一课

## 起步
1. 安装 `node` 环境
2. 替换淘宝镜像： `npm config set registry http://registry.npm.taobao.org/`<br>
   替换为官方镜像: `npm config set registry https://registry.npmjs.org/`<br>
   查看镜像地址：` npm config get  registry`

3. 安装手脚架工具 `npm install -g @angular/cli` 工具只需安装一次 即可
4. 创建项目: `ng new my-app`
5. 启动项目：`ng serve --open`

## 添加组件
1. app.module.ts
    >定义AppModule，这个根模块会告诉Angular如何组装该应用
    - `import` 表示引入项目所要运行的组件
    - `@NgModule` 表示引入注册当前模块运行的模块
    - `imports` 表示引入当前模块运行依赖的其他模块
    - `providers` 表示当前项目运行要访问的服务
    - `bootstrap` 项目启动默认的视图，这里的视图要和`index.html`里面引用的组件一致
2. 创建组件
    > 命令 `ng g component components/news` 会在目录 `components` 下新建 `news` 模块
    - 命令 `ng g component` 会在制定目录下创建模块。并且把创建的模块新加入到 `app.module.ts`的对应位置。
    1. 文件 `news.component.ts` 
        - `@Component` 
            - `selector` 引用组件的标签如：`<app-news><app-news/>`
            - `templateUrl` 组件对应的 html 文件
            - `styleUrls` 对应的 css 样式
        - 类 `NewsComponent` 实现业务代码
3. 定义数据与数据引用
    > 数据定义
    ```typeScript
    export class NewsComponent implements OnInit {
        title = 'Hello Angular 4.x';
        msg: any;  // 在初始化函数中初始化
        constructor() { }
        //初始化函数
        ngOnInit() {
            this.msg = '这是一个news组件';
        }
    }
    ```
    > 在 `news.component.html`文件中
     - 在html页面用`{{title}}`,`{{msg}}`引用数据

4. 绑定属性
    > 数据定义
    ```typeScript
       public id = '123';
       public title = 'hello';
    ```     
    > 引用绑定
    ```html
        <div id={{id}}>
            这是一个绑定属性,方法1
        </div>

        <div [title]="title">
            这是一个绑定属性 方法2
        </div>
    ```
5. 数据解析
- 解析 `HTML` <br>
    定义或者绑定的数据是 `html` 类型的时候在页面可以用 `<span [innerHtml]='ht'></span>` 方法解析，‘ht’为绑定的数据。 
- 解析对象 <br>
    如下定义一个对象：
    ```typeScript
        public obj={
            name:'张三'
        };
    ```
    引用对象：

        ```html
        {{obj.name}}
        ```
- 解析 `List` <br>
    定义或者绑定的数据类型是`list`类型时，可以用 `*ngFor` 的方法迭代。<br>

    定义 `list`

    ```typeScript

        public list = [];
        public list2: any[];
        public list3: any[];  //每一个List时一个对象

        /*初始化函数*/
        ngOnInit(){
            this.list = ['11', '22', '33'];
            this.list2 = ['数学', '语文', '外语'];
                this.list3 = [
                { 'title': '111111111111' },
                { 'title': '222222222222' },
                { 'title': '3333333333333' }
            ];
        }

    ```

    引用 `list` <br>
    ```html
        <!--使用 *ngFor 循环遍历 List-->
        <ul>
            <li *ngFor="let item of list">
                {{item}} ngFor
            </li>
        </ul>

        <!--遍历List 并获取索引-->

        <ul>
            <li *ngFor="let item of list2; let key = index">
                {{key}}:{{item}}
            </li>
        </ul>
    ```
    > 代码 `let item of list` 表示：如果定义一个item 存在于list 中。 `let item of list2; let key = index` 其中 ` let key = index` 表示把下标值赋给变量 `key`。

- 条件判断 *ngIf <br>
    在HTML页面上，条件判断指令来执行某些操作。<br>
    ```html
        <p *ngIf="list.length > 3">这是ngIF判断是否显示</p>
    ```

- 事件 <br>
    > 当出发一个点击事件的时候，调用函数执行某些操作。<br>

    声明执行事件的函数<br>

    ```typeScript      
        export class NewsComponent implements OnInit {
            msg: any;
            constructor() { }

            ngOnInit() {

            }
            /*声明事件函数*/
            getData() {
                alert(this.msg);
            }
        }
    ```
    绑定事件 <br>
    - click 事件
        ```html
        <button class="button" (click) = "getData()">
            点击按钮触发事件
        </button>
        ```
    - keyup 事件
    > 在 html 中定义一个按键事件

    ```html
        <input type="text" (keyup)="keyUpFn($event)">
    ```
    > 在 typeScript 中 声明函数这里就这样可以获取到事件对象。
    ```typeScript
        keyUpFn(event) {
            console.log(event);
        }
    ```

双向数据绑定<br>
>  使用双向数据绑定可以把已经改变的变量同步到所有引用了该变量的地方。<br>

```html
    <input type="text" [(ngModel)]="search">

    {{search}}

    <button (click)=getSearch()>获取输入框的值</button>
```
>在model中有一个变量`search` ，html 中可以使用 `[(ngModel)]="search"` 来进行model变量的绑定。上面绑定在一个input 标签中。当使用了`[(ngModel)]`进行绑定的时候，只要变量`search`发生了改变，就会自动同步到所有引用了该变量的地方。