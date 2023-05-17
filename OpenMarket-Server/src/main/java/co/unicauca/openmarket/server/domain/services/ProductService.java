package co.unicauca.openmarket.server.domain.services;

import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.commons.infra.Utilities;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import co.unicauca.openmarket.server.access.IProductRepository;

/**
 * Servicio de clientes. Da acceso a la lógica de negocio
 *
 * @author Libardo, Julio
 */
public class ProductService {

    /**
     * Repositorio de clientes
     */
    IProductRepository repo;

    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *
     * @param repo repositorio de tipo ICustomerRepository
     */
    public ProductService(IProductRepository repo) {
        this.repo = repo;
    }

    /**
     * Buscar un cliente
     *
     * @param id cedula
     * @return objeto tipo Customer
     */
    public synchronized Product findProduct(String id) {
        return repo.findProduct(id);
    }

    /**
     * Crea un nuevo Product. Aplica validaciones de negocio
     *
     * @param Product cliente
     * @return devuelve la id del Product creado
     */
    public synchronized String createProduct(Product product) {
        List<JsonError> errors = new ArrayList<>();
  
        // Validaciones y reglas de negocio
        if (product.getProductId().isEmpty() || product.getName().isEmpty()
                || product.getDescription().isEmpty() || product.getPrice().isEmpty()
                || product.getCategory().isEmpty()) {
           errors.add(new JsonError("400", "BAD_REQUEST","id, nombre, descrpcion, precio y categorya. "));
        }     
        
        if(!Utilities.isNumeric(product.getPrice())){
            errors.add(new JsonError("400", "BAD_REQUEST","Precio debe contener sólo dígitos "));
            
        }
        // Que no esté repetido
        
        Product productSearched = this.findProduct(product.getProductId());
        if (productSearched != null){
            errors.add(new JsonError("400", "BAD_REQUEST","La id ya existe. "));
        }
        
       if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        }             
        return repo.createProduct(product);
    }


}
