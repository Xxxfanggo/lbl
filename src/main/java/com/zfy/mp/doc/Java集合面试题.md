# Java 集合面试题

## 高频题索引

以下题目建议优先背熟，面试考察频率更高：

- 【高频】3. List、Set、Map 有什么区别？
- 【高频】4. ArrayList 和 LinkedList 有什么区别？
- 【高频】6. ArrayList 默认容量是多少？
- 【高频】7. ArrayList 扩容机制是怎样的？
- 【高频】16. HashMap 的底层数据结构是什么？
- 【高频】17. HashMap 的 put 流程是怎样的？
- 【高频】19. HashMap 为什么容量是 2 的幂？
- 【高频】20. HashMap 的默认容量和负载因子是多少？
- 【高频】23. HashMap 扩容过程是怎样的？
- 【高频】25. HashMap 为什么 JDK 8 引入红黑树？
- 【高频】26. HashMap 什么时候链表转红黑树？
- 【高频】28. HashMap 是线程安全的吗？
- 【高频】31. HashMap 和 ConcurrentHashMap 有什么区别？
- 【高频】32. ConcurrentHashMap 为什么不允许 null？
- 【高频】38. fail-fast 是什么？
- 【高频】40. 如何安全地遍历时删除元素？
- 【高频】51. ArrayList 和数组有什么区别？
- 【高频】52. ArrayList 插入和删除元素的时间复杂度？
- 【高频】58. fail-fast 和 fail-safe 有什么区别？
- 【高频】66. ConcurrentHashMap 如何保证线程安全？
- 【高频】68. ConcurrentHashMap 能保证复合操作原子性吗？

## 1. Java 集合体系是怎样的？

Java 集合主要分为两大体系：

```text
Collection
├── List
│   ├── ArrayList
│   ├── LinkedList
│   └── Vector
└── Set
    ├── HashSet
    ├── LinkedHashSet
    └── TreeSet

Map
├── HashMap
├── LinkedHashMap
├── TreeMap
├── Hashtable
└── ConcurrentHashMap
```

面试回答：

Java 集合主要分为 Collection 和 Map 两大体系。Collection 表示单列集合，下面有 List 和 Set；List 允许元素重复并且有序，Set 不允许重复。Map 是双列集合，用来存储 key-value 键值对，常见实现有 HashMap、LinkedHashMap、TreeMap、ConcurrentHashMap 等。

## 2. Collection 和 Map 有什么区别？

区别：

- Collection 存储单个元素
- Map 存储 key-value 键值对
- Collection 下有 List、Set、Queue
- Map 下有 HashMap、TreeMap、ConcurrentHashMap 等

面试回答：

Collection 是单列集合，每个元素是一个独立对象；Map 是双列集合，每个元素由 key 和 value 组成。Collection 更适合保存一组对象，Map 更适合根据 key 快速查找 value。

## 3. List、Set、Map 有什么区别？

区别：

- List：有序、可重复
- Set：无序或按规则排序、不可重复
- Map：key-value 结构，key 不可重复，value 可以重复

面试回答：

List 适合保存有顺序且允许重复的数据，比如列表数据。Set 适合保存不允许重复的数据，比如去重场景。Map 适合保存键值对，比如根据用户 id 查询用户信息。

## 4. ArrayList 和 LinkedList 有什么区别？

ArrayList：

- 底层是数组
- 查询快
- 随机访问效率高
- 插入删除可能需要移动元素

LinkedList：

- 底层是双向链表
- 插入删除节点较方便
- 随机访问效率低

面试回答：

ArrayList 底层是动态数组，支持按下标快速访问，查询效率高，但中间插入和删除可能需要移动元素。LinkedList 底层是双向链表，插入删除节点本身比较方便，但按下标查找需要遍历链表，随机访问效率较低。实际开发中 ArrayList 使用更多。

## 5. ArrayList 的底层原理是什么？

核心：

- 底层是 Object 数组
- 默认无参构造时先是空数组
- 第一次添加元素时扩容到默认容量
- 容量不足时自动扩容

面试回答：

ArrayList 底层基于数组实现。添加元素时会先检查容量，如果容量不足会进行扩容，并把旧数组元素复制到新数组中。因为底层是数组，所以按下标查询很快，但中间插入或删除元素时可能需要移动后面的元素。

## 6. ArrayList 默认容量是多少？

重点：

- JDK 8 中无参构造创建的是空数组
- 第一次添加元素时扩容到 10
- 后续扩容大约为原来的 1.5 倍

