package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ProductDao;
import data.ProductDto;

public class ExecCreateServlet extends HttpServlet {
	// 商品登録時の遷移先（商品一覧ページ）
	private static final String SUCCESS_PAGE = "/list";
	// 登録失敗時の遷移先（元の商品登録ページ）
	private static final String FAILURE_PAGE = "/WEB-INF/jsp/registrationPage.jsp";
	
	// POSTメソッドのリクエスト受信時に実行されるメソッド
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// レクエスト・レスポンスの設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// フォームから送られたデータを取得
		String productCode = request.getParameter("product_code");
		String productName = request.getParameter("product_name");
		String price = request.getParameter("price");
		String stockQuantity = request.getParameter("stock_quantity");
		String vendorCode = request.getParameter("vendor_code");
		
		// 数値型カラムのデータはString型→Int型に変換
		int iProductCode, iPrice, iStcokQuantity, iVendorCode;
		try {
			iProductCode = Integer.parseInt(productCode);
			iPrice = Integer.parseInt(price);
			iStcokQuantity = Integer.parseInt(stockQuantity);
			iVendorCode = Integer.parseInt(vendorCode);
		} catch (NumberFormatException e) { //数値変換失敗
			forwardFailure("商品名以外の項目には、有効な数値を入力してください。", request, response);
			return;
		}
		
		// 商品名が正しく取得できている（NULLや空文字でない）かチェック
		if (productName == null || productName.isEmpty()) {
			forwardFailure("有効な商品名を入力してください。", request, response);
			return;
		}
		
		// ここからデータ追加処理
		// Dtoのインスタンスを生成し、各カラムのデータをセット
		ProductDto data = new ProductDto();
		data.setProductCode(iProductCode);
		data.setProductName(productName);
		data.setPrice(iPrice);
		data.setStockQuantity(iStcokQuantity);
		data.setVendorCode(iVendorCode);
		
		// 商品データ操作用DAOクラスのインスタンス生成
		ProductDao product = new ProductDao();
		
		// 商品データを追加
		int rowCnt = product.create(data);
		
		// 結果に応じてメッセージを送信
		if (rowCnt > 0) {
			forwardSuccess("商品を" + rowCnt + "件登録しました。", request, response);
		} else {
			forwardFailure("データベース処理エラーが発生しました。システム管理者に確認してください。", request, response);
		}
	}
	
	// 成功時の画面遷移(フォワード)
	private void forwardSuccess(String message, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 成功メッセージをリクエストスコープに保存して画面遷移
		request.setAttribute("successMessage", message);
		request.getRequestDispatcher(SUCCESS_PAGE).forward(request, response);
	}
	
	// 失敗時の画面遷移（フォワード）
	private void forwardFailure(String message, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 失敗メッセージをリクエストスコープに保存して画面遷移
		request.setAttribute("failureMessage", message);
		// 画面遷移
		request.getRequestDispatcher(FAILURE_PAGE).forward(request, response);
	}
}
