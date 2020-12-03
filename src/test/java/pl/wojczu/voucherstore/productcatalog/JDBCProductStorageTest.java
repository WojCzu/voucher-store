package pl.wojczu.voucherstore.productcatalog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JDBCProductStorageTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate.execute("DROP TABLE products_catalog__products IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE `products_catalog__products` (" +
                "`id` varchar(100) NOT NULL," +
                "`description` varchar(255)," +
                "`image` varchar(150)," +
                "`price` DECIMAL(12,2)," +
                "PRIMARY KEY (id)" +
                ");");
    }

    @Test
    public void itAllowsToStoreProduct() {
        var product = ProductFixtures.draftProduct();
        ProductStorage jdbcStorage = thereIsJDBCProductStorage();

        jdbcStorage.save(product);

        assertThat(jdbcStorage.getById(product.getId()))
                .isNotEmpty()
                .map(product1 -> product.getId())
                .contains(product.getId());
    }

    @Test
    public void thereIsEmptyWhenNoProduct() {
        ProductStorage jdbcStorage = thereIsJDBCProductStorage();
        assertThat(jdbcStorage.getById("not_exists"))
                .isEmpty();
    }

    @Test
    public void itAllowsToLoadAllProducts() {
        var product1 = ProductFixtures.readyToSellProduct("product1", 21.37);
        var product2 = ProductFixtures.readyToSellProduct("product2", 41.90);
        var product3 = ProductFixtures.draftProduct();

        ProductStorage jdbcStorage = thereIsJDBCProductStorage();

        jdbcStorage.save(product1);
        jdbcStorage.save(product2);

        List<Product> products = jdbcStorage.allPublishedProducts();

        assertThat(products)
                .hasSize(2)
                .extracting(Product::getId)
                .contains(product1.getId())
                .doesNotContain(product3.getId());
    }

    private ProductStorage thereIsJDBCProductStorage() {
        return new JdbcProductStorage(jdbcTemplate);
    }
}