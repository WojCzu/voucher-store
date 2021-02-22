package pl.wojczu.voucherstore.sales;

import pl.wojczu.payu.PayU;

public class PayUPaymentGateway implements PaymentGateway {
    private final PayU payU;
    public PayUPaymentGateway(PayU payU) {

        this.payU = payU;
    }

    @Override
    public ReservationPaymentDetails register(Reservation reservation) {
        return null;
    }
}
