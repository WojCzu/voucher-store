package pl.wojczu.voucherstore.sales;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import pl.wojczu.voucherstore.sales.offer.Offer;
import pl.wojczu.voucherstore.sales.ordering.ClientData;
import pl.wojczu.voucherstore.sales.ordering.Reservation;
import pl.wojczu.voucherstore.sales.payment.PaymentDetails;

public class OrderingTest extends SalesTestCase {

    @Before
    public void setUp(){
        productCatalog = thereIsProductCatalog();
        basketStorage = thereIsBasketStorage();
        inventory = thereIsInventory();
        currentConsumerContext = thereIsCurrentCustomerContext();
        offerMaker = thereIsOfferMaker(productCatalog);
        paymentGateway = thereIsPaymentGateway();
        reservationRepository = thereIsInMemoryReservationsRepository();

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

        var clientData = clientProvideHisData();
        PaymentDetails paymentDetails = salesFacade.acceptOffer(seenOffer, clientData);

        assertThat(paymentDetails.getPaymentUrl()).isNotNull();
        thereIsPendingReservationWithId(paymentDetails.getReservationId());
        thereIsPaymentRegisteredForReservation(paymentDetails.getReservationId());
        thereIsReservationForCustomer(paymentDetails.getReservationId(), clientData.getFirstname());
    }

    private void thereIsReservationForCustomer(String reservationId, String firstname) {
        Reservation reservation = reservationRepository.loadById(reservationId).get();

        assertThat(reservation.getCustomerFirstname()).isEqualTo(firstname);
    }
    private void thereIsPaymentRegisteredForReservation(String reservationId) {
        Reservation reservation = reservationRepository.loadById(reservationId).get();
        assertThat(reservation.getPaymentId()).isNotNull();
    }
    private ClientData clientProvideHisData() {
        return new ClientData();
    }

    private void thereIsPendingReservationWithId(String reservationId) {
        Reservation reservation = reservationRepository.loadById(reservationId).get();
        assertThat(reservation.isPending()).isTrue();
    }
}
