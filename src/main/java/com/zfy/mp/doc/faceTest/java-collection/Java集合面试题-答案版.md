# Java集合面试题 - 答案版

> 来源：JavaGuide (javaguide.cn)
> 整理：Claude

---

## 集合概述

### 1. Java 集合框架主要包括哪些接口？List、Set、Queue、Map 四者的区别？

Java 集合主要由两大接口派生：
- **`Collection` 接口**：存放单一元素
  - `List`：存储有序、可重复的元素
  - `Set`：存储不可重复的元素
  - `Queue`：按特定排队规则确定先后顺序，存储有序、可重复的元素
- **`Map` 接口**：存放键值对（key-value）

**四者的区别**：
- `List`：对付顺序的好帮手，存储的元素有序、可重复
- `Set`：注重独一无二的性质，存储的元素不可重复
- `Queue`：实现排队功能的叫号机，按特定排队规则确定先后顺序
- `Map`：用 key 来搜索的专家，key 无序、不可重复，value 无序、可重复

---

### 2. 集合框架底层数据结构总结？

**Collection 接口下的集合**：

| 类型 | 实现类 | 底层数据结构 |
|------|--------|-------------|
| List | ArrayList | Object[] 数组 |
| List | Vector | Object[] 数组 |
| List | LinkedList | 双向链表 |
| Set | HashSet | 基于 HashMap 实现 |
| Set | LinkedHashSet | 基于 LinkedHashMap 实现 |
| Set | TreeSet | 红黑树 |
| Queue | PriorityQueue | Object[] 数组（小顶堆） |
| Queue | ArrayDeque | 可扩容动态双向数组 |

**Map 接口下的集合**：

| 实现类 | 底层数据结构 |
|--------|-------------|
| HashMap | JDK1.8 前：数组+链表；JDK1.8 后：数组+链表/红黑树 |
| LinkedHashMap | 数组+链表/红黑树+双向链表 |
| Hashtable | 数组+链表 |
| TreeMap | 红黑树 |

---

### 3. 如何选用集合？

- 需要根据键值获取元素值时：选用 `Map` 接口下的集合
  - 需要排序：选择 `TreeMap`
  - 不需要排序：选择 `HashMap`
  - 需要线程安全：选择 `ConcurrentHashMap`
- 只需要存放元素值时：选择 `Collection` 接口下的集合
  - 需要保证元素唯一：选择实现 `Set` 接口的集合（`TreeSet` 或 `HashSet`）
  - 不需要保证唯一：选择实现 `List` 接口的集合（`ArrayList` 或 `LinkedList`）

---

### 4. 为什么要使用集合？数组和集合有什么区别？

**数组的局限**：
- 长度固定，无法适应数据量动态变化
- 存储类型单一

**集合的优势**：
- 大小可变，灵活适应数据量变化
- 支持泛型，类型安全
- 提供丰富的 API 操作方法
- 内建算法支持

**数组和集合的区别**：
| 区别 | 数组 | 集合 |
|------|------|------|
| 长度 | 固定 | 可变 |
| 泛型支持 | 不支持 | 支持 |
| 基本类型存储 | 直接存储 | 需要包装类 |
| 操作方法 | 有限 | 丰富 |

---

## List

### 5. ArrayList 和 Array（数组）的区别？

| 区别 | ArrayList | Array |
|------|-----------|-------|
| 长度 | 动态扩容/缩容 | 固定长度 |
| 泛型 | 支持 | 不支持 |
| 基本类型 | 需要包装类 | 可直接存储 |
| 操作方法 | 丰富（add、remove等） | 有限（下标访问） |
| 创建时是否需要指定大小 | 不需要 | 需要 |

---

### 6. ArrayList 和 Vector 的区别？

- `ArrayList` 是 `List` 的主要实现类，底层使用 `Object[]` 存储，线程不安全
- `Vector` 是 `List` 的古老实现类，底层使用 `Object[]` 存储，线程安全（使用 `synchronized` 修饰）

---

### 7. Vector 和 Stack 的区别？

- `Vector` 和 `Stack` 都是线程安全的，都使用 `synchronized` 关键字同步
- `Stack` 继承自 `Vector`，是后进先出（LIFO）的栈，`Vector` 是列表

> 注意：`Vector` 和 `Stack` 已被淘汰，推荐使用并发集合类（如 `ConcurrentHashMap`、`CopyOnWriteArrayList` 等）

---

