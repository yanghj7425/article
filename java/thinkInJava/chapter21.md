# <center> 第21章 并发 </center> #
> 用并发解决的问题大体上可分为“速度”和“设计可管理性”两种。

## 并发的多面性
> 在单处理器上编写并发程序应该比所有程序都顺序执行的代价还要大，因为其中要添加*上下文切换*的代价。
- 实现并发最直接的方式是在操作系统级别使用**进程**。进程是运行在它自己的地址空间内的自包容的程序。
- Java 所使用的这种并发系统会共享诸如内存和 I/O 这样的资源，因此编写多线程的程序最基本的困难在协调不同线程驱动的任务之间对这些资源的使用。
- Java 的线程机制是抢占式的，调度机制会周期性的中断线程，将上下文切换到另外的线程中，从而为每个线程都提供时间片。

## 基本的线程机制
> 并发编程是的我们可以将程序划分为多个分离的、独立运行的任务。
```java
public class LiftOff implements Runnable {
    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++; // 可用于区分任务的多个实例 

    public LiftOff() {
    }
    public String status(){
        return "#" + id + "(" + (countDown > 0 ? countDown : "Over") + ")" ;
    }

    @Override
    public void run() {
        while (countDown -- > 0){
            System.out.println(status());
            Thread.yield(); // 暂时让出资源， vi. 退让、屈服、让位
        }
    }

    public static void main(String[] args) {
        LiftOff launch = new LiftOff(); //实例 1
        launch.run();
        launch = new LiftOff(); // 实例 2
        launch.run();
    }
}
```
- **注意：**当从 Runnable 导出一个类时，必须实现 run() 方法，但是这个方法并无特殊之处，不会产生任何内在线程能力。要实现线程行为，必须显示的附着在一个线程类上。
    1. 通常是将 Runnable 对象交给 Thread 构造器。

## Thread 类
> Thread 对象调用 start() 方法为该线程执行必需的初始化操所，然后调用 Runnable 的 run() 方法，以便在这个新线程中启动该任务。
```java
    public static void main(String[] args) {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("start method come back!");
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            Thread.yield();
        }
    }
```
1. start() 方法迅速返回，然后由另外一个线程执行 new LiftOff() 所创建的任务。
2. 程序会同时运行 main() 和 LiftOff.run() 两个方法。

### 使用 Executor
> Java SE5 的  java.util.concurrent 包中的执行器将管理 Thread 对象，从而简化了并发编程。
1. CachedThreadPool 将为每个任务都创建一个线程。
    - CachedThreadPool 在程序执行过程中通常会创建与所需数量相同的线程，然后再回收线程是停止创建新线程，因此它是合理的 Executor 的首选。
2. FixedThreadPool 使用有限的线程集来执行所提交的任务。
    - 不用为每个任务都固定的付出创建线程的开销。
3. SingleThreadExecutor 想是数量为 1 的 FixdThreadPool。
    - SingleThreadExecutor 会序列化所有提交给他的任务，并维护它自己的悬挂任务队列。
    - 可以确保在任意时刻在任何线程中都只有唯一的任务在运行。

```java
public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
        exec.execute(new LiftOff());
    }
    exec.shutdown();  // 防止新任务被提交给这个 Executor ，当前线程将继续运行在 shutdown() 被调用之前提交的所有任务。
}
```
### 从任务中产生返回值
> Runnable 接口是独立工作的不产生返回值，如果要产生返回值可以实现 Callable 接口。Callable 接口是一个具有类型参数的接口，类型参数是方法 call() 的返回值，必须使用 ExecutorService.submit() 方法调用。
```java
public class TaskWithResult implements Callable<String>{
    private  int id;
    public TaskWithResult(int id){
        this.id = id;
    }
    @Override
    public String call() throws Exception {
        return "This Task id  is :" + id;
    }
}

public class TaskWithMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> futures = new ArrayList<Future<String>>();
        for (int i = 0; i < 5; i++) {
            futures.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }
    }
}

```
- submit 方法会返回一个具有 call() 方法返回值参数化的 Future 对象。
- 可以使用 isDone() 方法来判断 Future 是否完成；也可以直接调用 get(),此时 get() 方法是阻塞的。

### 优先级
> 线程的优先级将该线程的**重要性**传递给调度器。优先级不会导致死锁，优先级低的线程仅仅意味着执行的频率较低。


### 让步
通过使用 yield() 方法可以暗示：此时 CPU 可以执行其他线程的任务。

### 后台线程 (daemon)
> 程序运行的时候在后台提供一种通用服务的线程，这种线程并不是程序中不可缺少的部分。当所有非后台线程结束时，程序也终止，同时会杀死所有后台线程。
- 必须在线程启动之前调用 setDaemon() 方法。
```java
Thread t = new Thread(new Runnable(){
    public void run(){
        /**/
    }
});
t.setDaemon(true); // 设置后台线程
t.start();
```
- 可以用 isDaemon() 方法来判断是否为一个后台线程。
- 一个后台线程创建的任何线程，都自动被设置成后台线程。
- 后台线程在不执行 finally 子句的情况下就会退出。当最后一个非后台线程终止时，非后台线程会突然终止， JVM 会关闭所有所有的后台线程，而不会出现任何你希望的退出方式。

