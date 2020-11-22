package pl.wojczu.voucherstore.productcatalog;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ProductCatalogTest {

    public static final String MY_DESCRIPTION = "My product description";
    public static final String MY_IMAGE = "http://myProductImage.jpg";

    @Test
    public void itAllowsToCreateProduct(){
        //arrange
        ProductCatalogFacade api = thereIsProductCatalog();
        //act
        String productId = api.createProduct();
        //assert
        Assert.assertTrue(api.isExists(productId));
    }

    @Test
    public void itAllowsToLoadProduct(){
        ProductCatalogFacade api = thereIsProductCatalog();

        String productId = api.createProduct();
        Product loaded = api.getById(productId);

        Assert.assertEquals(productId, loaded.getId());
    }

    @Test
    public void itAllowsToSetProductDetails(){
        ProductCatalogFacade api = thereIsProductCatalog();
        String productId = api.createProduct();

        api.updateProductDetails(productId, MY_DESCRIPTION, MY_IMAGE);
        Product loaded = api.getById(productId);

        Assert.assertEquals(MY_DESCRIPTION, loaded.getDescription());
        Assert.assertEquals(MY_IMAGE, loaded.getImage());
    }

    @Test
    public void itAllowsToApplyPrice(){
        ProductCatalogFacade api = thereIsProductCatalog();
        String productId = api.createProduct();

        api.applyPrice(productId, BigDecimal.TEN);
        Product loaded = api.getById(productId);

        Assert.assertEquals(BigDecimal.TEN, loaded.getPrice());
    }

    @Test
    public void itAllowsToListAllPublishedProducts(){
        ProductCatalogFacade api = thereIsProductCatalog();
        String draftProductId = api.createProduct();
        String productId = api.createProduct();
        api.updateProductDetails(productId, MY_DESCRIPTION, MY_IMAGE);
        api.applyPrice(productId, BigDecimal.TEN);

        List<Product> products = api.allPublishedProducts();

        assertThat(products)
                .hasSize(1)
                .extracting(Product::getId)
                .contains(productId)
                .doesNotContain(draftProductId);
    }

    @Test
    public void itDenyActionOnNotExistedProduct(){
        ProductCatalogFacade api = thereIsProductCatalog();

        assertThatThrownBy(() -> api.getById("notExists"))
                .hasMessage("There is no product with id: notExists");
        assertThatThrownBy(() -> api.updateProductDetails("notExists", "desc", "img"))
                .hasMessage("There is no product with id: notExists");
        assertThatThrownBy(() -> api.applyPrice("notExists", BigDecimal.TEN))
                .hasMessage("There is no product with id: notExists");
    }

    private static ProductCatalogFacade thereIsProductCatalog() {
        return new ProductCatalogConfiguration().productCatalogFacade();
    }
}