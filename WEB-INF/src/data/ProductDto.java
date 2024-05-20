package data;

public class ProductDto {
	private int id  = 0;
	private int productCode = 0;
	private String productName = "";
	private int price = 0;
	private int stockQuantity = 0;
	private int vendorCode = 0;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getProductCode() {
		return this.productCode;
	}
	
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getStockQuantity() {
		return this.stockQuantity;
	}
	
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	public int getVendorCode() {
		return vendorCode;
	}
	
	public void setVendorCode(int vendorCode) {
		this.vendorCode = vendorCode;
	}
}
