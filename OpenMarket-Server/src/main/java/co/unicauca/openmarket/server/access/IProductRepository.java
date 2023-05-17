package co.unicauca.openmarket.server.access;

import co.unicauca.openmarket.commons.domain.Product;

/**
 * Interface del respositorio de clientes
 * @author Libardo Pantoja
 */
public interface IProductRepository {
    /**
     * Busca un Customer por su ceduka
     * @param id cedula del cliente
     * @return  objeto de tipo Customer
     */
    
    public Product findProduct(String id);
    public String createProduct(Product product);

}
