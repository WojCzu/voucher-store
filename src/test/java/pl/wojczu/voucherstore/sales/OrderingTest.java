package pl.wojczu.voucherstore.sales;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import pl.wojczu.voucherstore.sales.offer.Offer;

public class OrderingTest extends SalesTestCase {

    @Before
    public void setUp(){
        productCatalog = thereIsProductCatalog();
        basketStorage = thereIsBasketStorage();
        inventory = thereIsInventory();
        currentConsumerContext = thereIsCurrentCustomerContext();
        offerMaker = thereIsOfferMaker(productCatalog);
    }

    @Test
    public void itCreatesReservationBasedOnCurrentOffer(){
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();

        var customerId = thereIsCustomerWhoIsDoingShopping();
        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);
        Offer seenOffer = salesFacade.getCurrentOffer();

        String reservationId = salesFacade.acceptOffer(seenOffer);

        thereIsPendingReservationWithId(reservationId);
    }

    private void thereIsPendingReservationWithId(String reservationId) {

        assertThat(true).isFalse();
    }
}
