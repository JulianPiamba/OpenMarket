package co.edu.unicauca.openmarket.access;


import com.unicauca.edu.co.openmarket.commons.domain.Product;
import java.util.List;

/**
 * Interface que define los servicios de persistencia de productos
 *
 * @author Libardo Pantoja, Julio Hurtado
 */
public interface IProductAccess {

    boolean save(Product newProduct) throws Exception;
    
    boolean edit(Long id, Product product) throws Exception;
    
    boolean delete(Long id) throws Exception;

    Product findById(Long id) throws Exception;
    
    Product findByName (String name) throws Exception;
    
    List<Product> findAll() throws Exception;
}