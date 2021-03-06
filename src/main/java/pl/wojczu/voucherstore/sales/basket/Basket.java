package pl.wojczu.voucherstore.sales.basket;

import pl.wojczu.voucherstore.productcatalog.Product;
import pl.wojczu.voucherstore.sales.Inventory;
import pl.wojczu.voucherstore.sales.exceptions.NotEnoughProductsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Basket {
    private final Map<String, Product> products;
    private final Map<String, Integer> productsQuantities;

    Basket(){
        this.products = new HashMap<>();
        this.productsQuantities = new HashMap<>();
    }

    public static Basket empty() {
        return new Basket();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void add(Product product, Inventory inventory) {
        if (!inventory.isAvailable(product.getId())){
            throw new NotEnoughProductsException();
        }
        if(!isContains(product)){
            putIntoBasket(product);
        }else {
            increaseQuantity(product);
        }
    }

    private void putIntoBasket(Product product) {
        products.put(product.getId(), product);
        productsQuantities.put(product.getId(), 1);
    }

    private void increaseQuantity(Product product) {
        productsQuantities.put(product.getId(), productsQuantities.get(product.getId()) + 1 );
    }

    private boolean isContains(Product product) {
        return productsQuantities.containsKey(product.getId());
    }

    public int getProductsQuantities() {
        return products.size();
    }


    public List<BasketLine> getBasketItems() {
        return productsQuantities
                .entrySet()
                .stream()
                .map(es -> new BasketLine(es.getKey(), es.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }
}
