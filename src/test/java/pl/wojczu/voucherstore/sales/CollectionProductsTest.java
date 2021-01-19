package pl.wojczu.voucherstore.sales;

import org.junit.Before;
import org.junit.Test;
import pl.wojczu.voucherstore.sales.basket.Basket;
import pl.wojczu.voucherstore.sales.offer.Offer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionProductsTest extends SalesTestCase {

    @Before
    public void setUp(){
        productCatalog = thereIsProductCatalog();
        basketStorage = thereIsBasketStorage();
        inventory = thereIsInventory();
        currentConsumerContext = thereIsCurrentCustomerContext();
        offerMaker = thereIsOfferMaker(productCatalog);
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

    @Test
    public void itGenerateOfferBasedOnSingleProduct(){
        SalesFacade salesFacade = thereIsSalesModule();
        var productId = thereIsProductAvailable();


        var customerId = thereIsCustomerWhoIsDoingShopping();
        salesFacade.addProduct(productId);
        Offer offer = salesFacade.getCurrentOffer();

        assertThat(offer.getTotal())
                .isEqualTo(BigDecimal.valueOf(10));
    }

    @Test
    public void itGenerateOfferBasedOnCollectedItems(){
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();

        var customerId1 = thereIsCustomerWhoIsDoingShopping();
        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);
        Offer offer = salesFacade.getCurrentOffer();

        assertThat(offer.getTotal())
                .isEqualTo(BigDecimal.valueOf(30));
    }

    private void thereIsXProductsInCustomersBasket(int expectedProductsCount, String customerId) {
        Basket basket = basketStorage.loadForCustomer(customerId)
                .orElse(Basket.empty());
        assertThat(basket.getProductsQuantities()).isEqualTo(expectedProductsCount);
    }

}
