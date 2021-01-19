package pl.wojczu.voucherstore.sales;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojczu.voucherstore.sales.offer.Offer;

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
}
