package com.zfy.mp.doc.javaTutorial.designMode.builder.classic;

/**
 * 产品：套餐
 */
public class Meal {
    private String burger;    // 汉堡
    private String drink;     // 饮料
    private String sideDish;  // 配菜
    private String toy;       // 玩具

    public void setBurger(String burger) { this.burger = burger; }
    public void setDrink(String drink) { this.drink = drink; }
    public void setSideDish(String sideDish) { this.sideDish = sideDish; }
    public void setToy(String toy) { this.toy = toy; }

    public void show() {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                       餐厅套餐                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
        System.out.println("  🍔 汉堡:   " + burger);
        System.out.println("  🥤 饮料:   " + drink);
        System.out.println("  🍟 配菜:   " + sideDish);
        System.out.println("  🧸 玩具:   " + toy);
        System.out.println("═══════════════════════════════════════════════════════════");
    }
}
