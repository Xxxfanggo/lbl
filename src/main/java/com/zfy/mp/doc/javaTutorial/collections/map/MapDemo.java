package com.zfy.mp.doc.javaTutorial.collections.map;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @文件名: MapDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.collections.map
 * @描述: Map 接口及实现类示例
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class MapDemo {

    public static void main(String[] args) {
        hashMapDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        treeMapDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        linkedHashMapDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        mapTraversal();
        System.out.println("\n" + "=".repeat(50) + "\n");
        mapCommonOperations();
        System.out.println("\n" + "=".repeat(50) + "\n");
        hashMapInternal();
    }

    /**
     * HashMap 示例 - 哈希表实现
     * 特点：无序，O(1) get/put
     * 适用：最常用的 Map，快速查找
     */
    private static void hashMapDemo() {
        System.out.println("【HashMap 示例】");

        Map<String, Integer> map = new HashMap<>();

        // 添加元素
        map.put("Apple", 5);
        map.put("Banana", 3);
        map.put("Cherry", 7);
        System.out.println("添加元素: " + map);

        // 获取元素
        System.out.println("获取 Apple 的数量: " + map.get("Apple"));
        System.out.println("获取不存在的元素: " + map.getOrDefault("Grape", 0));

        // 修改元素
        map.put("Apple", 10);
        System.out.println("修改 Apple: " + map.get("Apple"));

        // 计算更新
        map.compute("Banana", (k, v) -> v == null ? 1 : v + 2);
        System.out.println("Banana + 2: " + map.get("Banana"));

        // 检查包含
        System.out.println("包含 Apple? " + map.containsKey("Apple"));
        System.out.println("包含值 7? " + map.containsValue(7));

        // 删除元素
        map.remove("Cherry");
        System.out.println("删除 Cherry: " + map);

        // 大小
        System.out.println("Map大小: " + map.size());
        System.out.println("是否为空: " + map.isEmpty());
    }

    /**
     * TreeMap 示例 - 红黑树实现
     * 特点：按 Key 排序，O(log n) get/put
     * 适用：需要按 Key 排序
     */
    private static void treeMapDemo() {
        System.out.println("【TreeMap 示例】");

        TreeMap<Integer, String> map = new TreeMap<>();

        // 添加元素（自动按 Key 排序）
        map.put(30, "Thirty");
        map.put(10, "Ten");
        map.put(50, "Fifty");
        map.put(20, "Twenty");
        map.put(40, "Forty");
        System.out.println("添加元素（按Key排序）: " + map);

        // TreeMap 特有方法
        System.out.println("第一个Key: " + map.firstKey());
        System.out.println("最后一个Key: " + map.lastKey());
        System.out.println("小于30的最大Key: " + map.lowerKey(30));
        System.out.println("大于30的最小Key: " + map.higherKey(30));

        // 子Map
        System.out.println("headMap(30): " + map.headMap(30));     // Key < 30
        System.out.println("tailMap(30): " + map.tailMap(30));     // Key >= 30
        System.out.println("subMap(20, 40): " + map.subMap(20, 40)); // [20, 40)

        // 自定义排序（按字符串长度）
        TreeMap<String, Integer> lenMap = new TreeMap<>(Comparator.comparing(String::length));
        lenMap.put("cat", 1);
        lenMap.put("elephant", 2);
        lenMap.put("dog", 3);
        System.out.println("\n按字符串长度排序: " + lenMap);
    }

    /**
     * LinkedHashMap 示例 - 哈希表+链表
     * 特点：保持插入顺序或访问顺序
     * 适用：需要保持顺序的场景
     */
    private static void linkedHashMapDemo() {
        System.out.println("【LinkedHashMap 示例】");

        // 按插入顺序
        Map<String, Integer> insertOrderMap = new LinkedHashMap<>();
        insertOrderMap.put("First", 1);
        insertOrderMap.put("Second", 2);
        insertOrderMap.put("Third", 3);
        System.out.println("按插入顺序: " + insertOrderMap);

        // 按访问顺序（LRU缓存常用）
        Map<String, Integer> accessOrderMap = new LinkedHashMap<>(16, 0.75f, true);
        accessOrderMap.put("A", 1);
        accessOrderMap.put("B", 2);
        accessOrderMap.put("C", 3);
        System.out.println("初始: " + accessOrderMap);

        // 访问元素会改变顺序
        accessOrderMap.get("A");  // A被访问，移到末尾
        System.out.println("访问A后: " + accessOrderMap);

        accessOrderMap.get("C");  // C被访问，移到末尾
        System.out.println("访问C后: " + accessOrderMap);

        // 简单的 LRU 缓存实现
        class LRUCache<K, V> extends LinkedHashMap<K, V> {
            private final int capacity;

            public LRUCache(int capacity) {
                super(16, 0.75f, true);
                this.capacity = capacity;
            }

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        }

        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("1", "A");
        cache.put("2", "B");
        cache.put("3", "C");
        System.out.println("\nLRU缓存(容量3): " + cache);

        cache.put("4", "D");  // 超过容量，最老的(1)被移除
        System.out.println("添加4后: " + cache);

        cache.get("2");        // 2被访问，移到最末尾
        System.out.println("访问2后: " + cache);

        cache.put("5", "E");  // 超过容量，最老的(3)被移除
        System.out.println("添加5后: " + cache);
    }

    /**
     * Map 遍历方式
     */
    private static void mapTraversal() {
        System.out.println("【Map 遍历方式】");

        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 5);
        map.put("Banana", 3);
        map.put("Cherry", 7);

        // 方式1：遍历 Key
        System.out.println("方式1 - 遍历 Key:");
        for (String key : map.keySet()) {
            System.out.printf("  %s = %d%n", key, map.get(key));
        }

        // 方式2：遍历 Value
        System.out.println("\n方式2 - 遍历 Value:");
        for (Integer value : map.values()) {
            System.out.println("  " + value);
        }

        // 方式3：遍历 Entry（推荐）
        System.out.println("\n方式3 - 遍历 Entry (推荐):");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.printf("  %s = %d%n", entry.getKey(), entry.getValue());
        }

        // 方式4：forEach (Java 8+)
        System.out.println("\n方式4 - forEach (Java 8+):");
        map.forEach((k, v) -> System.out.printf("  %s = %d%n", k, v));

        // 方式5：Stream API
        System.out.println("\n方式5 - Stream API:");
        map.entrySet().stream()
                .filter(e -> e.getValue() > 4)
                .forEach(e -> System.out.printf("  %s = %d%n", e.getKey(), e.getValue()));
    }

    /**
     * Map 常用操作
     */
    private static void mapCommonOperations() {
        System.out.println("【Map 常用操作】");

        Map<String, Integer> map = new HashMap<>();

        // putIfAbsent - 仅当key不存在时才put
        map.put("Apple", 5);
        map.putIfAbsent("Apple", 10);  // Apple已存在，不会修改
        map.putIfAbsent("Banana", 3);  // Banana不存在，会添加
        System.out.println("putIfAbsent: " + map);

        // getOrDefault - 获取不存在的key时返回默认值
        System.out.println("get Apple: " + map.getOrDefault("Apple", 0));
        System.out.println("get Grape: " + map.getOrDefault("Grape", 0));

        // compute - 计算新值
        map.compute("Apple", (k, v) -> v + 5);
        System.out.println("compute Apple+5: " + map);

        // computeIfAbsent - key不存在时计算值
        map.computeIfAbsent("Cherry", k -> 7);
        System.out.println("computeIfAbsent: " + map);

        // computeIfPresent - key存在时计算值
        map.computeIfPresent("Grape", (k, v) -> v * 2);  // Grape不存在，不执行
        System.out.println("computeIfPresent (Grape不存在): " + map);

        // merge - 合并值
        map.merge("Apple", 10, Integer::sum);
        System.out.println("merge Apple+10: " + map);

        // replace
        map.replace("Apple", 20);
        System.out.println("replace Apple: " + map);

        // replaceAll
        map.replaceAll((k, v) -> v * 2);
        System.out.println("replaceAll x2: " + map);

        // 批量操作
        Map<String, Integer> other = new HashMap<>();
        other.put("Date", 8);
        other.put("Elderberry", 4);

        map.putAll(other);
        System.out.println("putAll: " + map);

        // 统计单词频率
        String text = "apple banana apple cherry banana apple";
        Map<String, Long> wordCount = Arrays.stream(text.split(" "))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
        System.out.println("\n单词频率统计: " + wordCount);
    }

    /**
     * HashMap 内部原理
     */
    private static void hashMapInternal() {
        System.out.println("【HashMap 内部原理】");

        // 初始容量和负载因子
        Map<String, Integer> map1 = new HashMap<>();           // 默认: 容量16, 负载因子0.75
        Map<String, Integer> map2 = new HashMap<>(32);          // 指定初始容量
        Map<String, Integer> map3 = new HashMap<>(32, 0.8f);   // 指定容量和负载因子

        // 负载因子 = size / capacity
        // 当 size > capacity * loadFactor 时，扩容为原来的2倍
        // 默认当元素超过 16 * 0.75 = 12 时扩容到 32

        System.out.println("初始容量: 16");
        System.out.println("负载因子: 0.75");
        System.out.println("扩容阈值: 12 (16 * 0.75)");

        // 扩容演示
        Map<Integer, String> resizeMap = new HashMap<>(4);
        System.out.println("\n扩容演示（初始容量4）:");
        for (int i = 1; i <= 5; i++) {
            resizeMap.put(i, "Value" + i);
            System.out.printf("添加%d个元素后, size=%d, capacity=%d%n",
                    i, resizeMap.size(), getCapacity(resizeMap));
        }
    }

    // 通过反射获取Map容量（仅用于演示）
    private static int getCapacity(Map<?, ?> map) {
        try {
            java.lang.reflect.Field field = map.getClass().getDeclaredField("table");
            field.setAccessible(true);
            Object[] table = (Object[]) field.get(map);
            return table == null ? 0 : table.length;
        } catch (Exception e) {
            return -1;
        }
    }
}
