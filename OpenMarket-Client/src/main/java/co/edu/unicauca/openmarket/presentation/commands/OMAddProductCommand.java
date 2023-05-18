/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.openmarket.presentation.commands;


import co.edu.unicauca.openmarket.domain.service.ProductService;
import co.edu.unicauca.openmarket.access.ProductAccessImplSockets;
import com.unicauca.edu.co.openmarket.commons.domain.Product;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 *
 * @author ahurtado
 */
public class OMAddProductCommand extends OMCommand {

    private Product pP;
    private ProductAccessImplSockets pS;
    boolean result=true;
    public OMAddProductCommand(Product pP, ProductAccessImplSockets pS){
        this.pP = pP;
        this.pS = pS;
    }
    
    
    @Override
    public void make() {
        try {
            result = pS.save(pP);
        } catch (Exception ex) {
            Logger.getLogger(OMAddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
   public void unmake() {
        List<Product> products = null;
        try {
            products = pS.findAll();
        } catch (Exception ex) {
            Logger.getLogger(OMAddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Product each: products){
            if(each.getName().equals(pP.getName())){
                try {
                    result = pS.delete(each.getProductId());
                } catch (Exception ex) {
                    Logger.getLogger(OMAddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public boolean result(){
        return result;
    }
    
}
