package com.demo.ecommerce;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Dish {

    private String description;
    private boolean delivered = false;

    public static Dish deliver(Dish dish) {
        Dish deliveredDish = new Dish(dish.description);
        deliveredDish.delivered = true;

        return deliveredDish;
    }

    public Dish(String description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
