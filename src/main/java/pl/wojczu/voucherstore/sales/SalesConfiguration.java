package pl.wojczu.voucherstore.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wojczu.payu.http.JavaHttpPayUApiClient;
import pl.wojczu.payu.PayU;
import pl.wojczu.payu.PayUCredentials;
import pl.wojczu.voucherstore.productcatalog.ProductCatalogFacade;
import pl.wojczu.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.wojczu.voucherstore.sales.offer.OfferMaker;
import pl.wojczu.voucherstore.sales.ordering.ReservationRepository;
import pl.wojczu.voucherstore.sales.payment.PayUPaymentGateway;
import pl.wojczu.voucherstore.sales.payment.PaymentGateway;
import pl.wojczu.voucherstore.sales.product.ProductCatalogProductDetailsProvider;
import pl.wojczu.voucherstore.sales.product.ProductDetailsProvider;

import java.util.UUID;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker, Inventory inventory, PaymentGateway paymentGateway, ReservationRepository reservationRepository){
        var alwaysSameCustomer= UUID.randomUUID().toString();

        return new SalesFacade(
                productCatalogFacade,
                new InMemoryBasketStorage(),
                () -> alwaysSameCustomer,
                inventory,
                offerMaker,
                paymentGateway,
                reservationRepository
        );
    }
    @Bean
    PaymentGateway payUPaymentGateway() {
        return new PayUPaymentGateway(new PayU(
                PayUCredentials.productionOfEnv(),
                new JavaHttpPayUApiClient()
        ));
    }

    @Bean
    Inventory inventory(){
        return (productId -> true);
    }

    @Bean
    OfferMaker offerMaker(ProductDetailsProvider productDetailsProvider){
        return new OfferMaker(productDetailsProvider);
    }

    @Bean
    ProductDetailsProvider productDetailsProvider(ProductCatalogFacade productCatalogFacade){
        return new ProductCatalogProductDetailsProvider(productCatalogFacade);
    }
}
