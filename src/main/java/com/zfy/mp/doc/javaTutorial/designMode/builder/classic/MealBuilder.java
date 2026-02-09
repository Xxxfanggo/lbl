package com.zfy.mp.doc.javaTutorial.designMode.builder.classic;

/**
 * 抽象建造者：套餐建造者
 */
public abstract class MealBuilder {
    protected Meal meal = new Meal();

    public abstract void buildBurger();
    public abstract void buildDrink();
    public abstract void buildSideDish();
    public abstract void buildToy();

    public Meal getResult() {
        return meal;
    }
}
