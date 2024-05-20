package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ProductDao {
	
	private static final String URL = "jdbc;mariadb://localhost:8889/java_db_app";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "root";
	
	public int create(ProductDto data) {
		int rowCnt = 0;
		
		String sql = "INSERT INTO products(" +
				" product_code, product_name, price, stock_quantity, vendor_code" +
				") VALUES(?, ?, ?, ?, ?);";
	
		try (Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
				PreparedStatement statement = con.prepareStatement(sql)) {
			
			statement.setInt(1, data.getProductCode());
			statement.setString(2, data.getProductName());
			statement.setInt(3, data.getPrice());
			statement.setInt(4, data.getStockQuantity());
			statement.setInt(5, data.getVendorCode());
			
			rowCnt = statement.executeUpdate();
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}
		
		return rowCnt;
	}
	
	public ArrayList<ProductDto> read(int id, String order, String keyword)

		throws SQLException {
		
		String sql = "SELECT * FROM products";
		
		ArrayList<ProductDto> dataList = new ArrayList<ProductDto>();
		
		// 文字列のNULL対策
		order = Objects.toString(order, "");
		keyword = Objects.toString(keyword, "");
		
		try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
			
			if (id > 0) {
				sql += " WHERE id = ?;";
			} else {
				// 検索キーワードが存在する場合
				if (!keyword.isEmpty()) {
					sql += " WHERE product_name LIKE ?";
				}
				
				// 並べ替え方向に合わせてORDER BY句を付加
				if ("asc".equals(order)) {
					sql += " ORDER BY updated_at ASC";
				} else if("desc".equals(order)) {
					sql += " ORDER BY updated_at DESC";
				}
				sql += ";";
			}
		
			try (PreparedStatement statement = con.prepareStatement(sql)) {
				// ?があれば置き換え
				if (id > 0) {
					statement.setInt(1, id); // IDに置き換え
				} else if (!keyword.isEmpty()) { //検索キーワードが存在する場合
					// 検索キーワードに置き換え（%を両端に付加して部分一致検索）
					statement.setString(1, "%" + keyword + "%");
				}
				
				// SQL文を実行
				ResultSet result = statement.executeQuery();
				
				// SQLクエリの実行結果を抽出
				while(result.next()) {
					ProductDto productData = new ProductDto();
					productData.setId(result.getInt("product_code"));
					productData.setProductCode(result.getInt("id"));
					productData.setProductName(result.getString("product_name"));
					productData.setPrice(result.getInt("price"));
					productData.setStockQuantity(result.getInt("stock_quantity"));
					productData.setVendorCode(result.getInt("vendor_code"));
					
					// リストにデータを追加
					dataList.add(productData);
				}
			}
		}
		return dataList; // データリストを返す
	}

	public int update(ProductDto data) {
		int rowCnt = 0;
		
		String sql = "UPDATE products " +
				"SET product_code = ?, " +
				" product_name = ?, " +
				" price = ?, " +
				" stock_quantity = ?, " +
				" vendor_code = ? " +
				"WHERE id = ?;";
		
		try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
				PreparedStatement statement = con.prepareStatement(sql)) {
			
			// SQL文の?を更新するデータで置き換える
			statement.setInt(1, data.getProductCode());
			statement.setString(2, data.getProductName());
			statement.setInt(3, data.getPrice());
			statement.setInt(4, data.getStockQuantity());
			statement.setInt(5, data.getVendorCode());
			statement.setInt(6, data.getId());
			
			rowCnt = statement.executeUpdate();
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}
		
		return rowCnt;
	}

	public int delete(int id) {
		int rowCnt = 0;
		
		String sql = "DELETE FROM products WHERE id = ?;";
		
		try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
				PreparedStatement statement = con.prepareStatement(sql)) {
			
			statement.setInt(1, id);
			
			rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}
		
		return rowCnt;
	}
}