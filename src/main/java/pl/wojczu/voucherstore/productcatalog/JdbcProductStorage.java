package pl.wojczu.voucherstore.productcatalog;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcProductStorage implements ProductStorage {
    public static final String INSERT_SQL = "INSERT INTO `products_catalog__products` " +
            "(`id`, `description`, `image`, `price`) " +
            "VALUES " +
            "(?,?,?,?)";

    public static final String SELECT_SINGLE_SQL = "SELECT * FROM `products_catalog__products` WHERE id = ?";

    public static final String SELECT_PUBLISHED = "SELECT * FROM `products_catalog__products` WHERE price IS NOT NULL AND description IS NOT NULL";

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Product newProduct) {
        jdbcTemplate.update(
                INSERT_SQL,
                newProduct.getId(), newProduct.getDescription(), newProduct.getImage(), newProduct.getPrice());
    }

    @Override
    public Optional<Product> getById(String productId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_SINGLE_SQL, new Object[] {productId}, getProductRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static RowMapper<Product> getProductRowMapper() {
        return (resultSet, i) -> {
            Product p = new Product(UUID.fromString(resultSet.getString("id")));
            p.setDescription(resultSet.getString("description"));
            p.setPrice(resultSet.getBigDecimal("price"));
            p.setImage(resultSet.getString("image"));

            return p;
        };
    }

    @Override
    public List<Product> allPublishedProducts() {
        return jdbcTemplate.query(SELECT_PUBLISHED, getProductRowMapper());
    }

    @Override
    public void clear() {
        jdbcTemplate.update("delete from `products_catalog__products`");
    }
}