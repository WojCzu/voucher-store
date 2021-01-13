package pl.wojczu.voucherstore.sales;

import org.junit.Before;
import org.junit.Test;
import pl.wojczu.voucherstore.productcatalog.ProductCatalogConfiguration;
import pl.wojczu.voucherstore.productcatalog.ProductCatalogFacade;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class SalesTest {

    ProductCatalogFacade productCatalog;
    private InMemoryBasketStorage basketStorage;
    private CurrentCustomerContext currentConsumerContext;
    private Inventory inventory;
    String customerId;

    @Before
    public void setUp(){
        productCatalog = new ProductCatalogConfiguration().productCatalogFacade();
        basketStorage = new InMemoryBasketStorage();
        inventory = (productId -> true);
        currentConsumerContext = () -> customerId;
    }

    @Test
    public void itAllowsAddProductToBasket(){
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();
        var customerId = thereIsCustomerWhoIsDoingShopping();

        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);

        thereIsXProductsInCustomersBasket(2, customerId);
    }

    @Test
    public void itAllowsAddProductToBasketByMultipleCustomers(){
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();

        var customerId1 = thereIsCustomerWhoIsDoingShopping();
        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);

        var customerId2 = thereIsCustomerWhoIsDoingShopping();
        salesFacade.addProduct(productId2);

        thereIsXProductsInCustomersBasket(2, customerId1);
        thereIsXProductsInCustomersBasket(1, customerId2);

    }

    private void thereIsXProductsInCustomersBasket(int expectedProductsCount, String customerId) {
        Basket basket = basketStorage.loadForCustomer(customerId)
                .orElse(Basket.empty());
        assertThat(basket.getProductsQuantities()).isEqualTo(expectedProductsCount);
    }

    private String thereIsCustomerWhoIsDoingShopping() {
        customerId = UUID.randomUUID().toString();
        return new String(customerId);
    }

    private String thereIsProductAvailable() {
        var id = productCatalog.createProduct();
        productCatalog.applyPrice(id, BigDecimal.valueOf(10));
        productCatalog.updateProductDetails(id, "desc", "image");
        return id;
    }

    private SalesFacade thereIsSalesModule() {
        return new SalesFacade(
                productCatalog,
                basketStorage,
                currentConsumerContext,
                inventory
        );
    }
}
