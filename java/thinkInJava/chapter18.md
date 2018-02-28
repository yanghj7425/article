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
        public static FileNameFilter filter(final String regex){
            return new FileNameFilter(){ 
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
1. InputStream 类型
    > 表示从不同数据原产生的输入类: 字节数组、String 对象、文件、管道、其他流组成的序列、其他数据源。
    - `StringBufferInputStream`: 将 String 转换成 `InputStream`。
    - `FileInputStream`: 从文件中读取信息。
    -  ....
2. OutputStream 类型
    > 该类别决定了输出要去的目标: 字节数组、文件、管道。
    - `FileOutputStream` 将信息写入文件。

## 添加属性和有用的接口
> `FilterInputStream` 和 `FileOutputStream` 分别来自 I/O 类库中的基类`InputStream` 和`OutputStream`。

1. 通过 `FilterInputStream` 和 `FileOutputStream` 读取数据
    - `DataInputStream` 允许读取不同的基本数据类型及 String 对象（所有的方法都以`read`开头，如:`readByte()、readFloat()`）。
    - `DataInputStream` 用`readByte()`时，任何字节值都是合法的，不能用返回值来判断是否结束。可使用`avaliable()`方法查看还有多少个可存取的字符。
    - 当使用 `readUTF()` 和`writeUTF()`时，只适用于 Java 程序之间的交互。
2. 通过`FilterOutputStream`向`OutputStream`中写入数据
    - 与`DataInputStream`对于的是`DataOutputStream`，可以将各种基本数据类型以及 String 对象格式化输入到“流”中。
    - `PrintStream` 和`PrintWriter` : 未完全国际化，不能以平台无关的方式处理。
## Reader 和 Writer
> `InputStream` 和 `OutputStream` 在面向 **字节** 的 I/O 中提供极有价值的功能，`Reader` 和 `Writer` 则提供兼容`Unicode`与面向 **字符** 的 I/O 功能。
- `InputStreamReader` (适配器类)：把`InputStream`转换成`Reader`。
- `OutputStreamReader` : 把`OutputStream`转换成`Writer`。
1. 数据的来源和去处
 > `java.util.zip`就是 **面向字节** 的。最佳实践是： 尽量使用面向字符的`Reader`和`Writer`，当程序无法编译成功的时候，就换为 **面向字节** 的`InputStream`和`OutputStream`。

 ## 标准 I/O

 1. 从标准输入中读取

 ```java
    public class Echo{
        public static void main(String[] args) throws IOException{
            // 从定向系统输入
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))  
        }
    }
 ```

  - **注意：** 系统输入和大多数流一样，通常应该对他进行缓冲；I/O 从定向操作的是 **字节流**，不是字符流。

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