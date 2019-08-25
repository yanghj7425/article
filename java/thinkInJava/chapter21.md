# <center> 第 21 章 并发 </center>

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

> Java SE5 的 java.util.concurrent 包中的执行器将管理 Thread 对象，从而简化了并发编程。

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

- 也可使用 lock.tryLock() 方法获取锁，获取到锁时会返回一个 true。可以添加时间参数，以尝试获取锁一段时间。

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

> 程序中 `public int getValue()` 方法，return i 是原子操作，但是缺少同步，使其可以在不稳定的情况下被读取。此外变量 i 也不是 volatile 的，也存在可视性的问题。getValue 方法和 envnIncrement 方法必须是 synchronized 的。

### 原子类

Java SE5 引入了如 AtomicInteger、AtomicLong、AtomicRefrence 等特殊的原子性变量类。

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
> [实例代码](./src/com/yhj/chapter21/concurrency/SyncObject.java)

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

- 创建 ThreadLocal 时只能通过 get() 和 set() 来访问该对象与线程关联的副本内容。

## 终结任务

### 在阻塞时终结任务

#### 线程状态

1. 新建：当线程被创建时，只会短暂的停留在这个状态。此时已经分配了必要的系统资源，并执行了初始化。此刻的线程已经有资格获取到 CPU 的时间了，之后调度器将把这个线程变为可运行状态或阻塞状态。
2. 就绪：只要调度器分配时间片给线程，就可以运行。
3. 阻塞：线程能够运行，但有某个条件阻止他的运行。当线程处于阻塞状态时调度器将忽略此线程，不会分配给线程任何 CPU 时间。
4. 死亡：不再是可调度的。通常是从 run 方法返回，但任务线程还可能是被中断。

#### 进入阻塞状态

- 一个线程进入阻塞状态一帮有一下原因：
  1. 调用 sleep() 方法使任务进入休眠，这样任务在一定时间不会运行。不会释放已经获取的资源
  2. 通过 wait 使线程挂起。直到线程得到了 notify() 或 notifyAll() 消息（java ES5 的 java.util.concurrent 类库中等价的 signal() 或 signalALl() 消息），线程才会进入就绪状态。
  3. 等待某个输入/输出完成。
  4. 任务视图在某个对象上调用其同步控制方法，但是对象锁不可用，因为另外一个任务已经获取了这个锁。
- suspend() 和 resume() 可能会造成死锁，已经废弃。
- stop() 不会释放已经获取的锁，而且如果线程处于不一致的状态，其他任务也可以在这种状态下浏览并修改它们。允许在线程 A 中调用线程 B 的 stop 方法。

### 中断

[知乎问题](https://www.zhihu.com/question/41048032?sort=created): 介绍了 interrupt 和 interrupted 。

- Thread 类包含 interrupt() 方法，因此可以终止一个阻塞的任务。
- Executor 上调用 shutdownNow()，会发送一个 interrupt() 调用给它启动的所有线程。
- Executor 使用 submit 提交，返回一个 Furtre<?>，对其调用 cancel() 并传入参数 true 则会单独取消某一个线程。[实例代码](./src/com/yhj/chapter21/concurrency/Interrupting.java)<br>

> **注意：** 不能中断正在试图获取 synchronized 锁或试图执行 I/O 的线程。第 18 章，介绍的 nio 类提供了更人性化的支持，被阻塞的 nio 通道会自动响应中断。

#### 互斥阻塞

在一个对象上调用 synchronized 方法，而这个锁已经被其他对象获得，那么这个任务将被挂起，直至该锁可以获得。

- 同一个互斥可以被一个任务多次获得。[实例代码](./src/com.yhj.chapter21.concurrency.MultiLock.java)
  - 由于任务第一次调用时已经 synchronized 方法已经获得了 this 对象的锁。当调用其他 synchronized 方法时，这个任务已经持有锁了。
- interrupt 可以打断被互斥所阻塞的调用。
  - 只要任务以不可中断的方式阻塞，就有锁住程序的可能。在 ReentrantLock 上阻塞的任务具有可以被中断的能力。(P<sub>700</sub>)

### 检查中断

- 当在线程上调用 interrupt 方法时，中断发生的唯一时刻时在任务进入到阻塞操作中，或者已经在阻塞操作中。
  - 除非不可中断的 synchronized 方法或 I/O 阻塞。
- 通过 interrupt() 设置中断状态，在 run() 方法中 `while(! Thread.interrupted()) {}` 检测中断标志。
  - interrupted() 不仅可以检测 interrupt() 是否被调用，还可以清除中断状态。
    - 中断状态可以确保并发结构不会就某个任务被中断这个问题通知你两次。
- 设计来响应 interrupt() 的类必须建立某种策略，来确保保持一至的特性。通常意味着所有需要清理的对象后面都必须紧跟`try-finally`子句，从而使得无论 run() 如何退出，清理都会发生。
  - 后台(daemon) 进程有例外，其可能会被突然终止。

## 线程之间的协作

当任务协作时，关键的问题时握手。使用了相同的基础特性： 互斥，来实现。互斥能确保只有一个任务可以响应某个信号，这样就可以根除某些竞争条件。在互斥之上，提供了另一种途径：将自身挂起。

- 使用 Object 的 wait 和 notify 方法安全实现。

### wait() 和 notify()

wait() 使你可以等待某个条件发生变化，而改变这个条件已经超出当前方法的控制能力。

- 只有 notify/notifyAll 或时间到期，任务才会被唤醒。
- 只能在同步块里面调用 wait()、notify、notifyAll() 方法，否则编译期可以，运行时将抛出 IllegalMonitorStateException 异常。
- 调用 sleep() 和 yeild() 的时候，不会释放资源锁。
  [实例代码](./src/com/yhj/chapter21/concurrency/YieldLock.java)<br>

### notify() 和 notifyAll()

- notifyAll() 因某个特定的锁被调用时，只有等待这个锁的任务才会被唤醒。[实例代码](./src/com/yhj/chapter21/concurrency/NotifyVsNotifyAll.java)

### 生产者和消费者

Restaurant 是 WaitPerson 和 Chef 的焦点，它们都必须知道在为哪一个 Restaurant 工作。[实例代码](./src/com/yhj/chapter21/concurrency/Restaurant.java)

#### 显示的 Lock 和 Condition 对象

可以使用 Java SE5 的 Condition 来互斥挂起基本类。

- await() 来挂起一个类。
- singalAll()、singal() 来唤醒在这个 Condition 上挂起的任务。

```java
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();  //获得对象

    condition.await();// 挂起

    condition.singal();// 唤醒

```

对于每一个 lock() 的调用都必须紧跟一个 try-finally 子句。
