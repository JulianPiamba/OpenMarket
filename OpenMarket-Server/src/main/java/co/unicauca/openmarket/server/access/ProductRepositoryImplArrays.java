package co.unicauca.openmarket.server.access;

import com.unicauca.edu.co.openmarket.commons.domain.Product;
import java.util.ArrayList;
import java.util.List;
import reloj.frameworkobsobs.Observado;

/**
 * Implementación de ICustomerRepository. Utilliza arreglos en memoria
 *
 * @author Libardo Pantoja, Julio Hurtado
 */
public final class ProductRepositoryImplArrays extends Observado implements IProductRepository {

    /**
     * Array List de clientes
     */
    private static List<Product> products;

    public ProductRepositoryImplArrays() {
        if (products == null){
            products = new ArrayList();
        }
        
        if (products.size() == 0){
            inicializar();
        }
    }

    public void inicializar() {
        products.add(new Product(10L, "Muck gren", "vaso de aluminio",100));
        products.add(new Product(20L, "Rosa roja", "Rosa plastica",200));
        products.add(new Product(30L, "Rasca culo", "Rascador de emergencia",300));
        products.add(new Product(40L, "laza Piedras", "resortera de metal",400));
        products.add(new Product(50L, "Play station 11", "consola no creada",500));
        products.add(new Product(60L, "Valorant", "Potenciador mental",600));
        products.add(new Product(70L, "LOL", "Juego para discapacitados",700));
        
        

    }

    /**
     * Busca u Product en el arreglo
     *
     * @param id cedula del customer
     * @return objeto Customer
     */
    public boolean save(Product newProduct) {
        if(!newProduct.equals(null)){
            products.add(newProduct);
            this.notificar();
            return true;
        }
        return false;
    }

    @Override
    /**
    * Edita una categoria en el arreglo
    * 
    * @param id Id del producto a editar
    * @param product objeto producto ya editado
    * @return true si se editó con éxito, en caso contrario false.
    */
    public boolean edit(Long id, Product product) {
        for(int i=0; i < products.size(); i++){
            if(products.get(i).getProductId().equals(id)){
                products.set(i, product);
                this.notificar();
                return true;
            }
        }
        return false;
    }

    @Override
    /**
    * Elimina un producto del arreglo
    *
    * @param id Id de la categoria que se eliminará del arreglo
    * @return true si se elimina con éxito, en caso contrario false.
    */
    public boolean delete(Long id) {
        for(int i=0; i < products.size(); i++){
            if(products.get(i).getProductId().equals(id)){
                products.remove(i);
                this.notificar();
                return true;
            }
        }
        return false;
    }

    @Override
    /**
    * Encuentra un producto por Id en el arreglo
    *
    * @param id Id del producto que se encontrará del arreglo
    * @return Objeto categoria encontrado por el Id.
    */
    public Product findById(Long id) {
        for(Product p: products){
            if(p.getProductId().equals(id)){
                return p;
            }
        }
        return null;
    }

    @Override
    /**
    * Encuentra un producto por Id en el arreglo
    *
    * @param name Nombre del producto que se encontrará del arreglo
    * @return Objeto categoria encontrado por el nombre.
    */
    public Product findByName(String name) {
        for(Product p: products){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    @Override
    /**
    * Encuentra todas los productos del arreglo.
    *
    * @return El arreglo de los productos
    */
    public List<Product> findAll() {
        return products;
    }
}
