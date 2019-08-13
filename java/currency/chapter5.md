# Future 模式
future 模式，时多线程中一种常见的模式，核心思想时异步调用。


## Future 模式的简单实现
- Data.java

    这是一个核心的接口，表示需要返回的数据。
```java
public interface Data {
    String getResult();
}

```
- RealData.java

    真实的数据接口，此处可能会有 i/o 发生，可能会比较慢，用 sleep 模拟耗时操作。  
```java
public class RealData implements Data {
    protected final String result;

    public RealData(String param) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(param);
            try {
                // 模拟获取数据过程中的耗时操作
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}


```


- FutureData.java 

    构造 Future 数据，调用后会立即返回，但是数据可能还没有填充。FutureData 主要用于提取真实地 Future 数据。

```java

public class FutureData implements Data {
    protected RealData realData = null;
    private boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();
    }


    @Override
    public synchronized String getResult() {
        // 如果没有准备好数据则等待
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 此处 返回 realData 的结果
        return realData.getResult();
    }
}
```

- Client.java

    模拟客户端程序
```java
public class Client {
    public Data request(final String queryStr) {
        final FutureData futureData = new FutureData();
        new Thread(() -> {
            RealData realData = new RealData(queryStr);
            futureData.setRealData(realData);
        }).start();
        return futureData;
    }
}
```


- Launch.java

    程序入口
```java
public class Launch {
    public static void main(String[] args) {
        Client client = new Client();
        // 客户端发送 请求
        Data data = client.request("hello");
        System.out.println("request returned and stand by data finished");

        System.out.println(data.getResult());
        
    }
}
```

## JDK 中的 Future 模式

- RealData.java
```java
public class RealData implements Callable<String> {
    private String para;

    public RealData(String str) {
        this.para = str;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            Thread.sleep(500);
        }
        return sb.toString();
    }
}
```

- Launch.java

```java

/**
 * jDK  中的 future 模式，在 JDK 中已经准备了一个 Future 模式。
 * 有一个重要的 RunnableFuture 接口，其实现了 Runnable  和 Future 两个接口，
 * RunnableFuture 接口有一个默认的实现 FutureTask . FutureTask  内部有一个 Sync ,
 * 一些实质性的工作都是委托给 Sync 完成。
 */
public class Launch {
    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new RealData("ooo"));
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(futureTask);
        System.out.println("finish submit");
        try {
            // sleep 模拟其他操作
            Thread.sleep(1000);
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }

}

```
 采用 submit 方式提交的任务会吞异常，不过 submit 返回一个 future 如：
 ```
 Future  future = servce.submit(task); 
 future.get(); 
 ```
则可以捕获异常信息。或者改用 `service.execute(task)` 也可。