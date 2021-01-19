package pl.wojczu.voucherstore.sales.product;

import pl.wojczu.voucherstore.productcatalog.ProductCatalogFacade;

public class ProductCatalogProductDetailsProvider implements ProductDetailsProvider {
    private final ProductCatalogFacade productCatalogFacade;

    public ProductCatalogProductDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        this.productCatalogFacade = productCatalogFacade;
    }

    @Override
    public ProductDetails getByProductId(String productId) {
        var product = productCatalogFacade.getById(productId);

        return new ProductDetails(
                product.getId(),
                product.getDescription(),
                product.getPrice());
    }
}
