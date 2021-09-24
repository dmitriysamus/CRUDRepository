import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;


class ProductRepositoryImplTest {

    private ProductRepositoryImpl repository;
    private Connection connection;

    private String url;
    private String login;
    private String password;

    private Product test1;
    private Product test2;
    private Product test3;

    private final PrintStream printStream = System.out;

    /**
     * Подготовка данных для тестов.
     */
    @BeforeEach
    void init() {
        url = "jdbc:postgresql://localhost:5432/testdb";
        login = "postgres";
        password = "12345678";

        try {
            connection = DriverManager.getConnection(url, login, password);
            repository = new ProductRepositoryImpl(connection);

        } catch (SQLException e) {
            throw new RuntimeException("Cannot create connection.", e);
        }

        test1 = new Product(
                UUID.randomUUID(),
                "test1",
                "text1",
                ProductCategory.FOOD,
                LocalDateTime.of(2021, 4, 4, 4, 4),
                "1",
                true,
                111);

        test2 = new Product(
                UUID.randomUUID(),
                "test2",
                "text2",
                ProductCategory.CHEMICAL,
                LocalDateTime.of(2021, 2, 2, 2, 2),
                "2",
                true,
                63);

        repository.createTable();
        repository.save(test1);
        repository.save(test2);
    }

    /**
     * Очистка объектов после тестов.
     */
    @AfterEach
    void clean() {
        url = null;
        login = null;
        password = null;
        connection = null;
        try {
            repository.dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        repository = null;
        test1 = null;
        test2 = null;
        test3 = null;
        System.setOut(printStream);
    }

    /**
     * Проверяется подгрузка контроллеров из контекста.
     */
    @Test
    void loadRepository_test() {
        Assertions.assertNotNull(repository);
    }

    /**
     * Проверяется работа метода {@link ProductRepositoryImpl#findById(UUID)},
     * при подаче на вход существующего id.
     */
    @Test
    void findById_exist_test() {
        UUID uuid = test1.getId();
        Assertions.assertEquals(uuid, repository.findById(uuid).getId());
    }

    /**
     * Проверяется работа метода {@link ProductRepositoryImpl#findById(UUID)},
     * при подаче на вход несуществующего id.
     */
    @Test
    void findById_does_not_exist_test() {
        UUID uuid = UUID.randomUUID();
        Assertions.assertEquals(null, repository.findById(uuid));
    }

    /**
     * Проверяется работа метода {@link ProductRepositoryImpl#save(Product)},
     * при подаче несуществующего id.
     */
    @Test
    void save_does_not_exist_test() {
        test3 = new Product(
                null,
                "test4",
                "text4",
                ProductCategory.TECHNIC,
                LocalDateTime.of(2021, 4, 3, 2, 1),
                "4",
                true,
                90);

        Assertions.assertEquals("text4", repository.save(test3).getDescription());
        Assertions.assertEquals("4", test3.getManufacturer());
    }

    /**
     * Проверяется работа метода {@link ProductRepositoryImpl#save(Product)},
     * при подаче существующего id.
     */
    @Test
    void save_exist_test() {
        test1.setDescription("999");
        repository.save(test1);
        Assertions.assertEquals("999", repository.save(test1).getDescription());
    }

    /**
     * Проверяется работа метода {@link ProductRepositoryImpl#findAllByCategory(ProductCategory)}
     */
    @Test
    void findAllByCategory_test() {
        Assertions.assertEquals(0, repository.findAllByCategory(ProductCategory.TECHNIC).size());
        Assertions.assertEquals(1, repository.findAllByCategory(ProductCategory.FOOD).size());
    }

}