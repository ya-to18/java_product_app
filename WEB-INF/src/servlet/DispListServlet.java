package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ProductDao;
import data.ProductDto;

// 商品一覧ページ用Servlet
public class DispListServlet extends HttpServlet {
    // GETメソッドのリクエスト受信時に実行されるメソッド
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエスト・レスポンスの設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // JSPからのリクエストデータ取得
        String order = request.getParameter("order");
        order = Objects.toString(order, ""); // NULLは空文字に置き換え

        // 商品データリストのインスタンスを生成
        ArrayList<ProductDto> productList = new ArrayList<ProductDto>();

        // 商品データ操作用DAOクラスのインスタンスを生成
        ProductDao product = new ProductDao();

        try {
            // 商品データの一覧を取得（ID指定・検索なし）
            productList = product.read(0, order, "");

            if (productList.isEmpty()) {
                // 商品データリストが空だった場合はメッセージを送る
                request.setAttribute("failureMessage", "該当するデータが見つかりませんでした。");
            } else {
                // 取得した商品データリストを送る
                request.setAttribute("productList", productList);
            }
        } catch (SQLException e) { // データベース処理の例外発生時
            request.setAttribute("failureMessage", "データベース処理エラーが発生しました。システム管理者に確認してください。");
        }

        // フォワードによる画面遷移
        request.getRequestDispatcher("/WEB-INF/jsp/listPage.jsp").forward(request, response);
    }
}
