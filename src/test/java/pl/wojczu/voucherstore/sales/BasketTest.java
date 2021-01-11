package pl.wojczu.voucherstore.sales;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import pl.wojczu.voucherstore.productcatalog.Product;

import java.util.UUID;

public class BasketTest {

    public static final String PRODUCT_1 = "voucher-321";
    public static final String PRODUCT_2 = "voucher-123";

    @Test
    public void newlyCreateBasketIsEmpty(){
        Basket basket = Basket.empty();

        assertThat(basket.isEmpty())
                .isTrue();
    }

    @Test
    public void basketWithProductsIsNotEmpty(){
        Basket basket = Basket.empty();
        Product product = thereIsProduct(PRODUCT_1);

        basket.add(product, thereIsAlwaysAvailableInventory());

        assertThat(basket.isEmpty())
                .isFalse();
    }

    @Test
    public void itShowsProductsCount(){
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct(PRODUCT_1);
        Product product2 = thereIsProduct(PRODUCT_2);

        basket.add(product1, thereIsAlwaysAvailableInventory());
        basket.add(product2, thereIsAlwaysAvailableInventory());

        assertThat(basket.isEmpty())
                .isFalse();

        assertThat(basket.getProductsQuantities())
                .isEqualTo(2);
    }

    @Test
    public void itCountsProductOnceWhenAddedTwice(){
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct(PRODUCT_1);

        basket.add(product1, thereIsAlwaysAvailableInventory());
        basket.add(product1, thereIsAlwaysAvailableInventory());

        assertThat(basket.getProductsQuantities())
                .isEqualTo(1);

        basketContainsProductWithQuantity(basket, product1, 2);
    }

    @Test
    public void itStoreQuantityForMultipleProducts(){
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct(PRODUCT_1);
        Product product2 = thereIsProduct(PRODUCT_2);

        basket.add(product1, thereIsAlwaysAvailableInventory());
        basket.add(product1, thereIsAlwaysAvailableInventory());
        basket.add(product2, thereIsAlwaysAvailableInventory());

        basketContainsProductWithQuantity(basket, product1, 2);
        basketContainsProductWithQuantity(basket, product2, 1);

    }

    @Test
    public void itDennyToAddProductWithNoInventory(){
        Basket basket = Basket.empty();
        Product product1 = thereIsProduct(PRODUCT_1);


        assertThatThrownBy(() ->basket.add(product1, (productId) -> false))
        .hasMessage("There are not enough products available");
    }

    private Inventory thereIsAlwaysAvailableInventory() {
        return (productId -> true);
    }

    private void thereIsFollowingAmountOfProductAvailable(String productId, int quantity) {
    }

    private void basketContainsProductWithQuantity(Basket basket, Product product1, int expectedQuantity) {
        assertThat(basket.getBasketItems())
                .filteredOn(basketLine -> basketLine.getProductId().equals(product1.getId()))
                .extracting(BasketLine::getQuantity)
                .first()
                .isEqualTo(expectedQuantity);
    }

    private Product thereIsProduct(String description) {
        Product product = new Product(UUID.randomUUID());
        product.setDescription(description);
        return product;
    }

}
