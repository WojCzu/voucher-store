package pl.wojczu.voucherstore.sales.offer;

import org.junit.Test;
import pl.wojczu.voucherstore.sales.basket.BasketLine;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferTest {
    @Test
    public void itCalculateOfferBasedOnBasketItems(){
        List<BasketLine> basketItems = Arrays.asList(
                new BasketLine("product1", 2),
                new BasketLine("product2", 1)
        );


        OfferMaker offerMaker = thereIsOfferMaker();
        Offer offer = offerMaker.calculateOffer(basketItems);

        assertThat(offer.getTotal())
                .isEqualTo(BigDecimal.valueOf(30));
    }

    @Test
    public void itCalculateOfferBasedOnSingleBasketItem(){
        List<BasketLine> basketItems = Collections.singletonList(
                new BasketLine("product1", 1)
        );


        OfferMaker offerMaker = thereIsOfferMaker();
        Offer offer = offerMaker.calculateOffer(basketItems);

        assertThat(offer.getTotal())
                .isEqualTo(BigDecimal.valueOf(10));
    }

    private OfferMaker thereIsOfferMaker() {
        return new OfferMaker();
    }
}