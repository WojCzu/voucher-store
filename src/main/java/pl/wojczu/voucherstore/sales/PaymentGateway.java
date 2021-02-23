package pl.wojczu.voucherstore.sales;

public interface PaymentGateway {
    ReservationPaymentDetails register(Reservation reservation);
    boolean isTrusted(PaymentUpdateStatusRequest paymentUpdateStatusRequest);
}
