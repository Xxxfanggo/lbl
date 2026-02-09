package com.zfy.mp.doc.javaTutorial.collections.list;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @文件名: ListDemo.java
 * @工程名: mp
 * @包名: com.zfy.mp.doc.javaTutorial.collections.list
 * @描述: List 接口及实现类示例
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class ListDemo {

    public static void main(String[] args) {
        arrayListDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        linkedListDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        listCommonMethods();
        System.out.println("\n" + "=".repeat(50) + "\n");
        performanceComparison();
    }

    /**
     * ArrayList 示例 - 动态数组
     * 特点：查询快 O(1)，增删慢 O(n)
     * 适用于：频繁查询，少量增删
     */
    private static void arrayListDemo() {
        System.out.println("【ArrayList 示例】");

        // 创建 ArrayList
        List<String> list = new ArrayList<>();

        // 添加元素
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        list.add(1, "Blueberry"); // 在指定位置插入
        System.out.println("添加元素后: " + list);

        // 查询元素 - O(1) 时间复杂度
        String fruit = list.get(2);
        System.out.println("索引2的元素: " + fruit);

        // 修改元素
        list.set(0, "Apricot");
        System.out.println("修改索引0后: " + list);

        // 删除元素
        list.remove("Blueberry"); // 按值删除
        list.remove(0);          // 按索引删除
        System.out.println("删除后: " + list);

        // 检查包含
        System.out.println("包含 Cherry? " + list.contains("Cherry"));

        // 大小
        System.out.println("列表大小: " + list.size());

        // 遍历
        System.out.println("for-each 遍历:");
        for (String item : list) {
            System.out.println("  - " + item);
        }

        // 转换为数组
        String[] array = list.toArray(new String[0]);
        System.out.println("转数组: " + Arrays.toString(array));
    }

    /**
     * LinkedList 示例 - 双向链表
     * 特点：增删快 O(1)，查询慢 O(n)
     * 适用于：频繁增删，头部/尾部操作
     */
    private static void linkedListDemo() {
        System.out.println("【LinkedList 示例】");

        LinkedList<Integer> list = new LinkedList<>();

        // 添加元素
        list.add(10);           // 尾部添加
        list.addFirst(5);       // 头部添加
        list.addLast(15);       // 尾部添加
        list.add(1, 7);         // 指定位置添加
        System.out.println("添加元素后: " + list);

        // 双端队列操作
        list.offer(20);         // 尾部添加（类似addLast）
        list.offerFirst(2);     // 头部添加
        System.out.println("双端操作后: " + list);

        // 获取元素
        System.out.println("getFirst: " + list.getFirst());
        System.out.println("getLast: " + list.getLast());
        System.out.println("peek: " + list.peek());     // 返回头部，不删除
        System.out.println("peekLast: " + list.peekLast());

        // 删除元素
        list.removeFirst();     // 删除头部
        list.pollLast();        // 删除尾部
        System.out.println("删除首尾后: " + list);

        // LinkedList 可以当作 栈 使用
        LinkedList<String> stack = new LinkedList<>();
        stack.push("A");        // 压栈
        stack.push("B");
        stack.push("C");
        System.out.println("栈操作:");
        while (!stack.isEmpty()) {
            System.out.println("  弹出: " + stack.pop());
        }
    }

    /**
     * List 通用方法
     */
    private static void listCommonMethods() {
        System.out.println("【List 通用方法】");

        List<Integer> numbers = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));

        // 排序
        Collections.sort(numbers);
        System.out.println("排序后: " + numbers);

        // 反转
        Collections.reverse(numbers);
        System.out.println("反转后: " + numbers);

        // 打乱
        Collections.shuffle(numbers);
        System.out.println("打乱后: " + numbers);

        // 二分查找（需要先排序）
        Collections.sort(numbers);
        int index = Collections.binarySearch(numbers, 5);
        System.out.println("查找5的位置: " + index);

        // 最值
        System.out.println("最大值: " + Collections.max(numbers));
        System.out.println("最小值: " + Collections.min(numbers));

        // 频率统计
        System.out.println("1出现的次数: " + Collections.frequency(numbers, 1));

        // Stream API 操作
        System.out.println("\nStream 操作:");
        numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * 10)
                .sorted()
                .forEach(n -> System.out.print(n + " "));
        System.out.println();

        // List 转 Set 去重
        Set<Integer> uniqueNumbers = new LinkedHashSet<>(numbers);
        System.out.println("去重后: " + uniqueNumbers);
    }

    /**
     * 性能对比演示
     */
    private static void performanceComparison() {
        System.out.println("【ArrayList vs LinkedList 性能对比】");

        int size = 100000;

        // 测试随机访问
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        // 初始化
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        // 测试 get(index) - ArrayList 远快于 LinkedList
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            arrayList.get(i);
        }
        long arrayListGetTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            linkedList.get(i);
        }
        long linkedListGetTime = System.nanoTime() - start;

        System.out.println("get(index) 1000次:");
        System.out.println("  ArrayList: " + arrayListGetTime + " ns");
        System.out.println("  LinkedList: " + linkedListGetTime + " ns");
        System.out.println("  LinkedList慢了: " + (linkedListGetTime / arrayListGetTime) + " 倍");

        // 测试头部插入 - LinkedList 快于 ArrayList
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            linkedList.addFirst(i);
        }
        long linkedListAddFirstTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            arrayList.add(0, i);
        }
        long arrayListAddFirstTime = System.nanoTime() - start;

        System.out.println("\naddFirst 1000次:");
        System.out.println("  LinkedList: " + linkedListAddFirstTime + " ns");
        System.out.println("  ArrayList: " + arrayListAddFirstTime + " ns");
        System.out.println("  ArrayList慢了: " + (arrayListAddFirstTime / linkedListAddFirstTime) + " 倍");
    }
}