面试回答：

ArrayList 使用无参构造时，刚创建出来内部是空数组，并不是马上分配 10 个容量。第一次添加元素时才会扩容到默认容量 10。之后如果容量不够，会按大约 1.5 倍进行扩容。

## 7. ArrayList 扩容机制是怎样的？

流程：

```text
添加元素
  -> 判断容量是否足够
  -> 不够则扩容
  -> 新容量约为旧容量的 1.5 倍
  -> 复制旧数组到新数组
```

面试回答：

ArrayList 添加元素时会检查内部数组容量，如果容量不够，会创建一个更大的新数组，新容量通常是旧容量的 1.5 倍，然后把旧数组中的元素复制过去。因为扩容涉及数组复制，所以如果能预估数据量，建议在创建时指定初始容量。

## 8. 为什么 ArrayList 查询快、插入删除慢？

原因：

- 查询快：数组支持下标随机访问
- 插入删除慢：中间位置操作需要移动元素

面试回答：

ArrayList 底层是数组，数组可以通过下标直接计算元素位置，所以随机查询效率高。但如果在中间插入或删除元素，后面的元素需要整体移动，所以插入删除相对慢。

## 9. LinkedList 的底层原理是什么？

核心：

- 底层是双向链表
- 每个节点保存前驱、后继和当前元素
- 不支持高效随机访问

面试回答：

LinkedList 底层是双向链表，每个节点保存当前元素、前一个节点和后一个节点。它适合在已知节点位置的情况下进行插入和删除，但如果通过下标访问元素，需要从头或尾开始遍历，所以随机访问效率不如 ArrayList。

## 10. Vector 和 ArrayList 有什么区别？

区别：

- ArrayList 线程不安全
- Vector 方法加了 synchronized，线程安全
- Vector 扩容通常是 2 倍
- ArrayList 性能通常更好

面试回答：

Vector 和 ArrayList 底层都是数组。区别是 Vector 的很多方法使用 synchronized 修饰，线程安全但性能较低；ArrayList 线程不安全，但单线程场景性能更好。现在实际开发中很少使用 Vector。

## 11. ArrayList 是线程安全的吗？

结论：

ArrayList 不是线程安全的。

面试回答：

ArrayList 不是线程安全的。如果多个线程同时修改同一个 ArrayList，可能出现数据覆盖、数据丢失、数组越界等问题。多线程场景可以使用 Collections.synchronizedList、CopyOnWriteArrayList，或者在外部加锁，不过具体选择要看读写比例。

## 12. Set 如何保证元素不重复？

核心：

- HashSet 依赖 hashCode 和 equals
- TreeSet 依赖排序比较规则

面试回答：

Set 的不同实现去重方式不同。HashSet 底层基于 HashMap，主要通过元素的 hashCode 和 equals 判断是否重复。TreeSet 底层基于红黑树，依赖比较器或元素自身的比较规则判断是否重复。

## 13. HashSet 的底层原理是什么？

核心：

- HashSet 底层是 HashMap
- 元素作为 HashMap 的 key
- value 是一个固定的 Object 对象

面试回答：

HashSet 底层其实是通过 HashMap 实现的。添加到 HashSet 中的元素会作为 HashMap 的 key，value 使用一个固定的占位对象。因为 HashMap 的 key 不能重复，所以 HashSet 可以实现元素去重。

## 14. HashSet 和 TreeSet 有什么区别？

HashSet：

- 基于 HashMap
- 无序
- 查询效率通常较高
- 依赖 hashCode 和 equals

TreeSet：

- 基于 TreeMap
- 元素有序
- 依赖 Comparable 或 Comparator

面试回答：

HashSet 基于哈希表实现，元素无序，查找效率通常较高。TreeSet 基于红黑树实现，元素会按照自然顺序或自定义比较器排序。如果只需要去重，一般用 HashSet；如果还需要排序，可以用 TreeSet。

## 15. LinkedHashSet 有什么特点？

特点：

- 元素不重复
- 可以保持插入顺序
- 底层基于 LinkedHashMap

面试回答：

LinkedHashSet 是 HashSet 的有序版本，既能保证元素不重复，又能按照插入顺序遍历。它底层基于 LinkedHashMap，通过哈希表保证查找效率，通过双向链表维护顺序。

## 16. HashMap 的底层数据结构是什么？

JDK 8 中：

