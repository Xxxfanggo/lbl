package com.zfy.mp.doc.javaTutorial.collections.set;

import java.util.*;

/**
 * @文件名: SetDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.mp.doc.javaTutorial.collections.set
 * @描述: Set 接口及实现类示例
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class SetDemo {

    public static void main(String[] args) {
        hashSetDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        treeSetDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        linkedHashSetDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        setOperations();
        System.out.println("\n" + "=".repeat(50) + "\n");
        customObjectSet();
    }

    /**
     * HashSet 示例 - 哈希表实现
     * 特点：无序，O(1) 查找/插入
     * 适用：快速查找，不需要排序
     */
    private static void hashSetDemo() {
        System.out.println("【HashSet 示例】");

        Set<String> set = new HashSet<>();

        // 添加元素（自动去重）
        set.add("Apple");
        set.add("Banana");
        set.add("Cherry");
        set.add("Apple");  // 重复元素不会被添加
        set.add("Banana"); // 重复元素不会被添加
        System.out.println("添加5个元素（含重复）: " + set);
        System.out.println("实际大小: " + set.size());

        // 检查包含 - O(1) 时间复杂度
        System.out.println("包含 Apple? " + set.contains("Apple"));
        System.out.println("包含 Grape? " + set.contains("Grape"));

        // 删除元素
        set.remove("Banana");
        System.out.println("删除 Banana 后: " + set);

        // 遍历（无序）
        System.out.println("遍历（顺序不确定）:");
        for (String item : set) {
            System.out.println("  - " + item);
        }

        // 批量操作
        Set<String> other = new HashSet<>();
        other.add("Date");
        other.add("Elderberry");

        set.addAll(other);    // 并集
        System.out.println("并集操作后: " + set);

        set.retainAll(other); // 交集
        System.out.println("交集操作后: " + set);
    }

    /**
     * TreeSet 示例 - 红黑树实现
     * 特点：有序（自然排序或自定义排序），O(log n) 查找/插入
     * 适用：需要排序的场景
     */
    private static void treeSetDemo() {
        System.out.println("【TreeSet 示例】");

        TreeSet<Integer> set = new TreeSet<>();

        // 添加元素（自动排序）
        set.add(30);
        set.add(10);
        set.add(50);
        set.add(20);
        set.add(40);
        System.out.println("添加元素（自动升序）: " + set);

        // TreeSet 特有方法
        System.out.println("第一个元素: " + set.first());
        System.out.println("最后一个元素: " + set.last());
        System.out.println("小于30的最大元素: " + set.lower(30));
        System.out.println("大于30的最小元素: " + set.higher(30));
        System.out.println("小于等于30的最大元素: " + set.floor(30));
        System.out.println("大于等于30的最小元素: " + set.ceiling(30));

        // 子集视图
        System.out.println("headSet(30): " + set.headSet(30));      // 小于30
        System.out.println("tailSet(30): " + set.tailSet(30));      // 大于等于30
        System.out.println("subSet(20, 40): " + set.subSet(20, 40)); // [20, 40)

        // 自定义排序（降序）
        TreeSet<Integer> descSet = new TreeSet<>(Comparator.reverseOrder());
        descSet.addAll(Arrays.asList(10, 30, 20, 50, 40));
        System.out.println("降序排列: " + descSet);

        // 字符串按长度排序
        TreeSet<String> strSet = new TreeSet<>(Comparator.comparing(String::length));
        strSet.add("cat");
        strSet.add("elephant");
        strSet.add("dog");
        strSet.add("ant");
        System.out.println("按长度排序: " + strSet);
    }

    /**
     * LinkedHashSet 示例 - 哈希表+链表
     * 特点：保持插入顺序，O(1) 查找/插入
     * 适用：需要去重且保持顺序
     */
    private static void linkedHashSetDemo() {
        System.out.println("【LinkedHashSet 示例】");

        Set<String> set = new LinkedHashSet<>();

        // 添加元素（保持插入顺序）
        set.add("First");
        set.add("Second");
        set.add("Third");
        set.add("First");  // 重复
        System.out.println("添加元素（保持插入顺序）: " + set);

        // 遍历（按插入顺序）
        System.out.println("遍历（按插入顺序）:");
        for (String item : set) {
            System.out.println("  - " + item);
        }

        // 常见应用：去除List中重复元素并保持顺序
        List<Integer> list = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5);
        Set<Integer> uniqueSet = new LinkedHashSet<>(list);
        System.out.println("\nList: " + list);
        System.out.println("去重后: " + uniqueSet);
    }

    /**
     * Set 集合运算
     */
    private static void setOperations() {
        System.out.println("【Set 集合运算】");

        Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

        System.out.println("集合A: " + setA);
        System.out.println("集合B: " + setB);

        // 并集
        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("并集 (A ∪ B): " + union);

        // 交集
        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("交集 (A ∩ B): " + intersection);

        // 差集 (A - B)
        Set<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB);
        System.out.println("差集 (A - B): " + difference);

        // 对称差集 (A △ B) = (A ∪ B) - (A ∩ B)
        Set<Integer> symmetricDiff = new HashSet<>(union);
        symmetricDiff.removeAll(intersection);
        System.out.println("对称差集 (A △ B): " + symmetricDiff);
    }

    /**
     * 自定义对象在Set中的使用
     * 重要：必须正确重写 hashCode() 和 equals()
     */
    private static void customObjectSet() {
        System.out.println("【自定义对象 Set】");

        Set<Person> set = new HashSet<>();
        set.add(new Person("Alice", 25));
        set.add(new Person("Bob", 30));
        set.add(new Person("Charlie", 35));
        set.add(new Person("Alice", 25)); // 重复对象（相同的hashCode和equals）

        System.out.println("Set中的Person对象数量: " + set.size());

        for (Person p : set) {
            System.out.println("  - " + p);
        }

        // TreeSet 需要实现 Comparable 或提供 Comparator
        TreeSet<Person> treeSet = new TreeSet<>(Comparator.comparing(Person::getAge));
        treeSet.addAll(set);
        System.out.println("\n按年龄排序的TreeSet:");
        for (Person p : treeSet) {
            System.out.println("  - " + p);
        }
    }

    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }

        // 必须重写 equals 和 hashCode 才能在 Set 中正确去重
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            return String.format("%s(%d)", name, age);
        }
    }
}
