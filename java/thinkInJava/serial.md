# 序列化与反序列化

## 概念

- 序列化：把对象转换为字节序列的过程称为对象的序列化。在传递和保存对象的时候保证对象的完整性。
- 反序列化：把字节序列恢复为对象的过程称为对象的反序列化。通过反序列化来重建对象。

## Serializable 接口

如果需要序列化的对象必须实现这个接口，否则会抛出 NotSerializableException 异常。实现了此接口的 java 类在序列化和反序列化时, ObjectOutputStream 会调用 writeObject() 方法序列化，ObjectInputStream 会调用 readObject() 方法反序列化。

# 小样示例

这里就模拟网络传输，给一个例子

- SerialUserService 接口

```java
public interface SerialUserService {
    void objectTransferBySocket();
}

```

- SerialUserServiceImpl 实现类

```java
public class SerialUserServiceImpl implements SerialUserService,Serializable {

    private static String name;
    private String sex;
    private String age;

    private static final long serialVersionUID = -8016197322478056228L;

    @Override
    public void objectTransferBySocket() {
        System.out.println("this is a demo to test object`s serializable transfer");
    }

    public SerialUserServiceImpl(String name, String sex, String age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    // omit getter and setter ...

}
```

- ServerTest 测试类

```java
public class ServerTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(9533);
        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(in);
        SerialUserService serialUserService = (SerialUserService) ois.readObject();
        serialUserService.objectTransferBySocket();
        System.out.println(serialUserService.toString());

    }
}
```

- ClientTest 测试类

```java

public class ClientTest {

    public static void main(String[] args) throws IOException {
        // 要连接的服务端IP地址和端口
        String host = "127.0.0.1";
        int port = 9533;
        // 与服务端建立连接
        Socket socket = new Socket(host, port);
        // 建立连接后获得输出流
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        SerialUserService serialUserService = new SerialUserServiceImpl("name_label", "sex_label", "age_label");
        oos.writeObject(serialUserService);
        System.out.println(serialUserService.toString());
        outputStream.close();
        socket.close();
    }
}
```

## 结果

- ClientTest

```
Connected to the target VM, address: '127.0.0.1:14292', transport: 'socket'
SerialUserServiceImpl{name='name_label', sex='sex_label', age='age_label'}
Disconnected from the target VM, address: '127.0.0.1:14292', transport: 'socket'

Process finished with exit code 0

```

- ServerTest

```
Connected to the target VM, address: '127.0.0.1:14287', transport: 'socket'
Disconnected from the target VM, address: '127.0.0.1:14287', transport: 'socket'
this is a demo to test object`s serializable transfer
SerialUserServiceImpl{name='null', sex='sex_label', age='age_label'}

Process finished with exit code 0

```