```text
数组 + 链表 + 红黑树
```

面试回答：

JDK 8 中 HashMap 底层结构是数组、链表和红黑树。数组用于定位桶位置，发生哈希冲突时，会在同一个桶中形成链表。当链表过长并且数组容量达到一定条件时，链表会转成红黑树，提高查询效率。

## 17. HashMap 的 put 流程是怎样的？

流程：

```text
计算 key 的 hash
根据 hash 定位数组下标
如果桶为空，直接插入
如果桶不为空，判断 key 是否相等
key 相等则覆盖 value
key 不相等则插入链表或红黑树
元素数量超过阈值则扩容
```

面试回答：

HashMap put 时会先根据 key 计算 hash 值，再根据 hash 定位数组下标。如果该位置为空，就直接放入；如果不为空，说明发生哈希冲突，会比较 key 是否相等，相等就覆盖 value，不相等就挂到链表或红黑树上。插入后如果元素数量超过阈值，就会触发扩容。

## 18. HashMap 如何计算数组下标？

核心公式：

```java
(n - 1) & hash
```

其中 `n` 是数组长度。

面试回答：

HashMap 通过 `(n - 1) & hash` 计算数组下标。因为 HashMap 的数组长度总是 2 的幂，所以这种位运算可以达到类似取模的效果，但性能更高。

## 19. HashMap 为什么容量是 2 的幂？

原因：

- 方便使用 `(n - 1) & hash` 计算下标
- 分布更均匀
- 扩容迁移效率更高

面试回答：

HashMap 容量保持为 2 的幂，是为了使用 `(n - 1) & hash` 这种位运算来计算下标，效率比取模更高。同时在容量为 2 的幂时，元素分布更均匀，扩容时元素要么留在原位置，要么移动到原位置加旧容量的位置，迁移效率也更高。

## 20. HashMap 的默认容量和负载因子是多少？

默认值：

- 默认容量：16
- 默认负载因子：0.75
- 扩容阈值：容量 * 负载因子

面试回答：

HashMap 默认初始容量是 16，默认负载因子是 0.75。当元素数量超过容量乘以负载因子的阈值时，会触发扩容。0.75 是时间和空间之间的折中，既能减少哈希冲突，又不会浪费太多空间。

## 21. HashMap 为什么负载因子默认是 0.75？

原因：

- 负载因子太小：空间浪费
- 负载因子太大：哈希冲突增加
- 0.75 是时间和空间的折中

面试回答：

负载因子表示 HashMap 的填充程度。负载因子越小，冲突越少但空间浪费越多；负载因子越大，空间利用率越高但冲突也越多。默认 0.75 是一个综合性能较好的折中值。

## 22. HashMap 什么时候扩容？

触发条件：

```text
size > threshold
threshold = capacity * loadFactor
```

面试回答：

HashMap 中元素数量超过扩容阈值时会触发扩容。扩容阈值等于数组容量乘以负载因子。默认容量 16，负载因子 0.75，所以默认阈值是 12。

## 23. HashMap 扩容过程是怎样的？

流程：

```text
创建 2 倍容量的新数组
遍历旧数组
重新分配元素位置
迁移链表或红黑树节点
```

面试回答：

HashMap 扩容时会创建一个容量为原来 2 倍的新数组，然后把旧数组中的元素迁移过去。JDK 8 中迁移时不需要重新计算完整 hash，而是通过判断 hash 与旧容量的按位与结果，决定元素留在原位置还是移动到原位置加旧容量的位置。

## 24. 什么是哈希冲突？

哈希冲突：

不同 key 经过 hash 计算后定位到同一个数组下标。

面试回答：

哈希冲突就是不同的 key 计算出的数组下标相同。HashMap 通过链表和红黑树解决冲突。冲突少时使用链表，链表过长并且数组容量足够时，会转成红黑树提升查询效率。

## 25. HashMap 为什么 JDK 8 引入红黑树？

原因：

- 链表过长时查询效率退化为 O(n)
- 红黑树查询效率是 O(log n)
- 可以避免极端哈希冲突导致性能下降

面试回答：

JDK 8 中 HashMap 引入红黑树，是为了优化哈希冲突严重时的查询效率。当某个桶中的链表过长时，查询复杂度会从接近 O(1) 退化为 O(n)。转成红黑树后，查询复杂度可以优化到 O(log n)。

## 26. HashMap 什么时候链表转红黑树？

条件：

- 链表长度达到 8
- 数组容量至少为 64

