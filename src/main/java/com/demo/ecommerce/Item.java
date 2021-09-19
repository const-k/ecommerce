package com.demo.ecommerce;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode
@Getter @Setter
@ToString
public class Item {
    private @Id String id;
    private String name;
    private double price;

    private Item() {
    }

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
