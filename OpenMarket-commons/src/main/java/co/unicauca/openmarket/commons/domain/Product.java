package co.unicauca.openmarket.commons.domain;

import java.util.Date;
import java.util.Locale.Category;
import javax.tools.DocumentationTool.Location;

/**
 * Cliente de la agencia de viajes
 *
 * @author Libardo, Julio
 */
public class Product {

    private String productId;

    private String name;
    
    private String description;
    
    private String price;
    
    private String category;
    
    private String location;

    /**
     * Constructor parametrizado
     *
     * @param productId id producto
     * @param name nombre producto
     * @param description descripcion
     * @param price precio
     * @param category categoria
     * @param location location
     */
    public Product(String name, String description, String vaso_de_aluminio, String price, String category, String location) {    
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.location = location;
    }

    /**
     * Constructor por defecto
     */
    public Product() {
    }
    
    /**
     * Getters and Setters
     *
     * @return
     */
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