面试回答：

HashMap 中同一个桶的链表长度达到 8 时，并不会一定马上树化，还要看数组容量是否达到 64。如果容量小于 64，会优先扩容；如果容量已经达到 64，才会把链表转成红黑树。

## 27. HashMap 什么时候红黑树退化为链表？

条件：

- 红黑树节点数量减少到 6 左右时

面试回答：

HashMap 中红黑树节点数量减少到一定程度时，会退化回链表，阈值通常是 6。这样做是因为节点较少时，链表的维护成本更低，没有必要继续使用红黑树。

## 28. HashMap 是线程安全的吗？

结论：

HashMap 不是线程安全的。

面试回答：

HashMap 不是线程安全的。在多线程同时 put、resize 的情况下，可能出现数据覆盖、数据丢失等问题。JDK 7 中甚至可能在并发扩容时形成链表环。多线程场景通常使用 ConcurrentHashMap。

## 29. HashMap 在 JDK 7 和 JDK 8 中有什么区别？

区别：

- JDK 7：数组 + 链表
- JDK 8：数组 + 链表 + 红黑树
- JDK 7 链表插入使用头插法
- JDK 8 链表插入使用尾插法

面试回答：

JDK 7 的 HashMap 底层是数组加链表，链表插入使用头插法，并发扩容时可能出现链表环。JDK 8 引入红黑树，链表过长时可以树化，同时链表插入改为尾插法，降低了一些并发扩容导致的问题，但 HashMap 本身仍然不是线程安全的。

## 30. HashMap 和 Hashtable 有什么区别？

区别：

- HashMap 线程不安全
- Hashtable 方法加 synchronized，线程安全但性能差
- HashMap 允许一个 null key 和多个 null value
- Hashtable 不允许 null key 和 null value
- Hashtable 是较老的类

面试回答：

HashMap 和 Hashtable 都是键值对集合。HashMap 线程不安全，但性能更好，允许 null key 和 null value。Hashtable 是早期线程安全实现，方法上加了 synchronized，性能较差，也不允许 null。现在一般不推荐使用 Hashtable。

## 31. HashMap 和 ConcurrentHashMap 有什么区别？

区别：

- HashMap 线程不安全
- ConcurrentHashMap 线程安全
- HashMap 允许 null key 和 null value
- ConcurrentHashMap 不允许 null key 和 null value
- ConcurrentHashMap 适合并发场景

面试回答：

HashMap 适合单线程或外部保证线程安全的场景，ConcurrentHashMap 适合多线程并发访问。ConcurrentHashMap 内部通过更细粒度的同步机制保证线程安全，同时不允许 null key 和 null value，避免并发场景下产生歧义。

## 32. ConcurrentHashMap 为什么不允许 null？

原因：

在并发环境下，`get(key)` 返回 null 会有歧义：

- key 不存在
- key 存在但 value 是 null

面试回答：

ConcurrentHashMap 不允许 null，主要是为了避免并发环境下的歧义。如果 get 返回 null，无法判断是 key 不存在，还是 key 对应的 value 本身就是 null。在并发场景中，这种判断可能受到其他线程修改影响，所以直接禁止 null。

## 33. LinkedHashMap 有什么特点？

特点：

- 底层是 HashMap + 双向链表
- 可以维护插入顺序
- 也可以维护访问顺序
- 可用于实现 LRU 缓存

面试回答：

LinkedHashMap 在 HashMap 的基础上增加了双向链表，因此可以维护元素顺序。默认维护插入顺序，也可以设置为访问顺序。利用访问顺序和 removeEldestEntry 方法，可以实现简单的 LRU 缓存。

## 34. TreeMap 的底层原理是什么？

核心：

- TreeMap 底层是红黑树
- key 有序
- key 需要实现 Comparable 或提供 Comparator

面试回答：

TreeMap 底层基于红黑树实现，会按照 key 的自然顺序或自定义比较器排序。因此 TreeMap 适合需要按 key 排序、范围查询的场景，但普通查询性能通常不如 HashMap。

## 35. HashMap、LinkedHashMap、TreeMap 怎么选择？

选择：

- 只需要 key-value 快速查询：HashMap
- 需要保持插入顺序或访问顺序：LinkedHashMap
- 需要按 key 排序：TreeMap

面试回答：

如果只需要普通键值存储和快速查询，一般选择 HashMap。如果还需要保持插入顺序或访问顺序，可以选择 LinkedHashMap。如果需要按照 key 排序或者做范围查询，就选择 TreeMap。

