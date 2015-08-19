package nifax.control.model;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

/**
 *
 * @author NB
 */
@Entity
@Table(name = "OFFER")
public class Offer implements Serializable {

    protected Offer() {
    }

    public Offer(String description, double discount, double quantity,
        Measure measure) {
        this.description = description;
        this.discount = discount;
        this.quantity = quantity;
        this.measure = measure;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "offer_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "discount")
    private double discount;
    @Column(name = "quantity")
    private double quantity;
    @JoinColumn(name = "measure_id")
    @OneToOne
    private Measure measure;

    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false, insertable = false)
    private Product product;

    
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscount() {
        return discount;
    }

    public double getQuantity() {
        return quantity;
    }

    public Measure getMeasure() {
        return measure;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    

}
