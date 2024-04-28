package models;

import com.darkin.electronicordersystem.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {
    private DatabaseConnection dbUtil = new DatabaseConnection();

    //Product SQL statements
    public ObservableList<Product> getAllProducts() throws  SQLException, ClassNotFoundException{
        String stmt = "SELECT * FROM `product`";
        try {
            CachedRowSet tmpRowSet = dbUtil.selectQuery(stmt);
            ObservableList<Product> productLst = getProductList(tmpRowSet);
            return productLst;
        }catch (SQLException e){
            System.err.println("SQL Select Operation failed: " + e);
            throw e;
        }
    }


    //Cart SQL Statements
    public ObservableList<Product> getAllCart(int userId) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT product.name, cart.id, product.stock ,cart.quantity, product.price, product.image_path FROM `cart` INNER JOIN `product` ON `productId`=product.id WHERE `isBought`=0 AND `userId`=" + userId + ";";
        try {
            CachedRowSet tmpRowSet = dbUtil.selectQuery(stmt);
            ObservableList<Product> cartList = getCartList(tmpRowSet);
            System.out.println(cartList.toString());
            return cartList;
        }catch (SQLException e){
            System.err.println("SQL Select Operation failed: " + e);
            throw e;
        }
    }
    public void addCart(int userId, int productId, int quantity) throws  SQLException, ClassNotFoundException{
        String stmt = String.format("INSERT INTO `cart` " +
                "(`userId`, `productId`, `quantity`, `isBought`) " +
                "VALUES ('%d', '%d', '%d', '0');", userId, productId, quantity);

        try {
            dbUtil.executeUpdate(stmt);
        }catch (SQLException e){
            System.err.println("Error Occurred while inserting: " + e);
            throw e;
        }
    }
    public void updateQuantity(int quantity, int cartItemId) throws SQLException, ClassNotFoundException{
        String stmt = String.format("UPDATE `cart` SET `quantity` = '%d' WHERE `cart`.`id` = %d ", quantity, cartItemId);

        try {
            dbUtil.executeUpdate(stmt);
        }catch (SQLException e){
            System.err.println("Error Occurred while inserting: " + e);
            throw e;
        }
    }
    public void checkoutCart(int userId, String billNo) throws SQLException, ClassNotFoundException{
        String stmt = String.format("UPDATE `cart` SET isBought=1, billNo='%s'  WHERE `userId`=%d;", billNo,userId);

        try {
            dbUtil.executeUpdate(stmt);
        }catch (SQLException e){
            System.err.println("Error Occurred while inserting: " + e);
            throw e;
        }
    }
    public void removeItemFromCart(int itemId) throws SQLException, ClassNotFoundException{
        String stmt = String.format("DELETE FROM `cart` WHERE `cart`.`id` = %d", itemId);

        try {
            dbUtil.executeUpdate(stmt);
        }catch (SQLException e){
            System.err.println("Error Occurred while inserting: " + e);
            throw e;
        }
    }



    private static Product getProductRst(ResultSet rs) throws SQLException{
        Product product = null;
        if(rs.next()){
            product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setCategory(rs.getString("category"));
            product.setPrice(rs.getDouble("price"));
            product.setStock(rs.getInt("stock"));
            product.setImage_path(rs.getString("image_path"));
        }
        return product;
    }

    private static ObservableList<Product> getProductList(ResultSet rs) throws SQLException, ClassNotFoundException{
        ObservableList<Product> productLst = FXCollections.observableArrayList();
        while(rs.next()){
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setCategory(rs.getString("category"));
            product.setPrice(rs.getDouble("price"));
            product.setStock(rs.getInt("stock"));
            product.setImage_path(rs.getString("image_path"));

            productLst.add(product);
        }

        return  productLst;
    }
    private static ObservableList<Product> getCartList(ResultSet rs) throws SQLException, ClassNotFoundException{
        ObservableList<Product> cartLst = FXCollections.observableArrayList();
        while(rs.next()){
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
            product.setStock(rs.getInt("stock"));
            product.setQuantity(rs.getInt("quantity"));
            product.setImage_path(rs.getString("image_path"));
            System.out.println("id:" + product.getId());

            cartLst.add(product);
        }

        return  cartLst;
    }
    private static void checkCartItem(){
        //TODO: Have a way to check if the addToCart again was clicked and have the same product to existing item in the cart
        //If has item -> Update; Else -> Insert
    }
}
