package pl.wojczu.voucherstore.sales;

public interface Inventory {
    boolean isAvailable(String productId);
}
