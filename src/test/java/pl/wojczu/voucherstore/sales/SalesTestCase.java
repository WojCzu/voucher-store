package pl.wojczu.voucherstore.sales;

import pl.wojczu.voucherstore.productcatalog.Product;
import pl.wojczu.voucherstore.productcatalog.ProductCatalogConfiguration;
import pl.wojczu.voucherstore.productcatalog.ProductCatalogFacade;
import pl.wojczu.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.wojczu.voucherstore.sales.offer.OfferMaker;
import pl.wojczu.voucherstore.sales.product.ProductDetails;

import java.math.BigDecimal;
import java.util.UUID;

public class SalesTestCase {

    ProductCatalogFacade productCatalog;
    InMemoryBasketStorage basketStorage;
    CurrentCustomerContext currentConsumerContext;
    Inventory inventory;
    String customerId;
    OfferMaker offerMaker;

    protected OfferMaker thereIsOfferMaker(ProductCatalogFacade productCatalogFacade) {
        return new OfferMaker(productId -> {
            Product product = productCatalogFacade.getById(productId);
            return new ProductDetails(
                    productId,
                    product.getDescription(),
                    product.getPrice());
        });
    }

    protected CurrentCustomerContext thereIsCurrentCustomerContext() {
        return () -> customerId;
    }

    protected Inventory thereIsInventory() {
        return productId -> true;
    }

    protected InMemoryBasketStorage thereIsBasketStorage() {
        return new InMemoryBasketStorage();
    }

    protected ProductCatalogFacade thereIsProductCatalog() {
        return new ProductCatalogConfiguration().productCatalogFacade();
    }

    protected String thereIsCustomerWhoIsDoingShopping() {
        customerId = UUID.randomUUID().toString();
        return new String(customerId);
    }

    protected String thereIsProductAvailable() {
        var id = productCatalog.createProduct();
        productCatalog.applyPrice(id, BigDecimal.valueOf(10));
        productCatalog.updateProductDetails(id, "desc", "image");
        return id;
    }

    protected SalesFacade thereIsSalesModule() {
        return new SalesFacade(
                productCatalog,
                basketStorage,
                currentConsumerContext,
                inventory,
                offerMaker
        );
    }
}
