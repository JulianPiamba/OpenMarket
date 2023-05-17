package co.unicauca.openmarket.server.access;

import co.unicauca.openmarket.commons.domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de ICustomerRepository. Utilliza arreglos en memoria
 *
 * @author Libardo Pantoja, Julio Hurtado
 */
public final class ProductRepositoryImplArrays implements IProductRepository {

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
        products.add(new Product("98000001", "Muck gren", "vaso de aluminio", "5", "Vasos", "Bogota"));
        products.add(new Product("98000002", "Huawei p20", "Celular inteligente", "200", "Celulares", "Bogota"));
        products.add(new Product("98000003", "Tomate rise", "Salsa de tomate picante", "12", "Salsa", "Cali"));
        products.add(new Product("98000004", "Radio GX", "Radio comunicador", "27", "Electronica", "Popayan"));
        products.add(new Product("98000005", "Cameo siluet", "Cortador cameo mini", "850", "Electronica", "Bogota"));
        products.add(new Product("98000006", "Pantalla gaming pro", "Monitor 244 ghz", "720", "Electronica", "Bogota"));
        

    }

    /**
     * Busca u Product en el arreglo
     *
     * @param id cedula del customer
     * @return objeto Customer
     */
    @Override
    public Product findProduct(String id) {
        for (Product product : products) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public String createProduct(Product product) {
        products.add(product);
        return product.getProductId();
    }

}
