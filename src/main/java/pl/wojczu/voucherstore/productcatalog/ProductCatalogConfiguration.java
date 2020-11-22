package pl.wojczu.voucherstore.productcatalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ProductCatalogConfiguration {

    public ProductCatalogFacade productCatalogFacade(){
        return new ProductCatalogFacade(new HashMapProductStorage());
    }

    @Bean
    public ProductStorage productionProductStorage(){
        return new HashMapProductStorage();
    }

    @Bean
    public ProductCatalogFacade fixturesAwareProductCatalogFacade(ProductStorage productStorage){
        ProductCatalogFacade productCatalogFacade = new ProductCatalogFacade(productStorage);

        String product1 = productCatalogFacade.createProduct();
        productCatalogFacade.applyPrice(product1, BigDecimal.valueOf(21.37));
        productCatalogFacade.updateProductDetails(product1, "This is my first product", "http://image.png");

        String product2 = productCatalogFacade.createProduct();
        productCatalogFacade.applyPrice(product2, BigDecimal.valueOf(80.20));
        productCatalogFacade.updateProductDetails(product2, "This is my second product", "http://super-image.png");

        String product3 = productCatalogFacade.createProduct();
        productCatalogFacade.applyPrice(product3, BigDecimal.valueOf(120.20));
        productCatalogFacade.updateProductDetails(product3, "This is my third product", "http://nice-image.png");

        return productCatalogFacade;
    }
}
