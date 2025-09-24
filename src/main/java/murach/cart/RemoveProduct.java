package murach.cart;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import murach.business.Product;

@WebServlet("/delete")
public class RemoveProduct extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String productCode = req.getParameter("productCode");
		HttpSession session = req.getSession();
		ArrayList<Product> products = (ArrayList<Product>) session.getAttribute("products");
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getCode().equals(productCode)) {
				products.remove(i);
			}
		}
		
		session.setAttribute("products", products);
		CartServlet cs = new CartServlet();
		req.getServletContext().getRequestDispatcher("/cart.jsp").forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
