package com.darkin.electronicordersystem.models;

import com.darkin.electronicordersystem.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    public ObservableList<Product> getAllCart(int account_id) throws SQLException, ClassNotFoundException{
        String stmt = "SELECT product.name, cart.id, product.stock ,cart.quantity, product.price, product.image_path FROM `cart` INNER JOIN `product` ON `product_id`=product.id WHERE `account_id`=" + account_id + ";";
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
    public void addCart(int account_id, int product_id, int quantity) throws  SQLException, ClassNotFoundException{
        String stmt = String.format("INSERT INTO `cart` " +
                "(`account_id`, `product_id`, `quantity`) " +
                "VALUES ('%d', '%d', '%d');", account_id, product_id, quantity);

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
    public void checkoutCart(int account_id, double totalPrice, String billNo) throws SQLException, ClassNotFoundException{;
        LocalDateTime now = LocalDateTime.now();
        String ref_no =  String.valueOf (Integer.valueOf(billNo) * now.hashCode());
        //TODO: Need to change and forward the items in cart -> order and order_items
        String stmt = String.format("SELECT cart.product_id , cart.quantity, product.price FROM `cart` INNER JOIN `product` ON `product_id`=product.id WHERE `account_id`=%d;", account_id);
        String orderStmt = String.format("INSERT INTO `order` (`order_date`, `total_price`, `ref_no`, `account_id`) " +
                "VALUES ('%s', %.2f, %s, %d)", now, totalPrice, ref_no, account_id);
        String selectOrderStmt = String.format("SELECT * FROM `order` WHERE `ref_no`='%s'", ref_no);


        String delStmt = String.format("DELETE FROM `cart` WHERE `account_id`=%d;", account_id);


        try {
            CachedRowSet tmpRowSet = dbUtil.selectQuery(stmt);
            int order_id = 0;
            ArrayList<Product> tmpPrd = getCheckoutList(tmpRowSet);
            dbUtil.executeUpdate(orderStmt);

            CachedRowSet tmpOrderSet = dbUtil.selectQuery(selectOrderStmt);
            Order order = null;
            while (tmpOrderSet.next()){
                order = new Order();
                order.setId(tmpOrderSet.getInt("id"));
                order.setOrder_date(LocalDateTime.parse(tmpOrderSet.getString("order_date")));
                order.setTotal_price(tmpOrderSet.getDouble("total_price"));
                order.setRef_no(tmpOrderSet.getString("ref_no"));
                order.setAccount_id(tmpOrderSet.getInt("account_id"));
            }

            dbUtil.executeUpdate(setOrderItem(tmpPrd, order.getId()));
            dbUtil.executeUpdate(delStmt);

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
    private static void checkCartItem(Product product, int account_id){
        //TODO: Have a way to check if the addToCart again was clicked and have the same product to existing item in the cart
        //If has item -> Update; Else -> Insert

    }

    /**
     * It will traverse to the result set given that holds what's from the cart that will be passed to the order_item
     *
     * It needs to return inside the Product are:
     *  - product_id
     *  - price
     *  - quantity
     *
     * @param rs
     * @return ArrayList<Product>
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ArrayList<Product> getCheckoutList(ResultSet rs) throws  SQLException, ClassNotFoundException{
        ArrayList<Product> tmpPrd = new ArrayList<Product>();
        while(rs.next()){
            Product product = new Product();
            product.setId(rs.getInt("product_id"));
            product.setQuantity(rs.getInt("quantity"));
            product.setPrice(rs.getDouble("price"));
            tmpPrd.add(product);
        }
        return tmpPrd;
    }
    private static String setOrderItem(ArrayList<Product> order_items, int order_id) throws  SQLException, ClassNotFoundException{
        String stmt = "INSERT INTO `order_item` (`quantity`, `price`, `product_id`, `order_id`) VALUES ";
        for(Product tmpP: order_items){
            if(tmpP.hashCode() == order_items.getLast().hashCode()){
                stmt += String.format("(%d, %.2f, %d, %d)", tmpP.getQuantity(), tmpP.getPrice(), tmpP.getId(), order_id);
            }else{
                stmt += String.format("(%d, %.2f, %d, %d),", tmpP.getQuantity(), tmpP.getPrice(), tmpP.getId(), order_id);
            }
        }
        return stmt;

    }
}
