package pl.wojczu.voucherstore.sales;

import org.junit.Test;

public class SalesTest {
    @Test
    public void itAllowsAddProductToBasket(){
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();
        var customerId = thereIsCustomerWhoIsDoingShopping();

        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);

        thereIsXProductCountInCustomersBasket(2, customerId);
    }

    private void thereIsXProductCountInCustomersBasket(int i, String customerId) {
    }

    private String thereIsCustomerWhoIsDoingShopping() {
        return null;
    }

    private String thereIsProductAvailable() {
        return null;
    }

    private SalesFacade thereIsSalesModule() {
        return null;
    }
}