### 8. ArrayList 可以添加 null 值吗？

可以。不过不建议添加 `null` 值，因为 `null` 无意义，会让代码难以维护，容易导致空指针异常。

---

### 9. ArrayList 插入和删除元素的时间复杂度？

**插入**：
- 头部插入：O(n)，需要将所有元素向后移动
- 尾部插入：O(1)，容量未满时直接添加；扩容时需要 O(n) 复制
- 指定位置插入：O(n)，需要移动平均 n/2 个元素

**删除**：
- 头部删除：O(n)，需要将所有元素向前移动
- 尾部删除：O(1)
- 指定位置删除：O(n)，需要移动平均 n/2 个元素

---

### 10. LinkedList 插入和删除元素的时间复杂度？

- 头部插入/删除：O(1)，只需修改头结点指针
- 尾部插入/删除：O(1)，只需修改尾结点指针
- 指定位置插入/删除：O(n)，需要先移动到指定位置，再修改指针

---

### 11. LinkedList 为什么不能实现 RandomAccess 接口？

`RandomAccess` 是标记接口，表示支持随机访问。`LinkedList` 底层是双向链表，内存地址不连续，只能通过指针遍历，不支持随机快速访问，所以不能实现 `RandomAccess` 接口。

`ArrayList` 实现了 `RandomAccess` 接口，因为底层是数组，支持 O(1) 的随机访问。

---

### 12. ArrayList 与 LinkedList 区别？

| 区别 | ArrayList | LinkedList |
|------|-----------|------------|
| 线程安全 | 否 | 否 |
| 底层数据结构 | Object[] 数组 | 双向链表 |
| 插入/删除影响 | 受位置影响（指定位置 O(n)） | 头尾 O(1)，指定位置 O(n) |
| 随机访问 | 支持 O(1) | 不支持 |
| 内存占用 | 结尾预留容量空间 | 每个元素消耗更多空间（前后驱指针） |

> 注意：实际开发中，`ArrayList` 几乎可以替代所有需要用 `LinkedList` 的场景，且性能更好。就连 `LinkedList` 的作者都说从来不会使用 `LinkedList`。

---

### 13. 说一说 ArrayList 的扩容机制？

ArrayList 底层使用 `Object[]` 数组存储，当容量不足时会自动扩容：

1. 当元素添加导致 `size + 1 > elementData.length` 时触发扩容
2. 扩容增量：` Arrays.copyOf()` 扩容为原容量的 **1.5 倍**
3. 扩容操作：`ElementData` 数组复制到新数组
4. 新数组容量：`oldCapacity + (oldCapacity >> 1)`

```java
int newCapacity = oldCapacity + (oldCapacity >> 1);  // 1.5倍
```

如果 1.5 倍仍不够（如添加大量元素），则使用所需最小容量作为新容量。

---

### 14. 集合中的 fail-fast 和 fail-safe 是什么？

**fail-fast（快速失败）**：
- 机制：通过维护 `modCount` 修改计数器，在迭代时检查 `expectedModCount` 是否一致
- 触发：检测到并发修改时立即抛出 `ConcurrentModificationException`
- 代表：`ArrayList`、`HashMap` 等非线程安全集合

**fail-safe（安全失败）**：
- 机制：复制原集合的快照，在快照上进行操作
- 特点：不对原集合产生影响，但可能无法读取实时数据
- 代表：`CopyOnWriteArrayList`、`ConcurrentHashMap` 等并发集合

**示例**：
```java
// fail-fast：并发修改会抛异常
List<Integer> list = new ArrayList<>();
// 迭代时其他线程修改，抛出 ConcurrentModificationException

// fail-safe：基于快照操作
List<Integer> safeList = new CopyOnWriteArrayList<>();
// 迭代时修改不影响遍历
```

---

## Set

### 15. Comparable 和 Comparator 的区别？

| 区别 | Comparable | Comparator |
|------|------------|------------|
| 包 | java.lang | java.util |
| 方法 | compareTo(Object obj) | compare(Object obj1, Object obj2) |
| 位置 | 在要比较的类内部实现 | 单独实现或匿名内部类 |
| 调用 | Collections.sort(list) | Collections.sort(list, comparator) |

- `Comparable`：自然排序，类实现后所有对象都按此排序
- `Comparator`：定制排序，可以为同一个类创建多种排序规则

---

### 16. 无序性和不可重复性的含义是什么？

