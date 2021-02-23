package pl.wojczu.voucherstore.sales;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import pl.wojczu.voucherstore.sales.offer.Offer;
import pl.wojczu.voucherstore.sales.payment.PaymentUpdateStatusRequest;

@RestController
public class SalesController {
    private final SalesFacade sales;

    public SalesController(SalesFacade sales){
        this.sales = sales;
    }

    @GetMapping("/api/current-offer")
    public Offer currentOffer(){
        return sales.getCurrentOffer();
    }

    @PostMapping("/api/basket/add/{productId}")
    public void addToBasket(@PathVariable String productId){
        sales.addProduct(productId);
    }
    @PostMapping("/api/accept-offer")
    public void acceptOffer(){}

    @PostMapping("/api/payment/status")
    public void updatePaymentStatus(@RequestHeader("OpenPayu-Signature") String signatureHeader, @RequestBody String body) {
        PaymentUpdateStatusRequest paymentUpdateStatusRequest = PaymentUpdateStatusRequest.of(signatureHeader, body);

        sales.handlePaymentStatusChanged(paymentUpdateStatusRequest);
    }
}
