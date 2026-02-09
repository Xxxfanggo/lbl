package com.zfy.mp.doc.javaTutorial.designMode.builder.classic;

/**
 * 指挥者：套餐组装员
 */
public class MealDirector {
    private MealBuilder mealBuilder;

    public MealDirector(MealBuilder mealBuilder) {
        this.mealBuilder = mealBuilder;
    }

    public void setMealBuilder(MealBuilder mealBuilder) {
        this.mealBuilder = mealBuilder;
    }

    /**
     * 构建标准套餐
     */
    public Meal constructStandardMeal() {
        mealBuilder.buildBurger();
        mealBuilder.buildDrink();
        mealBuilder.buildSideDish();
        mealBuilder.buildToy();
        return mealBuilder.getResult();
    }

    /**
     * 构建简化套餐（没有玩具）
     */
    public Meal constructSimpleMeal() {
        mealBuilder.buildBurger();
        mealBuilder.buildDrink();
        mealBuilder.buildSideDish();
        return mealBuilder.getResult();
    }
}
