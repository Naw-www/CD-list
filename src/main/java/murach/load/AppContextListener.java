package murach.load;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.ArrayList;
import murach.business.Product;
import murach.data.ProductIO;

// This annotation tells the server that this class is a listener
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application is starting up... loading data.");

        // 1. Get the ServletContext object. This is the "application scope".
        ServletContext context = sce.getServletContext();
        
        // 2. Get the path to your data file.
        String path = context.getRealPath("/WEB-INF/products.txt");
        
        // 3. Load your data using your existing I/O class.
        ArrayList<Product> products = ProductIO.getProducts(path);
        
        // 4. Store the loaded data in the application scope.
        // Now, any servlet can access this "products" list.
        context.setAttribute("products", products);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // This method is called when the server shuts down.
        // You can add cleanup code here if needed.
        System.out.println("Application is shutting down.");
    }
}