- **无序性**：不等于随机性，是指存储在底层数组中并非按索引顺序添加，而是根据哈希值决定
- **不可重复性**：添加的元素按 `equals()` 判断返回 false，需要同时重写 `equals()` 和 `hashCode()` 方法

---

### 17. 比较 HashSet、LinkedHashSet 和 TreeSet 三者的异同？

| 区别 | HashSet | LinkedHashSet | TreeSet |
|------|---------|---------------|---------|
| 底层数据结构 | 哈希表 | 链表+哈希表 | 红黑树 |
| 元素顺序 | 无序 | 插入顺序 | 有序（自然/定制） |
| 线程安全 | 否 | 否 | 否 |
| 元素唯一 | 是 | 是 | 是 |
| 时间复杂度 | O(1) | O(1) | O(log n) |
| 适用场景 | 最快查找，不关注顺序 | 需保持插入顺序 | 需要排序 |

---

## Queue

### 18. Queue 与 Deque 的区别？

**Queue（单端队列）**：先进先出（FIFO），只能从一端插入，另一端删除

| 操作 | 抛出异常 | 返回特殊值 |
|------|----------|------------|
| 插入队尾 | add() | offer() |
| 删除队首 | remove() | poll() |
| 查询队首 | element() | peek() |

**Deque（双端队列）**：两端都可以插入和删除

| 操作 | 抛出异常 | 返回特殊值 |
|------|----------|------------|
| 插入队首 | addFirst() | offerFirst() |
| 插入队尾 | addLast() | offerLast() |
| 删除队首 | removeFirst() | pollFirst() |
| 删除队尾 | removeLast() | pollLast() |
| 查询队首 | getFirst() | peekFirst() |
| 查询队尾 | getLast() | peekLast() |

`Deque` 还可以通过 `push()`/`pop()` 模拟栈。

---

### 19. ArrayDeque 与 LinkedList 的区别？

| 区别 | ArrayDeque | LinkedList |
|------|------------|------------|
| 底层实现 | 可变长数组+双指针 | 双向链表 |
| NULL 支持 | 不支持 | 支持 |
| 内存占用 | 预分配，固定 | 动态分配 |
| 插入效率 | 扩容时 O(n)，均摊 O(1) | O(1) |
| 性能 | 更高（数组访问局部性） | 需频繁申请堆空间 |

**推荐**：实现队列使用 `ArrayDeque` 性能更好。`LinkedList` 可用于需要频繁在头尾操作的场景。

---

### 20. 说一说 PriorityQueue？

`PriorityQueue`（优先级队列）是 JDK1.5 引入的，与普通队列的区别在于元素出队顺序与优先级相关，总是优先级最高的元素先出队。

**特点**：
- 底层使用二叉堆（小顶堆）实现
- 使用可变长数组存储
- 插入/删除操作时间复杂度 O(log n)
- 非线程安全
- 不支持存储 NULL 和 non-comparable 对象
- 默认是小顶堆，可通过 `Comparator` 自定义优先级

---

### 21. 什么是 BlockingQueue？

`BlockingQueue`（阻塞队列）是一个接口，继承自 `Queue`。它支持阻塞机制：
- 当队列没有元素时，阻塞等待直到有元素
- 当队列已满时，阻塞等待直到有空间

常用于生产者-消费者模型。

---

### 22. BlockingQueue 的实现类有哪些？

| 实现类 | 说明 |
|--------|------|
| ArrayBlockingQueue | 有界队列，基于数组，需指定容量，公平/非公平锁 |
| LinkedBlockingQueue | 可选有界/无界（默认 Integer.MAX_VALUE），基于链表，仅非公平锁 |
| PriorityBlockingQueue | 无界，支持优先级排序（需实现 Comparable 或传入 Comparator） |
| SynchronousQueue | 同步队列，不存储元素，插入必须等待删除 |
| DelayQueue | 延迟队列，元素需到延迟时间才能出队 |

---

### 23. ArrayBlockingQueue 和 LinkedBlockingQueue 有什么区别？

| 区别 | ArrayBlockingQueue | LinkedBlockingQueue |
|------|-------------------|---------------------|
| 底层实现 | 数组 | 单向链表 |
| 是否有界 | 有界（必须指定容量） | 可选有界/无界（默认 Integer.MAX_VALUE） |
| 锁分离 | 否（同一把锁） | 是（生产用 putLock，消费用 takeLock） |
| 内存占用 | 预分配 | 动态分配 |
| 锁公平性 | 支持公平/非公平 | 仅非公平 |

