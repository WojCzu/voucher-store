package pl.wojczu.voucherstore.productcatalog;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ProductStorage extends Repository<Product, String> {
    void save(Product newProduct);

    @Query("SELECT p FROM Product p WHERE p.productId = :productId")
    Optional<Product> getById(@Param("productId") String productId);

    @Query("SELECT p FROM Product p WHERE p.price IS NOT NULL AND p.description IS NOT NULL")
    List<Product> allPublishedProducts();

    @Modifying
    @Query("Delete from Product")
    void clear();
}
