package pl.wojczu.voucherstore.productcatalog;

public class ProductNotFoundException extends IllegalStateException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
