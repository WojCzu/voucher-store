package pl.wojczu.voucherstore.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wojczu.voucherstore.productcatalog.ProductCatalogFacade;
import pl.wojczu.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.wojczu.voucherstore.sales.offer.OfferMaker;
import pl.wojczu.voucherstore.sales.product.ProductCatalogProductDetailsProvider;
import pl.wojczu.voucherstore.sales.product.ProductDetailsProvider;

import java.util.UUID;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker, Inventory inventory){
        var alwaysSameCustomer= UUID.randomUUID().toString();

        return new SalesFacade(
                productCatalogFacade,
                new InMemoryBasketStorage(),
                () -> alwaysSameCustomer,
                inventory,
                offerMaker

        );
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
