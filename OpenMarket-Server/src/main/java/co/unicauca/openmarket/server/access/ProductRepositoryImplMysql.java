package co.unicauca.openmarket.server.access;

import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Repositorio de Clientes en MySWL
 *
 * @author Libardo, Julio
 */
public class ProductRepositoryImplMysql implements IProductRepository {

    /**
     * Conección con Mysql
     */
    private Connection conn;

    public ProductRepositoryImplMysql() {

    }
    /**
     * Busca en la bd un customer
     * @param id cedula
     * @return objeto customer, null si no lo encuentra
     */
    @Override
    public Product findProduct(String id) {
        Product product = null;

        this.connect();
        try {
            String sql = "SELECT * from products where id=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                product = new Product();
                product.setProductId(res.getString("id"));
                product.setName(res.getString("name"));
                product.setDescription(res.getString("description"));
                product.setCategory(res.getString("category"));
                product.setPrice(res.getString("price"));
                product.setLocation(res.getString("location"));

            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Customer de la base de datos", ex);
        }
        return product;
    }

    @Override
    public String createProduct(Product product) {
        
        try {

            this.connect();
            String sql = "INSERT INTO products(id, name, description, price, category, location) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getProductId());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, product.getPrice());
            pstmt.setString(5, product.getCategory());
            pstmt.setString(6, product.getLocation());


            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return product.getProductId();

    }

    /**
     * Permite hacer la conexion con la base de datos
     *
     * @return
     */
    public int connect() {
        try {
            Class.forName(Utilities.loadProperty("server.db.driver"));
            //crea una instancia de la controlador de la base de datos
            String url = Utilities.loadProperty("server.db.url");
            String username = Utilities.loadProperty("server.db.username");
            String pwd = Utilities.loadProperty("server.db.password");
            conn = DriverManager.getConnection(url, username, pwd);
            return 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Product de la base de datos", ex);
        }
        return -1;
    }

    /**
     * Cierra la conexion con la base de datos
     *
     */
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

}
