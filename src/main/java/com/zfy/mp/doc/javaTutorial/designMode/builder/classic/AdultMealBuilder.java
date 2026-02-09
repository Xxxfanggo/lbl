package com.zfy.mp.doc.javaTutorial.designMode.builder.classic;

/**
 * 具体建造者：成人套餐建造者
 */
public class AdultMealBuilder extends MealBuilder {
    @Override
    public void buildBurger() {
        meal.setBurger("牛肉汉堡");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("可乐(大杯)");
    }

    @Override
    public void buildSideDish() {
        meal.setSideDish("薯条(大份)");
    }

    @Override
    public void buildToy() {
        meal.setToy("无");
    }
}
