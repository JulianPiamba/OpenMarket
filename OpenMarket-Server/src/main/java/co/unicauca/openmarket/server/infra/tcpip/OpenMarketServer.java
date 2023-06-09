/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.server.infra.tcpip;

import co.unicauca.strategyserver.infra.ServerSocketMultiThread;
import co.unicauca.openmarket.server.access.ProductRepositoryImplArrays;
import co.unicauca.openmarket.server.domain.services.ProductService;
import java.util.Scanner;


/**
 *
 * @author ahurtado
 */
public class OpenMarketServer {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerSocketMultiThread myServer = new ServerSocketMultiThread(5000);
        OpenMarketHandler myHandler = new OpenMarketHandler();
        myHandler.setServiceP(new ProductService(new ProductRepositoryImplArrays()));
        myServer.setServerHandler(myHandler);
        myServer.startServer();
        myHandler.getServiceP().addObservador(myHandler);
    }
    
}
