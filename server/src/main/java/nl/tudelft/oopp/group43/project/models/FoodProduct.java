package nl.tudelft.oopp.group43.project.models;



import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="food_product"
    ,catalog="CSE1105Project"
)
public class FoodProduct  implements java.io.Serializable {


     private Integer id;
     private String name;
     private String description;
     private BigDecimal price;
     private Set buildingFoodProducts = new HashSet(0);
     private Set foodOrderDetailses = new HashSet(0);

    public FoodProduct() {
    }

	
    public FoodProduct(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public FoodProduct(String name, String description, BigDecimal price, Set buildingFoodProducts, Set foodOrderDetailses) {
       this.name = name;
       this.description = description;
       this.price = price;
       this.buildingFoodProducts = buildingFoodProducts;
       this.foodOrderDetailses = foodOrderDetailses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="description", nullable=false, length=16777215)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="price", nullable=false, precision=10)
    public BigDecimal getPrice() {
        return this.price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="foodProduct")
    public Set getBuildingFoodProducts() {
        return this.buildingFoodProducts;
    }
    
    public void setBuildingFoodProducts(Set buildingFoodProducts) {
        this.buildingFoodProducts = buildingFoodProducts;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="foodProduct")
    public Set getFoodOrderDetailses() {
        return this.foodOrderDetailses;
    }
    
    public void setFoodOrderDetailses(Set foodOrderDetailses) {
        this.foodOrderDetailses = foodOrderDetailses;
    }




}


