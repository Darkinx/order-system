package model;

import com.darkin.electronicordersystem.DatabaseConnection;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {
    private DatabaseConnection dbUtil = new DatabaseConnection();

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
}
