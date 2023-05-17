
package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.domain.Product;
import co.edu.unicauca.openmarket.infra.OpenMarketSockets;
//import co.edu.unicauca.openmarket.presentation.GUIProductsFind;
import co.unicauca.openmarket.commons.infra.Protocol;
import co.unicauca.openmarket.commons.infra.JsonError;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import reloj.frameworkobsobs.Observado;

/**
 * Servicio de Cliente. Permite hacer el CRUD de clientes solicitando los
 * servicios con la aplicación server. Maneja los errores devueltos
 *
 * @author Libardo Pantoja, Julio A. Hurtado
 */
public class ProductAccessImplSockets extends Observado implements IProductAccess {
    /**
     * El servicio utiliza un socket para comunicarse con la aplicación server
     */
    private OpenMarketSockets mySocket;

    public ProductAccessImplSockets() {
        mySocket = new OpenMarketSockets();
    }
    
   // @Override
        public boolean saveProduct(Product newProduct) throws Exception {
        String requestJson = doSaveProductRequestJSON(newProduct);

        try /*Socket mySocket = new Socket()*/ {
            mySocket.connect();
            String jsonResponse = mySocket.sendRequest(requestJson);

            if (jsonResponse == null) {
                throw new Exception("No se pudo conectar con el servidor");
            } else if (jsonResponse.contains("error")) {
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                throw new Exception(extractMessages(jsonResponse));
            } else {
                this.notificar();
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
            return false;
        }
      }
    
    
    

    /**
     * Busca un Product. Utiliza socket para pedir el servicio al servidor
     *
     * @param id del producto
     * @return Objeto Product
     * @throws Exception cuando no pueda conectarse con el servidor
     */
    @Override
    public Product findProduct(String id) throws Exception {
        String jsonResponse = null;
        String requestJson = doFindProductRequestJson(id);
        System.out.println(requestJson);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            throw new Exception("No se pudo conectar con el servidor. Revise la red o que el servidor esté escuchando. ");
        } else {
            if (jsonResponse.contains("error")) {
                //Devolvió algún error
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                throw new Exception(extractMessages(jsonResponse));
            } else {
                //Encontró el Product
                Product product = jsonToProduct(jsonResponse);
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, "Lo que va en el JSon: ("+jsonResponse.toString()+ ")");
                return product;
            }
        }
    }
    

    /**
     * Crea un Customer. Utiliza socket para pedir el servicio al servidor
     *
     * @param customer cliente de la agencia de viajes
     * @return devuelve la cedula del cliente creado
     * @throws Exception error crear el cliente
     */
    @Override
    public String createProduct(Product product) throws Exception {
        String jsonResponse = null;
        String requestJson = doCreateProductRequestJson(product);
        try {
            mySocket.connect();
            jsonResponse = mySocket.sendRequest(requestJson);
            mySocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            throw new Exception("No se pudo conectar con el servidor");
        } else {

            if (jsonResponse.contains("error")) {
                //Devolvió algún error                
                Logger.getLogger(ProductAccessImplSockets.class.getName()).log(Level.INFO, jsonResponse);
                throw new Exception(extractMessages(jsonResponse));
            } else {
                //Agregó correctamente, devuelve la cedula del customer 
                return product.getProductId().toString();
            }

        }

    }
    /**
     * Extra los mensajes de la lista de errores
     * @param jsonResponse lista de mensajes json
     * @return Mensajes de error
     */
    private String extractMessages(String jsonResponse) {
        JsonError[] errors = jsonToErrors(jsonResponse);
        String msjs = "";
        for (JsonError error : errors) {
            msjs += error.getMessage();
        }
        return msjs;
    }

    /**
     * Convierte el jsonError a un array de objetos jsonError
     *
     * @param jsonError
     * @return objeto MyError
     */
    private JsonError[] jsonToErrors(String jsonError) {
        Gson gson = new Gson();
        JsonError[] error = gson.fromJson(jsonError, JsonError[].class);
        return error;
    }

    /**
     * Crea una solicitud json para ser enviada por el socket
     *
     *
     * @param idProduct identificación del Product
     * @return solicitud de consulta del cliente en formato Json, algo como:
     * {"resource":"customer","action":"get","parameters":[{"name":"id","value":"98000001"}]}
     */
    private String doFindProductRequestJson(String idProduct) {

        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("get");
        protocol.addParameter("id", idProduct);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);

        return requestJson;
    }

    /**
     * Crea la solicitud json de creación del customer para ser enviado por el
     * socket
     *
     * @param Product objeto Product
     * @return devulve algo como:
     * {"resource":"customer","action":"post","parameters":[{"name":"id","value":"980000012"},{"name":"fistName","value":"Juan"},...}]}
     */
    private String doCreateProductRequestJson(Product product) {

        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("post");
        protocol.addParameter("id", product.getProductId().toString());
        protocol.addParameter("fistName", product.getName());
        protocol.addParameter("lastName", product.getDescription());
        protocol.addParameter("address", product.getCategory().toString());
        
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }

    /**
     * Convierte jsonProduct, proveniente del server socket, de json a un
     * objeto Product
     *
     * @param jsonProduct objeto cliente en formato json
     */
    private Product jsonToProduct(String jsonProduct) {

        Gson gson = new Gson();
        Product product = gson.fromJson(jsonProduct, Product.class);
        return product;

    }

   
        
         private String doSaveProductRequestJSON(Product product){
        
        Protocol protocol = new Protocol();
        protocol.setResource("product");
        protocol.setAction("post");
        protocol.addParameter("id", product.getProductId().toString());
        protocol.addParameter("name", product.getName());
        protocol.addParameter("description", product.getDescription());

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocol);
        return requestJson;
    }
    
    
   /* public Product findProductById(Long productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean deleteProduct(Long productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean editProduct(Long productId, Product prod) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Product> findAllProducts() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    */

    public Product findProductById(Long productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean deleteProduct(Long productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean editProduct(Long productId, Product prod) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

}
