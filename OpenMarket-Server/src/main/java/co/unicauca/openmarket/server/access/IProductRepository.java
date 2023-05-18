package co.unicauca.openmarket.server.access;

import com.unicauca.edu.co.openmarket.commons.domain.Product;
import java.util.List;

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
    
     boolean save(Product newProduct);
    
    boolean edit(Long id, Product product);
    
    boolean delete(Long id);

    Product findById(Long id);
    
    Product findByName (String name);
    
    List<Product> findAll();
}
