package com.zfy.mp.doc.javaTutorial.designMode.builder.classic;

/**
 * 经典建造者模式演示
 */
public class ClassicBuilderDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🔨 经典建造者模式 - 演示                      ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");

        // 创建指挥者
        MealDirector director = new MealDirector(null);

        // 构建儿童套餐
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              1. 儿童套餐");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        MealBuilder kidsBuilder = new KidsMealBuilder();
        director.setMealBuilder(kidsBuilder);
        Meal kidsMeal = director.constructStandardMeal();
        kidsMeal.show();

        // 构建成人套餐
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              2. 成人套餐");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        MealBuilder adultBuilder = new AdultMealBuilder();
        director.setMealBuilder(adultBuilder);
        Meal adultMeal = director.constructStandardMeal();
        adultMeal.show();

        // 构建简化套餐
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              3. 简化套餐");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Meal simpleMeal = director.constructSimpleMeal();
        simpleMeal.show();
    }
}
