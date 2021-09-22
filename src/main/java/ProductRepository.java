import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    /**
     * Find product by id
     *
     * @param id - product id
     * @return null if product doesn't exist
     */
    Product findById(UUID id);

    /**
     * Delete product by id
     *
     * @param id - product id
     */
    void deleteById(UUID id);

    /**
     * Save or update product.
     * If product id == null then create new product
     * or update existing product else
     *
     * @param product - product
     * @return created or updated product
     */
    Product save(Product product);


    /**
     * Find all products by current category.
     * ProductCategory is enum.
     *
     * @param category - product category
     * @return list of products
     */
    List<Product> findAllByCategory(ProductCategory category);
}