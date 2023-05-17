/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.server.infra.web;

import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.commons.infra.Parameters;
import co.unicauca.openmarket.server.domain.services.ProductService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ahurtado
 */
public class OpenMarketWebHandler implements HttpHandler  {

    private ProductService service;
 
    
    @Override
    public void handle(HttpExchange t) throws IOException {
            Scanner scanner = new Scanner(t.getRequestBody());
            String requestJson = scanner.nextLine();
            Gson gson = new Gson();
            System.out.println(requestJson);
            Parameters parameters = gson.fromJson(requestJson, Parameters.class);
            String response="";
            if (t.getRequestMethod().equals("GET"))
               response = processGetProduct(parameters);
            if (t.getRequestMethod().equals("POST")) 
                response = processPostProduct(parameters);
            respond(response, t);
    }
    
    private void respond(String response, HttpExchange t) throws IOException{
            t.sendResponseHeaders(200, response.getBytes().length);
            t.getResponseBody().write(response.getBytes());
            t.getResponseBody().close();
        
    }
    
    /* 
    Procesa la petición de un cliente, se le debe pasar esta información
    {"parameters":[{"name":"id","value":"98000003"}]} */
    
    private String processGetProduct(Parameters parameters) throws IOException {
        // Extraer la cedula del primer parámetro
        String id = parameters.getParameters().get(0).getValue();
        Product product = service.findProduct(id);
        if (product == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(product);
        }
    }  
    /**
     * Genera un ErrorJson de cliente no encontrado
     *
     * @return error en formato json
     */
    private String generateNotFoundErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("404");
        error.setError("NOT_FOUND");
        error.setMessage("Producto no encontrado. Id no existe");
        errors.add(error);

        Gson gson = new Gson();
        String errorsJson = gson.toJson(errors);

        return errorsJson;
    }
        
    private String generateErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("400");
        error.setError("BAD_REQUEST");
        error.setMessage("Error en la solicitud");
        errors.add(error);

        Gson gson = new Gson();
        String errorJson = gson.toJson(errors);

        return errorJson;
    }
    
    /**
     * Convierte el objeto Customer a json para que el servidor lo envie como
     * respuesta por el socket
     *
     * @param object
     * @return customer en formato json
     */
    protected String objectToJSON(Object object) {
        Gson gson = new Gson();
        String strObject = gson.toJson(object);
        return strObject;
    }

    /**
     * Procesa la solicitud de agregar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processPostProduct(Parameters parameters) throws IOException {
        Product product = new Product();
        // Reconstruir el customer a partid de lo que viene en los parámetros
        product.setProductId(parameters.getParameters().get(0).getValue());
        product.setName(parameters.getParameters().get(1).getValue());
        product.setDescription(parameters.getParameters().get(2).getValue());
        product.setPrice(parameters.getParameters().get(3).getValue());
        product.setCategory(parameters.getParameters().get(4).getValue());
        product.setLocation(parameters.getParameters().get(5).getValue());

        String response = getService().createProduct(product);
        return response;
    }

    /**
     * @return the service
     */
    public ProductService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(ProductService service) {
        this.service = service;
    } 
}
