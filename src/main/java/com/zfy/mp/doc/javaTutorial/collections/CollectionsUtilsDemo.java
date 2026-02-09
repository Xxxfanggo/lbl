package com.zfy.mp.doc.javaTutorial.collections;

import java.util.*;

/**
 * @文件名: CollectionsUtilsDemo.java
 * @工程名: mp
 * @包名: com.zfy.mp.doc.javaTutorial.collections
 * @描述: Collections 工具类方法使用示例
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-26
 * @版本号: V2.4.0
 */
public class CollectionsUtilsDemo {

    public static void main(String[] args) {
        sortMethods();
        System.out.println("\n" + "=".repeat(50) + "\n");
        searchMethods();
        System.out.println("\n" + "=".repeat(50) + "\n");
        modifyMethods();
        System.out.println("\n" + "=".repeat(50) + "\n");
        queryMethods();
        System.out.println("\n" + "=".repeat(50) + "\n");
        immutableMethods();
        System.out.println("\n" + "=".repeat(50) + "\n");
        specialMethods();
        System.out.println("\n" + "=".repeat(50) + "\n");
        singletonMethods();
    }

    /**
     * 排序相关方法
     */
    private static void sortMethods() {
        System.out.println("【排序相关方法】");

        // sort - 自然排序
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3));
        System.out.println("原始列表: " + numbers);
        Collections.sort(numbers);
        System.out.println("sort排序后: " + numbers);

        // sort - 自定义排序
        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie", "David"));
        System.out.println("\n原始名字: " + names);
        Collections.sort(names, Comparator.reverseOrder()); // 逆序
        System.out.println("逆序排序: " + names);

        // sort - 按字符串长度排序
        Collections.sort(names, Comparator.comparing(String::length));
        System.out.println("按长度排序: " + names);

        // sort - 多条件排序
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 20, 90));
        students.add(new Student("Bob", 18, 85));
        students.add(new Student("Charlie", 20, 88));
        students.add(new Student("David", 18, 92));

        System.out.println("\n原始学生列表:");
        students.forEach(s -> System.out.println("  " + s));

        // 先按年龄升序，再按分数降序
        Collections.sort(students, Comparator
                .comparing(Student::getAge)
                .thenComparing(Student::getScore).reversed());
        System.out.println("按年龄升序、分数降序排序:");
        students.forEach(s -> System.out.println("  " + s));

        // swap - 交换元素
        Collections.swap(numbers, 0, numbers.size() - 1);
        System.out.println("\nswap首尾后: " + numbers);

        // rotate - 旋转
        List<Integer> rotateList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("\n旋转前: " + rotateList);
        Collections.rotate(rotateList, 2); // 向右旋转2位
        System.out.println("rotate右移2位: " + rotateList);
        Collections.rotate(rotateList, -2); // 向左旋转2位
        System.out.println("rotate左移2位: " + rotateList);

        // shuffle - 打乱顺序
        List<Integer> shuffleList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("\n打乱前: " + shuffleList);
        Collections.shuffle(shuffleList);
        System.out.println("打乱后: " + shuffleList);

        // reverse - 反转
        Collections.reverse(numbers);
        System.out.println("reverse反转后: " + numbers);
    }

    /**
     * 查找相关方法
     */
    private static void searchMethods() {
        System.out.println("【查找相关方法】");

        List<Integer> numbers = Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15);
        System.out.println("排序列表: " + numbers);

        // binarySearch - 二分查找（必须已排序）
        int index = Collections.binarySearch(numbers, 7);
        System.out.println("查找7的位置: " + index);

        // 查找不存在的元素
        index = Collections.binarySearch(numbers, 6);
        System.out.println("查找6的位置: " + index);
        System.out.println("  (负数表示不存在，插入位置为 " + (-index - 1) + ")");

        // 使用自定义比较器
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        index = Collections.binarySearch(names, "Bob");
        System.out.println("\n查找Bob的位置: " + index);
    }

    /**
     * 修改相关方法
     */
    private static void modifyMethods() {
        System.out.println("【修改相关方法】");

        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("原始列表: " + numbers);

        // fill - 填充
        Collections.fill(numbers, 0);
        System.out.println("fill填充0: " + numbers);

        // copy - 复制
        List<Integer> src = Arrays.asList(10, 20, 30);
        List<Integer> dest = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("\n源列表: " + src);
        System.out.println("目标列表: " + dest);
        Collections.copy(dest, src);
        System.out.println("copy后目标: " + dest);

        // replaceAll - 替换所有
        List<String> strings = new ArrayList<>(Arrays.asList("a", "b", "c", "a", "b", "a"));
        System.out.println("\n原始字符串: " + strings);
        Collections.replaceAll(strings, "a", "x");
        System.out.println("替换a为x: " + strings);
    }

    /**
     * 查询相关方法
     */
    private static void queryMethods() {
        System.out.println("【查询相关方法】");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 2, 4, 2, 5, 2);
        System.out.println("列表: " + numbers);

        // max - 最大值
        System.out.println("max最大值: " + Collections.max(numbers));

        // min - 最小值
        System.out.println("min最小值: " + Collections.min(numbers));

        // max - 使用自定义比较器
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        System.out.println("\n字符串列表: " + names);
        System.out.println("max(按长度): " + Collections.max(names, Comparator.comparing(String::length)));
        System.out.println("min(按长度): " + Collections.min(names, Comparator.comparing(String::length)));

        // frequency - 频率统计
        System.out.println("\n2出现的频率: " + Collections.frequency(numbers, 2));

        // indexOfSubList - 子列表首次出现位置
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 3, 4, 5);
        List<Integer> subList = Arrays.asList(3, 4, 5);
        System.out.println("\n主列表: " + list1);
        System.out.println("子列表: " + subList);
        System.out.println("子列表首次位置: " + Collections.indexOfSubList(list1, subList));
        System.out.println("子列表最后位置: " + Collections.lastIndexOfSubList(list1, subList));

        // disjoint - 检查两个集合是否有交集
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(4, 5, 6);
        List<Integer> c = Arrays.asList(3, 4, 5);
        System.out.println("\na和b有交集? " + Collections.disjoint(a, b));
        System.out.println("a和c有交集? " + Collections.disjoint(a, c));
    }

    /**
     * 不可变集合方法
     */
    private static void immutableMethods() {
        System.out.println("【不可变集合方法】");

        List<String> mutableList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        System.out.println("原始可变列表: " + mutableList);

        // unmodifiableList - 不可变列表
        List<String> immutableList = Collections.unmodifiableList(mutableList);
        System.out.println("不可变列表: " + immutableList);

        try {
            immutableList.add("d"); // 抛出 UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("添加元素时异常: UnsupportedOperationException");
        }

        // 修改原列表会影响视图
        mutableList.add("d");
        System.out.println("原列表添加后: " + immutableList);

        // emptyList - 空不可变列表
        List<String> empty = Collections.emptyList();
        System.out.println("\n空列表: " + empty);
        System.out.println("空列表大小: " + empty.size());

        // nCopies - 创建包含n个相同元素的列表
        List<Integer> nCopies = Collections.nCopies(5, 10);
        System.out.println("\nnCopies(5, 10): " + nCopies);

        // 其他不可变方法
        Set<String> unmodifiableSet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("x", "y", "z")));
        Map<String, Integer> unmodifiableMap = Collections.unmodifiableMap(new HashMap<>(Map.of("a", 1, "b", 2)));
        System.out.println("\n不可变Set: " + unmodifiableSet);
        System.out.println("不可变Map: " + unmodifiableMap);
    }

    /**
     * 特殊方法
     */
    private static void specialMethods() {
        System.out.println("【特殊方法】");

        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3));
        System.out.println("原始列表: " + numbers);

        // addAll - 批量添加
        Collections.addAll(numbers, 10, 20, 30);
        System.out.println("addAll后: " + numbers);

        // reverseOrder - 反转比较器
        List<String> names = new ArrayList<>(Arrays.asList("a", "b", "c"));
        System.out.println("\n原始: " + names);
        names.sort(Collections.reverseOrder());
        System.out.println("reverseOrder排序: " + names);

        // enumeration - 集合与枚举转换
        Vector<String> vector = new Vector<>(Arrays.asList("x", "y", "z"));
        Enumeration<String> enumeration = Collections.enumeration(vector);
        System.out.println("\nEnumeration遍历:");
        while (enumeration.hasMoreElements()) {
            System.out.println("  " + enumeration.nextElement());
        }

        // list - 枚举转List
        Enumeration<String> e = vector.elements();
        List<String> fromEnum = Collections.list(e);
        System.out.println("Enumeration转List: " + fromEnum);

        // checkedCollection - 类型安全集合
        List rawList = new ArrayList();
        List<String> checkedList = Collections.checkedList(rawList, String.class);

        try {
            checkedList.add(String.valueOf(123)); // 编译通过，运行时抛出ClassCastException
        } catch (ClassCastException e1) {
            System.out.println("\n添加非String类型时: ClassCastException");
        }

        checkedList.add("hello");
        System.out.println("checkedList: " + checkedList);
    }

    /**
     * 单例集合方法
     */
    private static void singletonMethods() {
        System.out.println("【单例集合方法】");

        // singletonList - 只包含一个元素的不可变列表
        List<String> singleList = Collections.singletonList("only one");
        System.out.println("singletonList: " + singleList);
        System.out.println("大小: " + singleList.size());

        // singletonSet - 只包含一个元素的不可变Set
        Set<Integer> singleSet = Collections.singleton(42);
        System.out.println("\nsingletonSet: " + singleSet);

        // singletonMap - 只包含一个键值对的不可变Map
        Map<String, Integer> singleMap = Collections.singletonMap("key", 100);
        System.out.println("\nsingletonMap: " + singleMap);

        // 常见使用场景：参数为集合但只需要一个元素时
        List<String> list = new ArrayList<>();
        list.addAll(Collections.singletonList("element"));
        System.out.println("\n使用singletonList添加元素: " + list);
    }

    /**
     * 学生类（用于演示自定义排序）
     */
    static class Student {
        private String name;
        private int age;
        private int score;

        public Student(String name, int age, int score) {
            this.name = name;
            this.age = age;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return String.format("Student{name='%s', age=%d, score=%d}", name, age, score);
        }
    }
}
