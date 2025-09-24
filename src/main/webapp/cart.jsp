<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Your Cart</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
<!--    <style>
        table {
            width: 90%;
            border-collapse: collapse;
            margin: 20px auto;
        }
        th, td {
            border: 1px solid #666;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        .alert {
            width: 60%;
            margin: 20px auto;
            padding: 15px;
            text-align: center;
            border: 1px solid #ccc;
            background-color: #e7f3fe;
            color: #31708f;
            border-radius: 5px;
        }
    </style>-->
</head>
<body>

<h1>Your Cart</h1>

<c:if test="${empty cart.items}">
    <div class="alert">Your cart is empty.</div>
</c:if>

<c:if test="${not empty cart.items}">
<table>
    <tr>
        <th>Quantity</th>
        <th>Description</th>
        <th>Price</th>
        <th>Amount</th>
        <th>Action</th>
    </tr>
    <c:forEach var="item" items="${cart.items}">
        <tr>
            <td>
                <!-- Form Update -->
                
                <form action="cart" method="post" class="update">
                    <input type="hidden" name="productCode" value="${item.product.code}">
                    <input type="hidden" name="action" value="update">
                    <input type="number" name="quantity" value="${item.quantity}" min="0">
                    <input type="submit" value="Update">
                </form>
            </td>
            <td class="des">${item.product.description}</td>
            <td>${item.product.priceCurrencyFormat}</td>
            <td>${item.totalCurrencyFormat}</td>
            <td>
                <!-- Form Remove -->
                <form action="cart" method="post">
                    <input type="hidden" name="productCode" value="${item.product.code}">
                    <input type="hidden" name="action" value="remove">
                    <input type="submit" value="Remove">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<div style="text-align:center; margin-top:20px;">
    <form action="cart" method="post" style="display:inline;">
        <input type="hidden" name="action" value="shop">
        <input type="submit" value="⬅ Continue Shopping">
    </form>
    &nbsp;&nbsp;
    <form action="cart" method="post" style="display:inline;">
        <input type="hidden" name="action" value="checkout">
        <input type="submit" value="Checkout ➡">
    </form>
</div>
</c:if>

</body>
</html>
