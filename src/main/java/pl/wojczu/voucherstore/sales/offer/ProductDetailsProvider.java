package pl.wojczu.voucherstore.sales.offer;

public interface ProductDetailsProvider {
    ProductDetails getByProductId(String productId);
}
