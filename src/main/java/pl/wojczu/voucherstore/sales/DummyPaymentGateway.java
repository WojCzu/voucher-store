package pl.wojczu.voucherstore.sales;

public class DummyPaymentGateway implements PaymentGateway {
    @Override
    public ReservationPaymentDetails register(Reservation reservation) {
        return null;
    }
}