---

## Map

### 24. HashMap 和 Hashtable 的区别？

| 区别 | HashMap | Hashtable |
|------|---------|----------|
| 线程安全 | 否 | 是（synchronized 修饰） |
| 效率 | 高 | 低（锁竞争） |
| Null key/value | 支持（null 键只能有一个） | 不支持（抛 NullPointerException） |
| 初始容量 | 16 | 11 |
| 扩容方式 | 2 倍扩容 | 2n+1 扩容 |
| 哈希扰动 | 有（JDK1.8） | 无 |
| 链表转红黑树 | 支持（长度>8且数组>=64） | 不支持 |

> 注意：`Hashtable` 基本被淘汰，不推荐使用。

---

### 25. HashMap 和 HashSet 区别？

| 区别 | HashMap | HashSet |
|------|---------|---------|
| 接口 | 实现 Map 接口 | 实现 Set 接口 |
| 存储内容 | 键值对 | 仅对象 |
| 添加方式 | put(key, value) | add(element) |
| 哈希计算 | 使用 key 的 hashCode() | 使用对象的 hashCode() |
| 底层实现 | HashSet 底层就是 HashMap | - |

`HashSet` 的 `add()` 方法实际调用了 `HashMap` 的 `put()` 方法，value 使用固定对象 `PRESENT`。

---

### 26. HashMap 和 TreeMap 区别？

| 区别 | HashMap | TreeMap |
|------|---------|---------|
| 底层数据结构 | 哈希表（数组+链表/红黑树） | 红黑树 |
| 是否排序 | 否 | 是（按键排序） |
| 时间复杂度 | O(1)~O(log n) | O(log n) |
| 继承接口 | AbstractMap | AbstractMap + NavigableMap + SortedMap |
| 功能 | 快速查找 | 查找 + 范围搜索 + 排序 |

`TreeMap` 实现了 `NavigableMap` 接口，支持：
- 定向搜索：`ceilingEntry()`、`floorEntry()` 等
- 子集操作：`subMap()`、`headMap()`、`tailMap()`
- 逆序视图：`descendingMap()`
- 边界操作：`firstEntry()`、`lastEntry()`

---

### 27. HashSet 如何检查重复？

`HashSet` 检查重复的流程：
1. 先计算对象的 `hashCode()` 值，确定存放位置
2. 若该位置已有元素，比较 `hashCode()` 值
3. 若 `hashCode()` 相同，调用 `equals()` 方法比较
4. 若 `equals()` 返回 true，则拒绝添加

JDK1.8 中 `add()` 方法实际调用 `HashMap` 的 `put()` 方法，通过返回值判断是否重复。

---

### 28. HashMap 的底层实现（JDK1.8 之前和之后有什么区别）？

**JDK1.8 之前**：
- 底层：数组 + 链表（链表散列）
- 解决哈希冲突：拉链法（链地址法）
- 扰动函数：`hash()` 方法进行 4 次扰动

**JDK1.8 之后**：
- 底层：数组 + 链表/红黑树
- 当链表长度 > 8 且数组长度 >= 64 时，链表转为红黑树
- 若数组长度 < 64，优先扩容而非转红黑树
- 扰动函数简化为 1 次扰动

**为什么链表转红黑树**：
- 链表查询 O(n)，红黑树查询 O(log n)
- 长度短时差异不明显，长度长时性能差异显著

**为什么阈值是 8**：
- 泊松分布：链表长度达到 8 的概率极低（<千万分之一）
- 平衡性能和空间

---

### 29. HashMap 的长度为什么是 2 的幂次方？

1. **位运算效率高**：`hash % length == hash & (length - 1)`（当 length 是 2 的幂次时）
2. **哈希分布均匀**：扩容时元素均匀分布，最多移动到原位置或原位置+原容量
3. **扩容高效**：只需检查高位是 0 还是 1，决定位置不变还是移动到新位置

**公式**：`index = (n - 1) & hash`

当 `n = 16` 时，`n-1 = 15` (二进制 1111)，可以用 & 运算代替 % 取模，效率更高。

---

### 30. HashMap 多线程操作导致死循环问题？

**问题原因（JDK1.7 及之前）**：
- 多线程扩容时使用**头插法**，链表可能倒置形成环形
- 查询时陷入死循环，CPU 100%

