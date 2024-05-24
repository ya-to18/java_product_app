<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Objects"%>
<%@ page import="data.VendorDto"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品登録</title>
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
        <article class="registration">
            <h1>商品登録</h1>
            <div class="back">
                <a href="<%=request.getContextPath()%>/list" class="btn">&lt; 戻る</a>
            </div>
            <form action="<%=request.getContextPath()%>/create" method="post" class="registration-form">
                <div>
                    <label for="product_code">商品コード</label>
                    <input type="number" id="product_code" name="product_code" min="0" max="100000000" required>
                    <label for="product_name">商品名</label>
                    <input type="text" id="product_name" name="product_name" maxlength="50" required>
                    <label for="price">単価</label>
                    <input type="number" id="price" name="price" min="0" max="100000000" required>
                    <label for="stock_quantity">在庫数</label>
                    <input type="number" id="stock_quantity" name="stock_quantity" min="0" max="100000000" required>
                    <label for="vendor_code">仕入先コード</label>
                    <select id="vendor_code" name="vendor_code" required>
                        <option disabled selected value>選択してください</option>
                        <%
                        // 仕入先データリストを取得
                        ArrayList<VendorDto> vendorList = (ArrayList<VendorDto>) request.getAttribute("vendorList");

                        if (vendorList != null) {
                            // 仕入先データを1件ずつセレクトボックスの選択肢として出力
                            for (VendorDto vendor : vendorList) {
                                out.println("<option value='" + vendor.getVendorCode() + "'>"
                                + vendor.getVendorCode() + "</option>");
                            }
                        }
                        %>
                    </select>
                </div>
                <button type="submit" class="submit-btn" name="submit" value="create">登録</button>
            </form>
        </article>
    </main>
    <footer>
        <p class="copyright">&copy; 商品管理アプリ All rights reserved.</p>
    </footer>
</body>
</html>