## 36. HashMap 为什么适合用 String 作为 key？

原因：

- String 不可变
- hashCode 稳定
- equals 已重写
- 字符串常量池可以复用对象

面试回答：

String 很适合做 HashMap 的 key，因为 String 是不可变对象，创建后内容不会变，hashCode 稳定。同时 String 重写了 equals 和 hashCode，可以按照字符串内容判断相等。不可变也能避免 key 放入 Map 后被修改导致查找失败。

## 37. 如果 HashMap 的 key 是自定义对象，要注意什么？

注意：

- 重写 equals
- 重写 hashCode
- 尽量保证 key 的字段不可变

面试回答：

如果使用自定义对象作为 HashMap 的 key，必须正确重写 equals 和 hashCode，并保证二者语义一致。最好不要在对象放入 Map 后修改参与 equals 和 hashCode 计算的字段，否则可能导致后续无法正确查找到这个 key。

## 38. fail-fast 是什么？

含义：

集合遍历过程中，如果集合结构被非迭代器方式修改，可能快速失败并抛出 `ConcurrentModificationException`。

面试回答：

fail-fast 是集合的一种快速失败机制。比如在遍历 ArrayList 或 HashMap 时，如果直接修改集合结构，而不是通过迭代器修改，就可能抛出 ConcurrentModificationException。它主要用于尽早发现并发修改或错误修改问题。

## 39. 为什么遍历集合时删除元素容易报错？

原因：

- foreach 本质上使用 Iterator
- 直接调用集合的 remove 会修改 modCount
- Iterator 检测到不一致后抛出异常

面试回答：

foreach 遍历集合时底层使用迭代器。如果遍历过程中直接调用集合的 remove 方法，会改变集合的结构修改次数，而迭代器内部记录的期望修改次数没有同步更新，所以会触发 fail-fast，抛出 ConcurrentModificationException。正确方式是使用 Iterator 的 remove 方法。

## 40. 如何安全地遍历时删除元素？

方式：

```java
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String item = iterator.next();
    if ("a".equals(item)) {
        iterator.remove();
    }
}
```

面试回答：

遍历集合时如果要删除元素，推荐使用 Iterator 的 remove 方法，因为它会同步更新迭代器内部状态，避免 fail-fast。不要在 foreach 中直接调用集合的 remove 方法。

## 41. Iterator 和 ListIterator 有什么区别？

Iterator：

- 可以遍历 Collection
- 只能向后遍历
- 可以删除元素

ListIterator：

- 只能用于 List
- 可以双向遍历
- 可以添加、修改、删除元素

面试回答：

Iterator 是通用迭代器，可以遍历大多数 Collection 集合，主要支持向后遍历和删除。ListIterator 是 List 专用迭代器，功能更强，可以双向遍历，也可以在遍历过程中添加、修改元素。

## 42. Comparable 和 Comparator 有什么区别？

Comparable：

- 定义在类内部
- 通过 `compareTo` 实现自然排序

Comparator：

- 定义在类外部
- 通过 `compare` 实现自定义排序

面试回答：

Comparable 是对象自身具备比较能力，通常用于定义默认排序规则。Comparator 是外部比较器，可以在不修改类代码的情况下定义不同排序规则。如果一个类只有一种自然排序，可以实现 Comparable；如果需要多种排序方式，用 Comparator 更灵活。

## 43. Queue 和 Deque 有什么区别？

Queue：

- 队列
- 通常先进先出

Deque：

- 双端队列
- 两端都可以插入和删除

面试回答：

Queue 表示普通队列，通常按先进先出处理元素。Deque 是双端队列，可以在队头和队尾两端插入、删除元素，因此既可以当队列使用，也可以当栈使用。

## 44. ArrayDeque 和 LinkedList 作为队列怎么选择？

ArrayDeque：

- 基于数组
- 性能通常更好
- 不允许 null

LinkedList：

- 基于链表
- 可以作为 List 和 Deque
- 节点对象额外开销较大

面试回答：

如果只是作为队列或栈使用，通常优先选择 ArrayDeque，因为它基于数组实现，性能和内存利用率通常更好。LinkedList 功能更多，但每个节点都有额外指针开销，实际作为队列并不一定更优。

## 45. Collections 和 Collection 有什么区别？

Collection：

- 集合接口
- List、Set、Queue 的父接口

Collections：