**JDK1.8 解决方案**：
- 改用**尾插法**，避免链表倒置
- 但仍不推荐多线程使用，存在数据覆盖问题

**结论**：并发环境推荐使用 `ConcurrentHashMap`。

---

### 31. HashMap 为什么线程不安全？

多线程环境下 `HashMap` 并发写操作会导致：
1. **数据覆盖**：两个线程同时 put 且哈希冲突，后执行的线程可能覆盖前一个的数据
2. **容量统计错误**：多线程同时 `++size`，导致 size 值不正确
3. **JDK1.7 死循环**：头插法扩容导致环形链表

---

### 32. HashMap 常见的遍历方式？

HashMap 有 7 种遍历方式：
1. Iterator + entrySet
2. Iterator + keySet
3. forEach + entrySet
4. forEach + keySet
5. Lambda 表达式
6. Streams API
7. parallelStreams API

**性能建议**：
- 非阻塞场景：推荐 `entrySet` 或 `keySet`
- 阻塞场景：推荐 `parallelStream`（性能最高）

---

### 33. ConcurrentHashMap 和 Hashtable 的区别？

| 区别 | ConcurrentHashMap | Hashtable |
|------|-------------------|-----------|
| 线程安全实现 | JDK1.7 分段锁，JDK1.8 CAS+synchronized | synchronized |
| 锁粒度 | 锁桶（JDK1.7）或锁节点（JDK1.8） | 锁整个表 |
| 并发度 | 高（多个Segment/桶） | 低（全局锁） |
| Null key/value | JDK1.8 不支持 | 不支持 |
| 迭代一致性 | 弱一致性 | 同步迭代 |
| 性能 | 高 | 低 |

---

### 34. ConcurrentHashMap 线程安全的具体实现方式/底层具体实现？

**JDK1.7**：
- 数据结构：Segment 数组 + HashEntry 数组 + 链表
- Segment 继承 `ReentrantLock`，扮演锁角色
- 默认 16 个 Segment，支持 16 并发写
- 每个 Segment 守护一个 HashEntry 数组

**JDK1.8**：
- 数据结构：Node 数组 + 链表/红黑树（与 HashMap 相同）
- 并发控制：CAS + synchronized
- 锁粒度：只锁定当前链表或红黑树的首节点
- 当链表长度 > 8 时转为红黑树

---

### 35. JDK 1.7 和 JDK 1.8 的 ConcurrentHashMap 实现有什么不同？

| 区别 | JDK1.7 | JDK1.8 |
|------|--------|--------|
| 线程安全方式 | Segment 分段锁（继承 ReentrantLock） | CAS + synchronized |
| 锁粒度 | 锁 Segment | 锁链表/红黑树首节点 |
| 数据结构 | Segment + HashEntry + 链表 | Node + 链表/红黑树 |
| Hash 冲突解决 | 拉链法 | 拉链法 + 红黑树 |
| 最大并发度 | Segment 数（默认 16） | Node 数组大小 |
| 代码量 | ~1000 行 | ~6000 行 |

---

### 36. ConcurrentHashMap 为什么 key 和 value 不能为 null？

**原因**：避免二义性

`ConcurrentHashMap` 不允许 null 值，因为：
- `get(key)` 返回 null 可能表示"键不存在"或"值就是 null"
- 多线程环境下，无法用 `containsKey()` 判断键是否存在（其他线程可能同时修改）

**对比 HashMap**：
- 单线程的 HashMap 可以通过 `containsKey()` 解决二义性
- 多线程的 `ConcurrentHashMap` 无法保证判断和读取的原子性

**Doug Lea（ConcurrentHashMap 作者）的解释**：单线程可以容忍歧义，多线程无法容忍。

---

### 37. ConcurrentHashMap 能保证复合操作的原子性吗？

**不能保证复合操作的原子性**！

`ConcurrentHashMap` 只保证单步操作（`put`、`get`、`remove` 等）的线程安全。复合操作如：
```java
if (!map.containsKey(key)) {
    map.put(key, value);
}
```
可能被其他线程打断，导致非预期结果。

**解决方案**：使用原子性复合操作方法：
- `putIfAbsent(key, value)`
- `compute(key, remappingFunction)`
- `computeIfAbsent(key, mappingFunction)`
- `computeIfPresent(key, remappingFunction)`
- `merge(key, value, remappingFunction)`

---

> 以上内容整理自 JavaGuide (javaguide.cn)
> 共 **37 道面试题**
