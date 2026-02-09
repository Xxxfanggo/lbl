### Java集合面试题

---

## 一、基础题

### 1. Java集合框架有哪些核心接口？
```
Collection (接口)
    ├── List (有序、可重复)
    │   ├── ArrayList
    │   ├── LinkedList
    │   └── Vector
    ├── Set (无序、不可重复)
    │   ├── HashSet
    │   ├── LinkedHashSet
    │   └── TreeSet
    └── Queue (队列)
        ├── PriorityQueue
        ├── ArrayDeque
        └── BlockingQueue

Map (键值对)
    ├── HashMap
    ├── TreeMap
    ├── LinkedHashMap
    └── ConcurrentHashMap
```

### 2. ArrayList 和 LinkedList 的区别？

| 特性 | ArrayList | LinkedList |
|------|-----------|------------|
| 底层结构 | 动态数组 | 双向链表 |
| 随机访问 | O(1) | O(n) |
| 插入删除 | O(n) | O(1) (已知位置) |
| 内存开销 | 较小 | 较大（每个节点存前后引用） |
| 适用场景 | 频繁查询 | 频增删、头尾操作 |

**结论**：大多数情况使用 `ArrayList`，只有在频繁的头尾插入删除时才用 `LinkedList`。

### 3. HashMap 和 Hashtable 的区别？

| 特性 | HashMap | Hashtable |
|------|---------|-----------|
| 线程安全 | 不安全 | 安全（同步） |
| 性能 | 高 | 低 |
| null键值 | 允许 | 不允许 |
| 继承 | AbstractMap | Dictionary（已过时） |
| 迭代器 | Fail-Fast | Fail-Safe |

**结论**：Hashtable 已过时，需要线程安全时使用 `ConcurrentHashMap` 或 `Collections.synchronizedMap()`。

### 4. HashSet 如何保证元素不重复？

HashSet 底层使用 HashMap，存储元素时：
1. 先计算元素的 `hashCode()`
2. 如果哈希值不同，直接存储
3. 如果哈希值相同，调用 `equals()` 比较
4. `equals()` 返回 true，则认为是重复元素

**关键**：存储自定义对象必须重写 `hashCode()` 和 `equals()` 方法。

---

## 二、深入题

### 5. HashMap 的底层原理（重点）

**Java 7**：
- 数组 + 链表
- 扩容条件：`size > capacity * loadFactor`
- 链表过长影响查询效率

**Java 8**：
- 数组 + 链表 + 红黑树
- 链表长度 >= 8 转为红黑树
- 红黑树节点 <= 6 退化回链表

**Put 流程**：
```
1. 计算key的hash值，定位到数组索引
2. 索引位置为空 → 直接插入
3. 索引位置有数据（Hash碰撞）
   - 遍历链表/红黑树
   - key相同 → 覆盖value
   - key不同 → 插入到链表尾部（尾插法）
4. 插入后检查是否需要扩容
```

**扩容机制**：
- 默认初始容量：16
- 默认负载因子：0.75
- 扩容时机：元素数量 > 16 * 0.75 = 12
- 扩容后容量：原容量 * 2

### 6. 为什么 HashMap 线程不安全？如何解决？

**不安全原因**：
- 并发 put 可能导致数据丢失
- 扩容时可能形成死循环（Java 7）
- size 计数不准确

**解决方案**：
| 方案 | 适用场景 |
|------|---------|
| `ConcurrentHashMap` | **推荐**，高并发场景 |
| `Collections.synchronizedMap()` | 低并发，简单场景 |
| `Hashtable` | 不推荐（已过时） |

### 7. ConcurrentHashMap 的实现原理（高频题）

**Java 7**：分段锁（Segment）
- 默认 16 个 Segment
- 每个 Segment 相当于一个小 HashMap
- 多个线程可同时操作不同 Segment

**Java 8**：CAS + synchronized
- 取消 Segment，直接锁数组节点
- 查询操作无锁
- 首节点用 synchronized，其他节点用 CAS

