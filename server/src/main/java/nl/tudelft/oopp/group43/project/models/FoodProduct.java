package nl.tudelft.oopp.group43.project.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DynamicUpdate
@Table(name = "food_product")
public class FoodProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;


    public FoodProduct() {
    }


    public FoodProduct(@JsonProperty("id") int id,
                       @JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("price") double price) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;


    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodProduct)) return false;
        FoodProduct that = (FoodProduct) o;
        return getId() == that.getId() &&
                Double.compare(that.getPrice(), getPrice()) == 0 &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription());
    }

}
