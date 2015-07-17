package nifax.control.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

/**
 *
 * @author NB
 */
@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    protected Product() {
    }    
    
    public Product(String description, double cost, Category categoryProduct) {
        this.description = description;
        this.cost = cost;
        this.category = categoryProduct;
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "cost")
    private double cost;
    @JoinColumn(name = "category_id")
    @OneToOne
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.product", cascade=CascadeType.ALL)
    private Set<ProductMeasure> productMeasures = new HashSet<ProductMeasure>();

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public Category getCategory() {
        return category;
    }

    public Set<ProductMeasure> getMeasures() {
        return productMeasures;
    }

    public void setProductMeasures(Set<ProductMeasure> productMeasures) {
        this.productMeasures = productMeasures;
    }
}
