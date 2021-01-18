package pl.wojczu.voucherstore.sales;

import pl.wojczu.voucherstore.productcatalog.Product;
import pl.wojczu.voucherstore.productcatalog.ProductCatalogFacade;
import pl.wojczu.voucherstore.sales.basket.Basket;
import pl.wojczu.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.wojczu.voucherstore.sales.offer.Offer;
import pl.wojczu.voucherstore.sales.offer.OfferMaker;

public class SalesFacade {
    ProductCatalogFacade productCatalogFacade;
    InMemoryBasketStorage basketStorage;
    CurrentCustomerContext currentCustomerContext;
    Inventory inventory;
    OfferMaker offerMaker;

    public SalesFacade(ProductCatalogFacade productCatalogFacade, InMemoryBasketStorage basketStorage, CurrentCustomerContext currentCustomerContext, Inventory inventory, OfferMaker offerMaker) {
        this.productCatalogFacade = productCatalogFacade;
        this.basketStorage = basketStorage;
        this.currentCustomerContext = currentCustomerContext;
        this.inventory = inventory;
        this.offerMaker = offerMaker;
    }

    public void addProduct(String productId) {
        Product product = productCatalogFacade.getById(productId);
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId())
                .orElse(Basket.empty());

        basket.add(product, inventory);
        basketStorage.addForCustomer(getCurrentCustomerId(), basket);
    }

    private String getCurrentCustomerId(){
        return currentCustomerContext.getCurrentCustomerId();
    }

    public Offer getCurrentOffer() {
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId())
                .orElse(Basket.empty());
        return offerMaker.calculateOffer(basket.getBasketItems());
    }

    public String acceptOffer(Offer seenOffer) {
        return null;
    }
}
