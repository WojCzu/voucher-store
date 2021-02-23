package pl.wojczu.voucherstore.sales.payment;

import pl.wojczu.payu.exceptions.PayUException;

public class PaymentException extends IllegalStateException {
    public PaymentException(PayUException e) {
        super(e);
    }
}