/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.openmarket.server.domain.services;

import co.unicauca.openmarket.server.access.IProductRepository;
import co.unicauca.strategyserver.helpers.JsonError;
import co.unicauca.strategyserver.helpers.Utilities;
import com.google.gson.Gson;
import com.unicauca.edu.co.openmarket.commons.domain.Product;
import java.util.ArrayList;
import java.util.List;
import reloj.frameworkobsobs.Observado;

/**
 *
 * @author Alejandro
 */
public class ProductService extends Observado{
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
     * Buscar un producto
     *
     * @param id cedula
     * @return objeto tipo Customer
     */
    public synchronized Product findByIdProduct(long id) {
        return repo.findById(id);
    }

     /**
     * Buscar un producto
     *
     * @param name nombre
     * @return objeto tipo Product
     */
    public synchronized Product findByNameProduct(String name) {
        return repo.findByName(name);
    }

    /**
     * Crea una nueva categoria. Aplica validaciones de negocio
     *
     * @param category cliente
     * @return devuelve la cadena "true" si fue creado, en caso contrario retornará errorsJson
     */
    public synchronized String createProduct(Product product) {
        List<JsonError> errors = new ArrayList<>();
  
        // Validaciones y reglas de negocio
        if (product.getProductId().equals(null) || product.getName().isEmpty() || product.getDescription().isEmpty()) {
           errors.add(new JsonError("400", "BAD_REQUEST","Id, nombre y descripcion son obligatorios. "));
        }
        
        if (product.getProductId() < 0){
            errors.add(new JsonError("400", "BAD_REQUEST","El id de categoria no puede ser negativo. "));
        }

        if(!Utilities.isNumeric(product.getProductId().toString())){
            errors.add(new JsonError("400", "BAD_REQUEST","El id debe contener sólo dígitos "));
        }
        
        if(Utilities.isNumeric(product.getDescription())){
            errors.add(new JsonError("400", "BAD_REQUEST","La descripcion del producto debe contener sólo letras "));
        }
        
        // Que no esté repetido
        Product productSearched = this.findByIdProduct(product.getProductId());
        if (productSearched != null){
            errors.add(new JsonError("400", "BAD_REQUEST","El producto ya existe. "));
        }
        
       if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        }
       this.notificar();
       return String.valueOf(repo.save(product));
    }
    
    public synchronized String editProduct(Long id, Product product){
        List<JsonError> errors = new ArrayList<>();
  
        // Validaciones y reglas de negocio
        if (product.getProductId().equals(null) || product.getName().isEmpty() || product.getDescription().isEmpty()) {
           errors.add(new JsonError("400", "BAD_REQUEST","Id, nombre y descripcion son obligatorios. "));
        }
        
        if (product.getProductId() < 0){
            errors.add(new JsonError("400", "BAD_REQUEST","El id de categoria no puede ser negativo. "));
        }

        if(!Utilities.isNumeric(product.getProductId().toString())){
            errors.add(new JsonError("400", "BAD_REQUEST","El id debe contener sólo dígitos "));
        }
        
        if(Utilities.isNumeric(product.getDescription())){
            errors.add(new JsonError("400", "BAD_REQUEST","La descripcion del producto debe contener sólo letras "));
        }
        
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        } 
        this.notificar();
        return String.valueOf(repo.edit(id, product));
    }
    
    public synchronized String deleteProduct(Long id){
        List<JsonError> errors = new ArrayList<>();
        
        Product product = repo.findById(id);
        
        // Validaciones y reglas de negocio
        if (product.equals(null)) {
           errors.add(new JsonError("400", "BAD_REQUEST","Producto no encontrado. "));
        }
        
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        } 
        this.notificar();
        return String.valueOf(repo.delete(product.getProductId()));
    }
    
    public synchronized List<Product> findAllProducts(){
        List<Product> aux = repo.findAll();
        
        if(!aux.isEmpty()){
            return aux;
        }
        return null;
    }
}
