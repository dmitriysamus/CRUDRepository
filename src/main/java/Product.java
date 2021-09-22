import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    UUID id;
    String name;
    String description;
    ProductCategory category;
    LocalDateTime manufactureDateTime;
    String manufacturer;
    boolean hasExpiryTime;
    int stock;

    public Product(UUID id,
                   String name,
                   String description,
                   ProductCategory category,
                   LocalDateTime manufactureDateTime,
                   String manufacturer,
                   boolean hasExpiryTime,
                   int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.manufactureDateTime = manufactureDateTime;
        this.manufacturer = manufacturer;
        this.hasExpiryTime = hasExpiryTime;
        this.stock = stock;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory ProductCategory) {
        this.category = ProductCategory;
    }

    public LocalDateTime getManufactureDateTime() {
        return manufactureDateTime;
    }

    public void setManufactureDateTime(LocalDateTime manufactureDateTime) {
        this.manufactureDateTime = manufactureDateTime;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isHasExpiryTime() {
        return hasExpiryTime;
    }

    public void setHasExpiryTime(boolean hasExpiryTime) {
        this.hasExpiryTime = hasExpiryTime;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", manufactureDateTime=" + manufactureDateTime +
                ", manufacturer='" + manufacturer + '\'' +
                ", hasExpiryTime=" + hasExpiryTime +
                ", stock=" + stock +
                '}';
    }
}