**核心优化**：
- `get()` 无锁，使用 `volatile` 保证可见性
- `put()` 只锁当前节点，支持高并发

### 8. CopyOnWriteArrayList 的原理和适用场景？

**原理**：写时复制（Copy-on-Write）
- 读操作无锁，直接读
- 写操作复制新数组，修改后替换引用

**适用场景**：读多写少
```
优点：
- 读操作性能高，无锁
- 遍历时可以安全修改

缺点：
- 写操作需要复制整个数组，成本高
- 内存占用大
- 数据一致性弱（读到的可能是旧数据）
```

### 9. TreeMap 和 HashMap 的区别？

| 特性 | HashMap | TreeMap |
|------|---------|---------|
| 排序 | 无序 | 按 Key 排序（自然/自定义） |
| 底层 | 哈希表 | 红黑树 |
| 查询 | O(1) | O(log n) |
| 插入 | O(1) | O(log n) |
| null键 | 允许 | 不允许 |

**结论**：需要排序用 `TreeMap`，否则用 `HashMap`。

---

## 三、进阶题

### 10. 为什么建议使用 `Arrays.asList()` 时要注意？

```java
List<String> list = Arrays.asList("A", "B", "C");

// 问题1：不能添加元素
list.add("D");  // 抛出 UnsupportedOperationException

// 问题2：是内部类 ArrayList，不是 java.util.ArrayList
System.out.println(list.getClass().getName());  // java.util.Arrays$ArrayList

// 解决方案
List<String> modifiableList = new ArrayList<>(Arrays.asList("A", "B", "C"));
```

### 11. 如何正确遍历集合并删除元素？

```java
// ❌ 错误方式：ConcurrentModificationException
for (String s : list) {
    if (s.equals("B")) {
        list.remove(s);  // 报错
    }
}

// ✅ 正确方式1：使用 Iterator
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    if (s.equals("B")) {
        it.remove();
    }
}

// ✅ 正确方式2：Java 8+ removeIf
list.removeIf(s -> s.equals("B"));

// ✅ 正确方式3：倒序遍历（仅限 List）
for (int i = list.size() - 1; i >= 0; i--) {
    if (list.get(i).equals("B")) {
        list.remove(i);
    }
}
```

### 12. ArrayList 扩容机制？

```java
// 默认构造函数
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;  // 空数组
}

// 第一次添加时扩容到 10
if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
    minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);  // 10
}

// 后续扩容：newCapacity = oldCapacity + (oldCapacity >> 1) = 1.5倍
int newCapacity = oldCapacity + (oldCapacity >> 1);
```

### 13. HashSet 如何去重？底层原理？

```java
// HashSet.add() 内部调用 HashMap.put()
public boolean add(E e) {
    return map.put(e, PRESENT) == null;  // PRESENT 是一个固定对象
}

// HashMap.put() 逻辑
if (e.hash == p.hash && ((k = p.key) == key || (key != null && key.equals(k))))
    return e.value;  // key相同，覆盖
```

### 14. 如何让 List 按自定义规则排序？

```java
// 方式1：Comparable（自然排序）
class Person implements Comparable<Person> {
    private int age;
    @Override
    public int compareTo(Person other) {
        return this.age - other.age;  // 升序
    }
}
Collections.sort(list);

// 方式2：Comparator（自定义排序）
Collections.sort(list, (p1, p2) -> p2.getAge() - p1.getAge());  // 降序
list.sort(Comparator.comparing(Person::getAge).reversed());
```

### 15. LinkedHashMap 如何实现 LRU 缓存？

```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(16, 0.75f, true);  // accessOrder = true
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;  // 超过容量删除最老的
    }
}
```

### 16. PriorityQueue 是如何实现的？

