package pl.wojczu.voucherstore.sales;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationPaymentDetails {
    String reservationId;
    String paymentUrl;
}
