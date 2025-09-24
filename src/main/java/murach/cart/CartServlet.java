package murach.cart;

import java.io.*;
import jakarta.servlet.*;           
import jakarta.servlet.http.*;      
import jakarta.servlet.annotation.WebServlet;

import murach.data.*;
import murach.business.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletContext sc = getServletContext();

        String action = request.getParameter("action");
        if (action == null) {
            action = "cart";  
        }

        String url = "/index.jsp";

        if (action.equals("shop")) {            
            url = "/index.jsp";    
        } 
        else if (action.equals("cart")) {
            String productCode = request.getParameter("productCode");
            String quantityString = request.getParameter("quantity");

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityString);
                if (quantity < 0) quantity = 1;
            } catch (NumberFormatException nfe) {
                quantity = 1;
            }

            String path = sc.getRealPath("/WEB-INF/products.txt");
            Product product = ProductIO.getProduct(productCode, path);

            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);

            if (quantity > 0) {
                cart.addItem(lineItem);  
            }

            session.setAttribute("cart", cart);
            url = "/cart.jsp";
        }
        else if (action.equals("update")) {
            String productCode = request.getParameter("productCode");
            String quantityString = request.getParameter("quantity");

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) cart = new Cart();

            int quantity;
            try {
                quantity = Integer.parseInt(quantityString);
                if (quantity < 0) quantity = 1;
            } catch (NumberFormatException nfe) {
                quantity = 1;
            }

            String path = sc.getRealPath("/WEB-INF/products.txt");
            Product product = ProductIO.getProduct(productCode, path);

            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);

            if (quantity > 0) {
                cart.updateItem(lineItem);   
            } else {
                cart.removeItem(lineItem);
            }

            session.setAttribute("cart", cart);
            url = "/cart.jsp";
        }
        else if (action.equals("remove")) {
            String productCode = request.getParameter("productCode");

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) cart = new Cart();

            String path = sc.getRealPath("/WEB-INF/products.txt");
            Product product = ProductIO.getProduct(productCode, path);

            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(0);

            cart.removeItem(lineItem);
            session.setAttribute("cart", cart);

            if (cart.getItems().isEmpty()) {
                url = "/index.jsp"; 
            } else {
                url = "/cart.jsp";    
            }
        }
        // ---------------- checkout ----------------
        else if (action.equals("checkout")) {
            url = "/checkout.jsp";
        }
        // ---------------- back to shopping ----------------
        else if (action.equals("backToShopping")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        sc.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
