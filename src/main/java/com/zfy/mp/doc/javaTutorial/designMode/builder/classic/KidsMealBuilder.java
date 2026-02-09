package com.zfy.mp.doc.javaTutorial.designMode.builder.classic;

/**
 * 具体建造者：儿童套餐建造者
 */
public class KidsMealBuilder extends MealBuilder {
    @Override
    public void buildBurger() {
        meal.setBurger("儿童小汉堡");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("苹果汁");
    }

    @Override
    public void buildSideDish() {
        meal.setSideDish("薯条(小份)");
    }

    @Override
    public void buildToy() {
        meal.setToy("卡通玩具");
    }
}