### 编码的变体
- 在线程的构造函数中调用 start() 方法。
```java
public class SimpleThread extends Thread{ // 继承类
    SimpleThread(){
        start(); //当外部使用 new SimpleThread() 时，就开始启动线程。
    }
}

public SelfManage implements Runnable{ // 实现接口
    private Thread t = new Thread(this);
    public SelfManage(){
        t.start();
    }
}

//在内部类中封装： 略。
```

### 捕获异常 P<sub>672</sub>
Thread.UncaughtExceptionHandler 允许在每一个 Thread 对象上附着一个异常处理器。
- Thread.UncaughtExceptionHandler.uncaughtException() 会在线程因未捕获的异常而临近死亡的时候而被调用。
    ```java
    class HandlerThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            return t;
        }
    }
    public class CaptureUncaughtException {
        public static void main(String[] args) {
            ExecutorService service = Executors.newCachedThreadPool(new HandlerThreadFactory());
            service.execute(new ExceptionThread());
        }
    } //冷
    ```

## 共享资源
- Java 的线程机制是抢占式的，所以在任何非原子性操作的地方都会失去对资源的使用。
```java
    public int nextInt() { // 可以添加 synchronize  关键字来避免多个任务同时进入
        ++ currentEvent; // Danger, The use of cpu maybe lost 
        ++ currentEvent;
        return currentEvent;
    }
```

### 解决资源共享
对于并发，需要以某种方式来防止两个任务同时访问同一个资源。
- 采用序列化资源的方案，在代码前面加上一条锁语句。
- Java 以关键字 synchronize 防止资源冲突提供支持。
    - 要控制对资源的访问，先得把他包装到一个对象。然后把所要访问这个资源的方法标记为 synchronize。
- 所有对象都自动含有单一的锁（监视器）。在对象上调用任意的 synchronize 标记的方法时，此对象会被枷锁，这时该对象上的其他 synchronize 方法。

### 显示使用 Lock 对象
Lock 对象必须被显示的创建、锁定和释放。<br>
ReentrantLock ： 允许尝试获取锁，但是最终未能获取到锁。这样就如果获取不到锁就可以先去执行其他任务。
```java
public class MutexEventGenerator extends IntGenerator {
    private int currentEvent = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int nextInt() {
        lock.lock();
        try {
            ++ currentEvent;
            ++ currentEvent;
            return currentEvent; // return 语句一定要在 try 里面，以确保 unlock 不会过早发生，而将数据暴露给第二个任务。
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EventChecker.test(new MutexEventGenerator());
    }
}
```
-  也可使用 lock.tryLock() 方法获取锁，获取到锁时会返回一个 true。可以添加时间参数，以尝试获取锁一段时间。
```java
    public void tryLockMethod(){
        boolean captured = lock.tryLock(); 
        try{
            // do something ……         
            }finnally{
                if(captured){
                    lock.unlock();
                }
            }
    }
```

### 原子性与易变性
- 原子性：一但操作开始，一定可以在上下文发生切换之前执行完成。
- 原子操作可由线程机制来保证其不可中断，专家级的程序员可以利用这一点来编写无锁的代码。
- 同步机制强制在处理器系统中，一个任务做出的修改必须应用中是可视的。
    - volatile 关键字确保了可视性。<br>

[实例代码](./src/com/yhj/chapter21/concurrency/AtomicityTest.java)<br>
>程序中 `public int getValue()` 方法，return i 是原子操作，但是缺少同步，使其可以在不稳定的情况下被读取。此外变量 i 也不是 volatile 的，也存在可视性的问题。getValue 方法和 envnIncrement 方法必须是 synchronized 的。

### 原子类
Java SE5 引入了如 AtomicInteger、AtomicLong、AtomicRefrence等特殊的原子性变量类。

### 临界区
**临界区：** 防止多个线程同时访问方法内部的部分，而不是防止访问整个方法。
- 使用 synchronized 关键字定义。
```java
synchronized(syncObject){
    // This code can be accessed by only one task at a time 
}
```
- 使用同步控制块可以使多任务访问对象的时间性能得到显著提高。

### 在其他对象上同步
synchronized 块必须给定一个在其上进行同步对象，最合理的方式使，使用其方法正在被调用的对象： synchronized(this)。

> 两个任务可以同时进入同一个对象，只要这个对象上的方法使在不同的锁上同步即可：
[实例代码](./src/com/yhj/chapter21/concurrency/SyncObject.java)
```java
 public synchronized void f() { //在 this 上同步
        for (int i = 0; i < 5; i++) {
            System.out.println("f()");
            Thread.yield();
        }
    }

    public void g() {
        synchronized (syncObject) {  // 在  syncObject 上同步
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        }
    }
```

### 线程本地存储
防止任务在共享资源上产生冲突的第二种方式使根除变量共享。线程本地存储是一种自动化的机制，可以为使相同变量的每个不同线程都创建不同的存储空间。
[实例代码](./src/com/yhj/chapter21/concurrency/ThreadLocalVariableHolder.java)。
-创建 ThreadLocal 时只能通过 get() 和 set() 来访问该对象与线程关联的副本内容。