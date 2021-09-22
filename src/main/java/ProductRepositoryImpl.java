import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Представляет собой CRUD репозиторий к таблице объекта {@link Product}.
 * Реализует интерфейс {@link ProductRepository}.
 */
public class ProductRepositoryImpl implements ProductRepository {

    Statement statement;

    /**
     * Конструктор с параметром в виде объекта типа {@link Connection}.
     */
    ProductRepositoryImpl(Connection connection) throws SQLException {
        this.statement = connection.createStatement();
    }

    public void createTable() {
        String query = "create extension if not exists " +
                "drop type if exists product_category;" +
                "create table if not exists product (" +
                " id uuid," +
                " name varchar(32)," +
                " description varchar(32)," +
                " category varchar(32)," +
                " manufacture_date_time timestamp," +
                " manufacturer varchar(32)," +
                " has_expiry_time boolean," +
                " stock integer);";

        try {
            int updatedRowsCount = statement.executeUpdate(query);
            System.out.println(".createTable completed updatedRowsCount = " + updatedRowsCount);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table");
        }
    }

    public void dropTable() throws SQLException {
        String query = "drop table product";
        int updatedRowCount = statement.executeUpdate(query);
        System.out.println(".dropTable completed updatedRowsCount = " + updatedRowCount);
    }


    @Override
    public Product findById(UUID id) {
        String query = "select * from product where id::text = '" + id.toString() + "';";

        try {
            boolean execution = statement.execute(query);
            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                return null;
            }

            String product_id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String productCategory = resultSet.getString("category");
            String manufactureDateTime = resultSet.getString("manufacture_date_time");
            String manufacturer = resultSet.getString("manufacturer");
            String hasExpiryTime = resultSet.getString("has_expiry_time");
            int stock = resultSet.getInt("stock");

            Product product = new Product(
                    java.util.UUID.fromString(product_id),
                    name,
                    description,
                    ProductCategory.valueOf(productCategory),
                    LocalDateTime.parse(manufactureDateTime.replace(" ", "T")),
                    manufacturer,
                    Boolean.getBoolean(hasExpiryTime),
                    stock);

            return product;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to select user", e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        try {
            int updatedRowCount = statement.executeUpdate("delete from product where id::text ='" + id.toString() + "'");
            System.out.println(".delete completed updateRowCount = " + updatedRowCount);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to delete", ex);
        }
    }

    @Override
    public Product save(Product product) {
        if (product.id != null) {
            String query = String.format("insert into product (id, name, description, category, " +
                            "manufacture_date_time, manufacturer, has_expiry_time, stock) " +
                            " values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%d')",
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getManufactureDateTime(),
                    product.getManufacturer(),
                    product.isHasExpiryTime(),
                    product.getStock());

            try {
                boolean execution = statement.execute(query);
                int updatedRowCount = statement.getUpdateCount();
                System.out.println(".update completed updateRowCount = " + updatedRowCount);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to update table", e);
            }

        } else {
            String query = String.format("update product set id = '%s', name = '%s', " +
                            "description = '%s', category = '%s', manufacture_date_time = '%s'," +
                            " manufacturer = '%s', has_expiry_time = '%s', stock = '%d';",
                    UUID.randomUUID(),
                    product.getName(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getManufactureDateTime(),
                    product.getManufacturer(),
                    product.isHasExpiryTime(),
                    product.getStock()
            );

            try {
                boolean execution = statement.execute(query);
                int updatedRowCount = statement.getUpdateCount();
                System.out.println(".update completed updateRowCount = " + updatedRowCount);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to update table", e);
            }
        }

        return product;
    }

    @Override
    public List<Product> findAllByCategory(ProductCategory category) {
        List<Product> products = new ArrayList<>();

        String query = "select * from product where category =" + category.toString();

        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String productCategory = resultSet.getString("category");
                String manufactureDateTime = resultSet.getString("manufacture_date_time");
                String manufacturer = resultSet.getString("manufacturer");
                String hasExpiryTime = resultSet.getString("has_expiry_time");
                int stock = resultSet.getInt("stock");
                products.add(new Product(
                        java.util.UUID.fromString(id),
                        name,
                        description,
                        ProductCategory.valueOf(productCategory),
                        LocalDateTime.parse(manufactureDateTime.replace(" ", "T")),
                        manufacturer,
                        Boolean.getBoolean(hasExpiryTime),
                        stock));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all by category", e);
        }

        return products;
    }
}