- 工具类
- 提供排序、查找、同步包装、不可变包装等静态方法

面试回答：

Collection 是集合体系中的接口，表示一组元素。Collections 是集合工具类，提供很多静态工具方法，比如 sort、binarySearch、synchronizedList、unmodifiableList 等。一个是接口，一个是工具类。

## 46. Collections.synchronizedList 是怎么保证线程安全的？

核心：

- 返回一个同步包装对象
- 方法内部通过 synchronized 加锁

面试回答：

Collections.synchronizedList 会返回一个包装后的 List，对 List 的大部分操作方法加 synchronized，从而保证单个方法调用的线程安全。但遍历时仍然需要手动在同步块中进行，否则遍历过程中其他线程修改集合仍可能出问题。

## 47. unmodifiableList 是不可变集合吗？

结论：

它是不允许通过该包装对象修改集合，但不一定是真正不可变。

面试回答：

Collections.unmodifiableList 返回的是一个不可修改的包装视图，通过这个视图调用 add、remove 会抛异常。但如果原始 List 仍然被其他引用持有并修改，这个 unmodifiableList 看到的内容也会变化。所以它不是严格意义上的不可变集合。

## 48. Arrays.asList 有什么坑？

常见坑：

- 返回的是固定长度 List
- 不能 add/remove
- 修改元素会影响原数组
- 基本类型数组会被当成一个元素

面试回答：

Arrays.asList 返回的是基于原数组的固定长度 List，不支持 add 和 remove，否则会抛 UnsupportedOperationException。但可以 set 修改元素，并且会影响原数组。另外，如果传入基本类型数组，会把整个数组当成一个元素。

## 49. 如何把数组转换成真正可修改的 List？

方式：

```java
List<String> list = new ArrayList<>(Arrays.asList(array));
```

面试回答：

如果需要把数组转成可增删的 List，不要直接使用 Arrays.asList 的返回值，而应该再包装一层 ArrayList，比如 `new ArrayList<>(Arrays.asList(array))`。这样得到的是一个真正可修改的 ArrayList。

## 50. 集合面试题应该怎么回答？

回答模板：

```text
1. 先说它是什么
2. 再说底层数据结构
3. 然后说核心特点
4. 接着说使用场景
5. 最后补充注意事项或坑
```

## 51.【高频】ArrayList 和数组有什么区别？

区别：

- 数组长度固定，ArrayList 长度可动态扩容
- 数组可以存基本类型和引用类型，ArrayList 只能存引用类型
- 数组功能简单，ArrayList 提供了丰富的增删改查方法
- ArrayList 底层仍然是数组

面试回答：

数组是一种固定长度的数据结构，创建后长度不能改变，可以存基本类型和引用类型。ArrayList 是基于数组封装的集合，长度可以动态扩容，并提供了更方便的增删改查方法。实际开发中，如果数据长度固定且追求极致性能，可以用数组；普通业务列表通常用 ArrayList。

## 52.【高频】ArrayList 插入和删除元素的时间复杂度？

复杂度：

- 尾部添加：通常 O(1)，扩容时 O(n)
- 指定位置插入：O(n)
- 删除尾部元素：O(1)
- 删除指定位置元素：O(n)

面试回答：

ArrayList 底层是数组，所以尾部添加通常是 O(1)，但如果触发扩容，需要复制数组，复杂度是 O(n)。在中间插入或删除元素时，需要移动后续元素，因此复杂度是 O(n)。按下标查询是 O(1)。

## 53.【高频】LinkedList 插入和删除元素的时间复杂度？

复杂度：

- 已知节点位置时插入删除：O(1)
- 按下标查找后再插入删除：O(n)
- 头尾插入删除：O(1)

面试回答：

LinkedList 底层是双向链表，如果已经定位到节点，插入和删除只需要修改前后指针，复杂度是 O(1)。但如果是按照下标操作，需要先遍历查找节点，查找过程是 O(n)。所以不能简单说 LinkedList 插入删除一定比 ArrayList 快，要看是否已经定位到节点。

## 54. LinkedList 为什么不能实现 RandomAccess 接口？

原因：

- RandomAccess 表示支持高效随机访问
- LinkedList 按下标访问需要遍历链表

面试回答：

RandomAccess 是一个标记接口，用来表示集合支持高效随机访问。ArrayList 底层是数组，可以通过下标 O(1) 访问，所以实现了 RandomAccess。LinkedList 底层是链表，按下标访问需要遍历，复杂度是 O(n)，因此没有实现 RandomAccess。

