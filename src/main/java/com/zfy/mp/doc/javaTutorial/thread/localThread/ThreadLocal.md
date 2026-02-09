# ThreadLocal 讲解文档

## 1. 什么是 ThreadLocal？

ThreadLocal 是 Java 中一种特殊的变量存储机制，它允许每个线程拥有自己的变量副本，实现了变量的线程隔离。通过 ThreadLocal 存储的变量，只能被当前线程访问，其他线程无法获取或修改该变量。

## 2. ThreadLocal 的工作原理

### 2.1 核心概念

- **ThreadLocalMap**: 每个线程内部维护的 ThreadLocal 实例映射表
- **Entry**: 存储键值对，键是 ThreadLocal 实例，值是对应的线程局部变量
- **Hash 碰撞处理**: 使用开放地址法处理哈希冲突

### 2.2 工作流程

1. 当调用 `set(T value)` 方法时：
   - 获取当前线程
   - 获取该线程的 ThreadLocalMap
   - 将 ThreadLocal 实例作为 key，value 作为值存储在 ThreadLocalMap 中

2. 当调用 `get()` 方法时：
   - 获取当前线程
   - 获取该线程的 ThreadLocalMap
   - 以当前 ThreadLocal 实例为 key 查找对应的值

3. 当调用 `remove()` 方法时：
   - 获取当前线程
   - 获取该线程的 ThreadLocalMap
   - 移除当前 ThreadLocal 实例对应的值

## 3. ThreadLocal 的使用场景

### 3.1 适用场景

1. **数据库连接管理**: 为每个线程保存独立的数据库连接
2. **用户会话管理**: 在 Web 应用中保存用户会话信息
3. **事务管理**: 为每个线程保存独立的事务上下文
4. **线程安全的日期格式化**: 避免 SimpleDateFormat 的线程安全问题

### 3.2 不适用场景

1. **共享状态**: 需要多个线程共享的状态
2. **频繁变动的数据**: 数据量较大且频繁变化的数据
3. **大数据量存储**: 可能导致内存泄漏的场景

## 4. ThreadLocal 的注意事项

### 4.1 内存泄漏问题

ThreadLocal 可能导致内存泄漏，原因：
- ThreadLocalMap 的 key 是弱引用，value 是强引用
- 当 ThreadLocal 实例被回收，但线程仍然存在时，key 变为 null，value 无法被回收

### 4.2 解决方案

```java
// 使用后及时清理
try {
    // 业务逻辑
} finally {
    threadLocal.remove();
}
```

### 4.3 最佳实践

1. **尽量使用 static final 修饰**
2. **使用完后及时清理**
3. **避免存储大对象**
4. **注意线程池中的线程复用问题**

## 5. 代码示例

```java
public class ThreadLocalExample {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // 设置线程局部变量
        threadLocal.set("主线程数据");

        // 新建线程
        new Thread(() -> {
            threadLocal.set("线程1的数据");
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
        }, "Thread-1").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            threadLocal.set("线程2的数据");
        }, "Thread-2").start();

        System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
    }
}
```

## 6. 总结

ThreadLocal 是一个强大的工具，用于实现线程局部变量，但在使用时需要注意内存泄漏问题。合理使用 ThreadLocal 可以有效地解决线程安全问题，特别是在需要线程隔离的场景下。