### Java集合框架 (Java Collections Framework)

#### 一、体系结构图

```
                    Collection (接口)
                           │
           ┌───────────────┼───────────────┐
           ▼               ▼               ▼
        List            Set            Queue
           │               │               │
           │               │               │
    ┌──────┴──────┐   ┌───┴────┐    ┌─────┴─────┐
    ▼             ▼   ▼        ▼    ▼           ▼
ArrayList   LinkedList HashSet TreeSet PriorityQueue
Vector      └───┴───┴───────┘
                 │
            (SortedSet)
```

```
                    Map (接口)
                        │
            ┌───────────┼───────────┐
            ▼           ▼           ▼
        HashMap   TreeMap   LinkedHashMap
        (O(1))   (O(log n))  (保持插入顺序)
            │           │
        (SortedMap)
```

#### 二、List 接口（有序、可重复）

| 实现类 | 特点 | 时间复杂度 | 适用场景 |
|--------|------|-----------|---------|
| `ArrayList` | 动态数组，查询快，增删慢 | get/set: O(1)<br>add/remove: O(n) | 频繁查询，少量增删 |
| `LinkedList` | 双向链表，增删快，查询慢 | get/set: O(n)<br>add/remove: O(1) | 频繁增删，头部/尾部操作 |
| `Vector` | 同步的ArrayList（已过时） | - | 需要线程安全时用`Collections.synchronizedList()` |

#### 三、Set 接口（无序、不可重复）

| 实现类 | 特点 | 时间复杂度 | 适用场景 |
|--------|------|-----------|---------|
| `HashSet` | 哈希表，无序 | add/contains: O(1) | 快速查找，不需要排序 |
| `TreeSet` | 红黑树，有序 | add/contains: O(log n) | 需要排序的集合 |
| `LinkedHashSet` | 哈希表+链表，插入顺序 | add/contains: O(1) | 需要保持插入顺序 |

#### 四、Map 接口（键值对）

| 实现类 | 特点 | 时间复杂度 | 适用场景 |
|--------|------|-----------|---------|
| `HashMap` | 哈希表，无序 | get/put: O(1) | 最常用，快速查找 |
| `TreeMap` | 红黑树，按Key排序 | get/put: O(log n) | 需要按Key排序 |
| `LinkedHashMap` | 哈希表+链表，插入顺序 | get/put: O(1) | 需要保持插入/访问顺序 |
| `WeakHashMap` | 弱引用，GC自动清理 | - | 缓存场景 |

#### 五、Queue 接口（队列）

| 实现类 | 特点 | 适用场景 |
|--------|------|---------|
| `LinkedList` | 双端队列 | 基础队列操作 |
| `ArrayDeque` | 数组双端队列 | 高性能栈/队列 |
| `PriorityQueue` | 优先级堆 | 任务调度、优先队列 |
| `ConcurrentLinkedQueue` | 无锁并发队列 | 高并发场景 |

#### 六、工具类 Collections

| 方法 | 说明 |
|------|------|
| `sort(List)` | 排序 |
| `reverse(List)` | 反转 |
| `shuffle(List)` | 随机打乱 |
| `binarySearch(List, key)` | 二分查找 |
| `synchronizedList/Set/Map()` | 返回线程安全视图 |
| `unmodifiableList/Set/Map()` | 返回不可修改视图 |

#### 七、JUC 并发集合（重点）

| 类 | 特点 | 适用场景 |
|----|------|---------|
| `ConcurrentHashMap` | 分段锁/无锁，高性能 | 高并发Map |
| `CopyOnWriteArrayList` | 写时复制，读多写少 | 读多写少场景 |
| `CopyOnWriteArraySet` | 写时复制 | 读多写少Set |
| `ConcurrentLinkedQueue` | 无锁并发队列 | 高并发队列 |
| `ArrayBlockingQueue` | 有界阻塞队列 | 生产者-消费者 |
| `LinkedBlockingQueue` | 可选边界阻塞队列 | 任务队列 |

#### 八、选择建议

```
需要频繁查询? ──Yes──▶ ArrayList
       │
       No
       │
需要频繁增删? ──Yes──▶ LinkedList
       │
       No
       │
需要排序? ──Yes──▶ TreeSet / TreeMap
       │
       No
       │
需要去重? ──Yes──▶ HashSet
       │
       No
       │
需要按顺序访问? ──Yes──▶ LinkedHashSet / LinkedHashMap
       │
       No
       │
─────▶ HashMap / ArrayList (默认选择)
```

#### 九、性能对比

| 操作 | ArrayList | LinkedList | HashMap | TreeMap |
|------|-----------|------------|---------|---------|
| get(index) | O(1) | O(n) | - | - |
| add(index) | O(n) | O(1) | - | - |
| add(tail) | O(1)* | O(1) | - | - |
| contains | O(n) | O(n) | O(1) | O(log n) |
| get(key) | - | - | O(1) | O(log n) |

*ArrayList在容量足够时为O(1)

#### 十、线程安全性

| 集合 | 线程安全 |
|------|---------|
| ArrayList | No |
| LinkedList | No |
| HashSet | No |
| HashMap | No |
| TreeMap | No |
| Vector | Yes (已过时) |
| Hashtable | Yes (已过时) |
| ConcurrentHashMap | Yes |
| CopyOnWriteArrayList | Yes |

**推荐**：使用 `ConcurrentHashMap` 或 `Collections.synchronizedXxx()` 包裹