## 55. ArrayList 可以添加 null 吗？

结论：

ArrayList 可以添加 null。

面试回答：

ArrayList 可以存放 null，因为它底层是 Object 数组，对元素是否为 null 没有限制。需要注意的是，如果后续业务逻辑没有做好空值判断，遍历或调用元素方法时可能出现 NullPointerException。

## 56. 如何选用集合？

选择思路：

- 需要有序、可重复：List
- 查询多、随机访问多：ArrayList
- 需要去重：Set
- 需要排序去重：TreeSet
- 需要 key-value：Map
- 需要保持插入顺序：LinkedHashMap 或 LinkedHashSet
- 需要按 key 排序：TreeMap
- 并发场景：ConcurrentHashMap 等并发集合

面试回答：

集合选择主要看数据结构需求。如果是普通列表，优先 ArrayList；需要去重用 HashSet；需要排序用 TreeSet 或 TreeMap；需要 key-value 查询用 HashMap；需要保持顺序用 LinkedHashMap；并发场景要选择并发集合，不能直接使用普通 HashMap。

## 57. 为什么要使用集合？

原因：

- 数组长度固定，不够灵活
- 集合提供动态扩容
- 集合提供丰富 API
- 集合封装了常用数据结构

面试回答：

数组适合存储固定长度的数据，但长度不可变，操作也比较基础。集合框架封装了常用数据结构，提供动态扩容、排序、去重、键值映射等能力，开发效率和可读性都更好。

## 58.【高频】fail-fast 和 fail-safe 有什么区别？

fail-fast：

- 遍历时检测到结构被修改，会快速抛出异常
- 常见于 ArrayList、HashMap 等普通集合

fail-safe：

- 遍历时基于副本或弱一致性机制
- 不一定抛出并发修改异常
- 常见于 CopyOnWriteArrayList、ConcurrentHashMap

面试回答：

fail-fast 是快速失败机制，遍历集合时如果检测到集合结构被非迭代器方式修改，可能抛出 ConcurrentModificationException。fail-safe 则不会直接在原集合上遍历，或者采用弱一致性迭代，因此遍历过程中修改集合不一定报错。普通集合多是 fail-fast，并发集合更多体现 fail-safe 或弱一致性。

## 59. HashSet、LinkedHashSet、TreeSet 三者有什么区别？

区别：

- HashSet：无序，查询效率高
- LinkedHashSet：保持插入顺序
- TreeSet：按照比较规则排序

面试回答：

HashSet 基于 HashMap，适合普通去重；LinkedHashSet 基于 LinkedHashMap，在去重的同时维护插入顺序；TreeSet 基于 TreeMap，会按照自然顺序或自定义比较器排序。选择时看是否需要顺序和排序。

## 60. HashMap 和 HashSet 有什么区别？

区别：

- HashMap 存 key-value
- HashSet 存单个元素
- HashSet 底层基于 HashMap
- HashSet 的元素作为 HashMap 的 key

面试回答：

HashMap 是键值对集合，用于根据 key 查找 value。HashSet 是去重集合，只保存元素本身。HashSet 底层其实使用 HashMap 实现，元素作为 HashMap 的 key，value 是固定占位对象。

## 61. HashMap 常见遍历方式有哪些？

常见方式：

- 遍历 `entrySet`
- 遍历 `keySet`
- 使用 `forEach`
- 使用迭代器

面试回答：

HashMap 常见遍历方式有遍历 entrySet、遍历 keySet、使用 forEach 或迭代器。一般推荐遍历 entrySet，因为可以一次拿到 key 和 value，避免通过 key 再次 get 查询 value。

## 62. HashMap 和 TreeMap 有什么区别？

区别：

- HashMap 基于哈希表，查询通常更快
- TreeMap 基于红黑树，key 有序
- HashMap 允许一个 null key
- TreeMap 默认不允许 null key

面试回答：

HashMap 适合普通 key-value 快速查找，元素没有排序保证。TreeMap 底层是红黑树，会按照 key 的自然顺序或比较器排序，适合需要排序和范围查询的场景。普通查询用 HashMap，需要有序用 TreeMap。

## 63. PriorityQueue 是什么？

特点：

- 优先级队列
- 默认小顶堆
- 出队元素按优先级决定，不是普通 FIFO
- 不允许 null

面试回答：

