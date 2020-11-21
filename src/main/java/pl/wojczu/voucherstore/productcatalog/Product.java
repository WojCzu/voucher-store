package pl.wojczu.voucherstore.productcatalog;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final UUID productId;

    private String description;
    private String image;
    private BigDecimal price;

    public Product(UUID productId) {
        this.productId = productId;

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
