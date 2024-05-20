package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VendorDao {
	
    private static final String URL = "jdbc:mariadb://localhost8889/java_db_app"; // MAMPの場合は"localhost:8889"としてください
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    public ArrayList<VendorDto> read()
    	throws SQLException {
    	
    	String sql = "SELECT vendor_code FROM vendors;";
    	
    	ArrayList<VendorDto> dataList = new ArrayList<VendorDto>();
    	
    	try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    			Statement statement = con.createStatement();) {
    		ResultSet result = statement.executeQuery(sql);
    		
    		while(result.next()) {
    			// DTOのインスタンスに実行結果を抽出
    			VendorDto vendorData = new VendorDto();
    			vendorData.setVendorCode(result.getInt("vendor_code"));
    			
    			dataList.add(vendorData);
    		}
    	}
    	
    	return dataList;
    }
}
