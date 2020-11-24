package pl.wojczu.voucherstore.productcatalog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static  org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcPlaygroundTest {

    public static final String PRODUCT_ID = "e1d02462-6f3a-461e-bec5-ac08a98bde8b";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
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
    public void itCountProducts(){
        int result = jdbcTemplate.queryForObject("SELECT count(*) FROM `products_catalog__products`", Integer.class);

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void itAddProduct(){
        var query = "INSERT INTO `products_catalog__products` (`id`, `description`, `image`, `price`) VALUES " +
                "('product1', 'product1 description', 'product1 image', 20.77)," +
                "('product2', 'product2 description', 'product2 image', 20.20)" +
                ";";
        jdbcTemplate.execute(query);

        int result = jdbcTemplate.queryForObject("SELECT count(*) FROM `products_catalog__products`", Integer.class);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void itLoadProduct(){
        var query = "INSERT INTO `products_catalog__products` (`id`, `description`, `image`, `price`) VALUES " +
                "('fc1ca4ef-f556-4c7f-bde1-65cad010b1ae', 'product1 description', 'product1 image', 20.77)," +
                "('e1d02462-6f3a-461e-bec5-ac08a98bde8b', 'product2 description', 'product2 image', 20.20)" +
                ";";
        jdbcTemplate.execute(query);
        var selectQuery = "SELECT * FROM `products_catalog__products` WHERE id = ?";
        Product product = jdbcTemplate.queryForObject(
                        selectQuery,
                        new Object[] {PRODUCT_ID},
                        (resultSet, i) -> new Product(UUID.fromString(resultSet.getString("id"))));

        assertThat(product.getId()).isEqualTo(PRODUCT_ID);
    }

    @Test
    public void itLoadProductViaRowMapper() {
        var query = "INSERT INTO `products_catalog__products` (`id`, `description`, `image`, `price`) VALUES " +
                "('fc1ca4ef-f556-4c7f-bde1-65cad010b1ae', 'product1 description', 'product1 image', 20.77)," +
                "('e1d02462-6f3a-461e-bec5-ac08a98bde8b', 'product2 description', 'product2 image', 20.20)" +
                ";";

        jdbcTemplate.execute(query);
        var selectQuery = "Select * from `products_catalog__products` WHERE id = ?";
        Product product = jdbcTemplate.queryForObject(selectQuery, new Object[] {PRODUCT_ID}, new ProductRowMapper());

        assertThat(product.getId()).isEqualTo(PRODUCT_ID);
    }

    @Test
    public void itAllowsToAddProduct() {
        Product newProduct = new Product(UUID.randomUUID());
        newProduct.setDescription("abc");

        jdbcTemplate.update(
                "INSERT INTO `products_catalog__products` (`id`, `description`, `image`, `price`) VALUES " +
                        "(?,?,?,?)",
                newProduct.getId(), newProduct.getDescription(), newProduct.getImage(), newProduct.getPrice());

        List<Product> products = jdbcTemplate.query("SELECT * FROM `products_catalog__products`", new ProductRowMapper());

        assertThat(products)
                .hasSize(1)
                .extracting(Product::getId)
                .contains(newProduct.getId());

    }

    static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            Product product = new Product(UUID.fromString(resultSet.getString("id")));
            product.setDescription(resultSet.getString("description"));
            return product;
        }
    }
}