PriorityQueue 是优先级队列，底层通常基于堆结构实现。它不是按照插入顺序出队，而是按照元素自然顺序或比较器定义的优先级出队。常用于 TopK、任务优先级调度等场景。

## 64. BlockingQueue 是什么？

定义：

BlockingQueue 是阻塞队列。

特点：

- 队列满时，生产者可以阻塞等待
- 队列空时，消费者可以阻塞等待
- 常用于生产者消费者模型

面试回答：

BlockingQueue 是并发包中的阻塞队列，常用于生产者消费者模型。它在队列满时可以阻塞生产者，在队列空时可以阻塞消费者，从而简化线程之间的数据交换。常见实现有 ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue、DelayQueue 等。

## 65.【高频】ArrayBlockingQueue 和 LinkedBlockingQueue 有什么区别？

区别：

- ArrayBlockingQueue 底层是数组，有界
- LinkedBlockingQueue 底层是链表，可以有界，也可以近似无界
- ArrayBlockingQueue 通常使用一把锁
- LinkedBlockingQueue 读写分离锁，并发能力相对更好

面试回答：

ArrayBlockingQueue 基于数组实现，创建时必须指定容量，是有界队列。LinkedBlockingQueue 基于链表实现，可以指定容量，如果不指定容量，容量会非常大，使用时要注意内存风险。它们都常用于生产者消费者场景。

## 66.【高频】ConcurrentHashMap 如何保证线程安全？

JDK 8 核心：

- 数组 + 链表 + 红黑树
- CAS 初始化和插入空桶
- synchronized 锁定桶头节点
- volatile 保证可见性
- 分段计数降低竞争

面试回答：

JDK 8 的 ConcurrentHashMap 底层结构和 HashMap 类似，也是数组、链表和红黑树。它通过 CAS 处理初始化和空桶插入，通过 synchronized 锁住桶头节点来保证同一个桶内写操作安全，同时使用 volatile 保证可见性。相比 Hashtable 锁整个表，ConcurrentHashMap 锁粒度更细，并发性能更好。

## 67.【高频】JDK 1.7 和 JDK 1.8 的 ConcurrentHashMap 有什么不同？

区别：

- JDK 1.7：Segment 分段锁
- JDK 1.8：取消 Segment，使用数组 + 链表 + 红黑树 + CAS + synchronized

面试回答：

JDK 1.7 的 ConcurrentHashMap 使用 Segment 分段锁，每个 Segment 类似一个小 HashMap。JDK 1.8 取消了 Segment 的核心锁设计，改为数组、链表、红黑树结构，并通过 CAS 和 synchronized 对桶级别操作进行控制，锁粒度更细，结构也更接近 HashMap。

## 68.【高频】ConcurrentHashMap 能保证复合操作原子性吗？

结论：

单个方法调用线程安全，但多个操作组合不一定原子。

面试回答：

ConcurrentHashMap 能保证单个操作的线程安全，比如 put、get、remove。但多个操作组合起来不一定具备原子性，比如先 get 再 put，中间可能被其他线程修改。如果需要复合操作原子性，应使用 putIfAbsent、compute、computeIfAbsent、merge 等原子方法。

## 69. 集合转数组要注意什么？

推荐写法：

```java
String[] array = list.toArray(new String[0]);
```

面试回答：

集合转数组时不建议使用无参 toArray，因为返回的是 Object[]，强转可能出错。推荐使用带类型数组参数的 toArray，比如 `list.toArray(new String[0])`，这样可以得到正确类型的数组，代码也更安全。

## 70. 集合转 Map 时 key 重复怎么办？

问题：

使用 Stream 转 Map 时，如果 key 重复且没有指定合并策略，会抛异常。

示例：

```java
Map<Long, User> map = list.stream()
        .collect(Collectors.toMap(User::getId, item -> item, (a, b) -> a));
```

面试回答：

集合转 Map 时要考虑 key 重复问题。如果使用 `Collectors.toMap`，没有指定合并函数时，遇到重复 key 会抛异常。实际开发中应根据业务选择保留旧值、保留新值，或者把 value 收集成 List。

示例：回答 HashMap

```text
HashMap 是 Java 中常用的 key-value 集合。
JDK 8 中底层是数组、链表和红黑树。
put 时会根据 key 的 hash 定位数组下标，冲突时使用链表或红黑树处理。
它查询效率通常很高，但线程不安全。
单线程场景常用 HashMap，多线程场景可以使用 ConcurrentHashMap。
```
