package pl.wojczu.voucherstore.sales.payment;

import pl.wojczu.voucherstore.sales.ordering.Reservation;

public interface PaymentGateway {
    PaymentDetails register(Reservation reservation);
    boolean isTrusted(PaymentUpdateStatusRequest paymentUpdateStatusRequest);
}