```java
// 底层使用最小堆（数组实现）
// 父节点索引：(i - 1) / 2
// 左子节点索引：2 * i + 1
// 右子节点索引：2 * i + 2

PriorityQueue<Integer> minHeap = new PriorityQueue<>();
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

---

## 四、JUC 专题（企业级）

### 17. ConcurrentHashMap 和 Hashtable 的区别？

| 特性 | Hashtable | ConcurrentHashMap |
|------|-----------|-------------------|
| 锁粒度 | 全表锁 | 分段锁/节点锁 |
| 并发度 | 低 | 高 |
| 迭代 | 不支持并发修改 | 支持 |
| null | 不允许 | 不允许 |

### 18. 如何选择合适的集合？

```
需要排序?
├─ Yes → TreeSet / TreeMap
└─ No
   需要频繁查询?
   ├─ Yes → ArrayList / HashMap
   └─ No
      需要去重?
      ├─ Yes → HashSet
      └─ No → ArrayList
```

### 19. 集合的 Fail-Fast 机制是什么？

```java
// 迭代器遍历时，集合被修改会抛出 ConcurrentModificationException
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
Iterator<String> it = list.iterator();
list.add("D");  // 修改集合
it.next();      // ConcurrentModificationException
```

**原理**：迭代器维护一个 `expectedModCount`，每次操作前检查是否与集合的 `modCount` 一致。

**安全方案**：
- 使用 Iterator 的 `remove()`
- 使用 `CopyOnWriteArrayList`
- 使用并发集合

### 20. HashMap 中 `key.hashCode()` 的计算方法？

```java
static final int hash(Object key) {
    int h;
    // 高16位异或低16位，减少哈希冲突
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

### 21. 为什么 HashMap 容量要是 2 的幂次方？

```java
// 2的幂次方可以用位运算代替取模，效率高
index = hash & (capacity - 1);  // 等价于 hash % capacity

// 例如：容量=16，n-1=15 (二进制 1111)
hash & 1111  → 取hash的低4位作为索引
```

---

## 五、代码题

### 22. 手写 LRU 缓存（LeetCode 146）

```java
class LRUCache extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCache(int capacity) {
        super(16, 0.75f, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
    }
}
```

### 23. 判断两个 List 是否相等（忽略顺序）

```java
// 方式1：排序后比较
boolean equals(List<String> l1, List<String> l2) {
    if (l1.size() != l2.size()) return false;
    List<String> c1 = new ArrayList<>(l1);
    List<String> c2 = new ArrayList<>(l2);
    Collections.sort(c1);
    Collections.sort(c2);
    return c1.equals(c2);
}

// 方式2：使用频率统计
boolean equals2(List<String> l1, List<String> l2) {
    Map<String, Long> m1 = l1.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    Map<String, Long> m2 = l2.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    return m1.equals(m2);
}
```

### 24. 实现 List 去重并保持顺序

```java
// 方式1：LinkedHashSet
List<Integer> unique = new ArrayList<>(new LinkedHashSet<>(list));

// 方式2：Stream distinct
List<Integer> unique = list.stream().distinct().collect(Collectors.toList());

// 方式3：手动遍历
List<Integer> unique = new ArrayList<>();
Set<Integer> seen = new HashSet<>();
for (Integer i : list) {
    if (seen.add(i)) {
        unique.add(i);
    }
}
```

---

## 六、高频面试题速记

1. **ArrayList vs LinkedList**：ArrayList 优先，LinkedList 仅用于频繁头尾操作
2. **HashMap 扩容**：2倍扩容，默认负载因子 0.75
3. **ConcurrentHashMap**：Java 7 分段锁，Java 8 CAS + synchronized
4. **HashSet 去重**：依赖 hashCode 和 equals
5. **Fail-Fast**：遍历时修改集合会报 ConcurrentModificationException
6. **CopyOnWriteArrayList**：读多写少场景
7. **TreeSet/TreeMap**：红黑树实现，需要实现 Comparable 或提供 Comparator
8. **LRU 缓存**：使用 LinkedHashMap (accessOrder=true)
9. **HashMap 线程不安全**：用 ConcurrentHashMap
10. **容量为 2 的幂次方**：位运算代替取模，效率高
