package nifax.control.model;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

/**
 *
 * @author NB
 */
@Entity
@Table(name = "STOCK")
public class Stock implements Serializable {
    
    protected Stock() {
    }

    public Stock(String description, double quantity,double quantityCommitted, Measure measure,
            Product product, Store store) {
        this.description = description;
        this.quantity = quantity;
        this.quantityCommitted = quantityCommitted;
        this.measure = measure;
        this.product = product;
        this.store = store;
    }

    public Stock(Product product, Store store) {
        this.product = product;
        this.store = store;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private double quantity;
    @Column(name = "quantitycommitted")
    private double quantityCommitted;
    @JoinColumn(name = "measure_id")
    @OneToOne
    private Measure measure;
    @JoinColumn(name = "product_id")
    @OneToOne
    private Product product;
    @JoinColumn(name = "store_id")
    @OneToOne
    private Store store;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getQuantityCommitted() {
        return quantityCommitted;
    }
    
    public Measure getMeure() {
        return measure;
    }

    public Product getProduct() {
        return product;
    }

    public Measure getMeasure() {
        return measure;
    }

    public Store getStore() {
        return store;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setQuantityCommitted(double quantityCommitted) {
        this.quantityCommitted = quantityCommitted;
    }
    
}
