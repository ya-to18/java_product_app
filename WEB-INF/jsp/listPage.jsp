<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Objects" %>
<%@ page import="data.ProductDto"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品一覧</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<%-- Google Fontsの読み込み --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
    <header>
        <nav>
            <a href="<%=request.getContextPath()%>/">商品管理アプリ</a>
        </nav>
    </header>
    <main>
    	<%
    	// 最後に送信されたデータを取得
    	// 検索キーワード
    	String keyword = request.getParameter("keyword");
    	keyword = Objects.toString(keyword, "");
    	
    	// 並べ替え方向
    	String order = request.getParameter("order");
    	order = Objects.toString(order, "");
    	%>
        <article class="products">
            <h1>商品一覧</h1>
            <%
            // 失敗メッセージがあれば表示
            String failureMessage = (String) request.getAttribute("failureMessage");
            if (failureMessage != null && !failureMessage.isEmpty()) {
                out.println("<p class='failure'>" + failureMessage + "</p>");
            }
            %>
            <div class="products-ui">
                <div>
                    <a href="<%= request.getContextPath() %>/list?order=desc&keyword=<%= keyword %>">
                    	<img alt="降順に並べ替え" src="images/desc.png" class="sort-img">
                    </a>
                    <a href="<%= request.getContextPath() %>/list?order=asc&keyword=<%= keyword %>">
                    	<img alt="昇順に並べ替え" src="images/asc.png" class="sort-img">
                    </a>
                    <form action="<%= request.getContextPath() %>/list" method="get" class="search-form">
                    	<input type="hidden" name="order" value="<%= order %>">
                    	<input type="text" name="keyword" class="search-box" placeholder="商品名で検索" value="<%= keyword %>">
                    </form>
                </div>
                <a href="<%= request.getContextPath() %>/register" class="btn">商品登録</a>
            </div>
            <table class="products-table">
                <tr>
                    <th class="hidden-id">ID</th>
                    <th>商品コード</th>
                    <th>商品名</th>
                    <th>単価</th>
                    <th>在庫数</th>
                    <th>仕入先コード</th>
                </tr>
                <%
                // 商品データリストを取得
                ArrayList<ProductDto> productList = (ArrayList<ProductDto>) request.getAttribute("productList");

                if (productList != null) {
                    // 商品データを1レコードずつ表形式で出力
                    for (ProductDto product : productList) {
                        out.println("<tr>");
                        out.println("<td class='hidden-id'>" + product.getId() + "</td>");
                        out.println("<td>" + product.getProductCode() + "</td>");
                        out.println("<td>" + product.getProductName() + "</td>");
                        out.println("<td>" + product.getPrice() + "</td>");
                        out.println("<td>" + product.getStockQuantity() + "</td>");
                        out.println("<td>" + product.getVendorCode() + "</td>");
                        out.println("</tr>");
                    }
                }
                %>
            </table>
        </article>
    </main>
    <footer>
        <p class="copyright">&copy; 商品管理アプリ All rights reserved.</p>
    </footer>
</body>
</html>
