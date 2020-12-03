package pl.wojczu.voucherstore.productcatalog;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {

    @Id
    private String productId;
    private String description;
    private String image;
    private BigDecimal price;

    Product(){}

    public Product(UUID productId) {
        this.productId = productId.toString();

    }

    public String getId() {
        return productId.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
