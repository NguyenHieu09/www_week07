package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import vn.edu.iuh.fit.backend.pks.ProductPricePK;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_price")
//@IdClass(ProductPricePK.class)
public class ProductPrice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long price_id;

    @JoinColumn(name = "product_id",foreignKey = @ForeignKey(name = "pid"))
    @ManyToOne
    private Product product;
//    @Id
    @Column(name = "priceDate")
    private LocalDateTime priceDate;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "note")
    private String note;

    public ProductPrice() {
    }

    public ProductPrice(LocalDateTime priceDate, double price, String note) {
        this.priceDate = priceDate;
        this.price = price;
        this.note = note;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getpriceDate() {
        return priceDate;
    }

    public void setpriceDate(LocalDateTime priceDate) {
        this.priceDate = priceDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "product=" + product +
                ", priceDate=" + priceDate +
                ", price=" + price +
                ", note='" + note + '\'' +
                '}';
    }
}
