# <center> 第18章 Java I/O系统 </center>

## File 类
> `File (文件)` 类这个名字有一定的误导性。它即能代表一个目录名称，又能代表一个目录下的一组文件名称（调用 list 方法，返回一个字符数组）。
```java
    File file = new File(".");
    String [] list = file.list();
```
> 在方法中可使用内部匿名类，如：
```java
    public class DirList2{
        public static FilenameFilter filter(final String regex){
            return new FilenameFilter(){ 
                /**使用内部匿名类**/
                ...
            };
        }
        public static void main(String [] args){
            File path = new File(".");
            path.list(filter(args[0])); 
        }
    }

```

## 输入和输出
> 任何`InputStream`或`Reader` 派生而来的类都有名为`read()`的方法，用于读单个字节或者字节数组；从`OutputStream`或`Writer`派生而来的类都有`write()`方法，用于写单个字节或者字节数组。
### InputStream 类型
    > 表示从不同数据原产生的输入类: 字节数组、String 对象、文件、管道、其他流组成的序列、其他数据源。
    - `StringBufferInputStream`: 将 String 转换成 `InputStream`。
    - `FileInputStream`: 从文件中读取信息。
    -  ....
### OutputStream 类型
    > 该类别决定了输出要去的目标: 字节数组、文件、管道。
    - `FileOutputStream` 将信息写入文件。

## 添加属性和有用的接口
> `FilterInputStream` 和 `FilterOutputStream` 分别来自 I/O 类库中的基类`InputStream` 和`OutputStream`。

### 通过 `FilterInputStream` 和 `FilterOutputStream` 读取数据
    - `DataInputStream` 允许读取不同的基本数据类型及 String 对象（所有的方法都以`read`开头，如:`readByte()、readFloat()`）。
    - `DataInputStream` 用`readByte()`时，任何字节值都是合法的，不能用返回值来判断是否结束。可使用`avaliable()`方法查看还有多少个可存取的字符。
    - 当使用 `readUTF()` 和`writeUTF()`时，只适用于 Java 程序之间的交互。
### 通过`FilterOutputStream`向`OutputStream`中写入数据
    - 与`DataInputStream`对应的是`DataOutputStream`，可以将各种基本数据类型以及 String 对象格式化输入到“流”中。
    - `PrintStream` 和`PrintWriter` : 未完全国际化，不能以平台无关的方式处理。
## Reader 和 Writer
- `InputStream` 和 `OutputStream` 在面向 **字节** 的 I/O 中提供极有价值的功能。
- `Reader` 和 `Writer` 则提供兼容`Unicode`与面向 **字符** 的 I/O 功能。
- `InputStreamReader` (适配器类)：把`InputStream`转换成`Reader`。
- `OutputStreamReader` : 把`OutputStream`转换成`Writer`。
### 数据的来源和去处
 > `java.util.zip`就是 **面向字节** 的。最佳实践是： 尽量使用面向字符的`Reader`和`Writer`，当程序无法编译成功的时候，就换为 **面向字节** 的`InputStream`和`OutputStream`。

 ## 标准 I/O

 ### 从标准输入中读取

 ```java
    public class Echo{
        public static void main(String[] args) throws IOException{
            // 从定向系统输入
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))  
        }
    }
 ```
  - **注意：** 系统输入和大多数流一样，通常应该对他进行缓冲；I/O 重定向操作的是 **字节流**，不是字符流。

  ## 进程控制
  > 可以通过 Java 程序执行系统命令，并捕获命令执行的结果。
```java
    public class OSExecute {

        public static void command(String command) {
            boolean err = false;
            Process process = null;
            try {
                process = new ProcessBuilder(command.split(" ")).start();
                BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String s;
                while ((s = results.readLine()) != null) {
                    System.out.println(s);
                }
                BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((s = errors.readLine()) != null) {
                    System.err.println(s);
                    err = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                command("CMD /C " + command);
            }

        }
    }

    public class OSExecuteDemo {
        public static void main(String[] args) {
            OSExecute.command("dir"); // javap OSExecuteDemo
            System.out.println("你好3");
        }
    }


```

## 新 I/O
> JDK 1.4 的 `java.nio.*` 包中引入的新的 Java I/O 类库，其目的在于提高速度。
- 速度的提高来自使用的结构更接近于操作执行I/O的方式： **通道和缓冲器**；唯一直接和通道交互的缓冲器是 `ByteBuffer`。
- `RandomAccessFile` 类速度比较慢，不推荐使用。
```java
public class GetChannel {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        try {
            FileChannel fileChannel = new FileOutputStream("D:\\s.txt").getChannel();
            fileChannel.write(ByteBuffer.wrap("hello".getBytes()));
            fileChannel.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

```
- 一旦调用`read()` 来告知`FileChannel`向`ByteBuffer`存储字节，就必须调用缓冲器上的`flip()`。
```java
    FileChannel in = new FileInputStream("").getChannel();
    FileChannel out = new FileOutputStream("").getChannel();
    ByteBuffer buffer  = ByteBuffer.allocate(1024);
    while(in.read(buffer) != -1){
        buffer.flip();
        out.write(buffer);
        buffer.clear();
    }
```
- **注意：** 在得到`FileChannel`之后可以使用特殊的方法`transferTo()`和`transferFrom()`。
### 获取基本类型
> 尽管 `ByteBuffer`只能保存字节类型的数据，但是它具有可以从其容纳的字节数据中产生各种不同**基本类型**的方法。
```java
    ByteBuffer bb = ByteBuffer.allocate(1024);
    bb.rewind(); // 返回到数据最开始的部分
    bb.asChartBuffer().put("hello");
    char c;
    while((c = bb.getChar()) != 0){
        print(c);
    }
    bb.rewind();
    bb.asShortBuffer().put((short)1323);
    print(bb.getShort());
```
###  视图缓冲器
> 视图缓存器（view buffer），可以让我们通过某个特定的基本数据类型的视图窗查看底层的 `ByteBuffer`。
```java
    ByteBuffer bb  = ByteBuffer.allocate(1024);
    IntBuffer ib = bb.asIntBuffer(); //创建一个视图
    /* 可通过 put 和  get 方法往 视图中添加数据*/
```
### 字节存放次序
ByteBuffer 是以高位优先的形式存储的，网络上传输的时候也是高位优先。可以使用参数：ByteOrder.BIG_ENDIAN、
      ByteOrder.LITTLE_ENDIAN 改变。

### 缓冲器细节
Buffer 由数据和可以高效地访问及操作这些数据的四个所以组成：mark (标记)、position (位置)、limit (界限) 和 capacity (容量)。

### 内存映射文件
内存映射文件允许我们创建和修改那些因为太大而不能放入年内存的文件。就可以假定整个文件都放在内存中。

### 文件加锁
JDK 1.4 引入了文件加锁机制，允许我们同步访问某个共享资源文件。java的文件加锁直接映射导了本地操作系统的加锁工具。