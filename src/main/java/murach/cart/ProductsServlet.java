package murach.cart;

import java.io.*;
import java.net.http.HttpClient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import murach.data.ProductIO;
import murach.business.Product;

@WebServlet("/loadProducts")
public class ProductsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            HttpSession session = request.getSession();

            String path = request.getServletContext().getRealPath("/WEB-INF/products.txt");
            ArrayList<Product> products = ProductIO.getProducts(path);
            session.setAttribute("products", products);

            String url = "/index.jsp";
            request.getServletContext().getRequestDispatcher(url).forward(request, response);
	}
}