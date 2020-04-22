package mate.academy.internetshop;

import java.math.BigDecimal;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product product1 = new Product("Apple", new BigDecimal(1000));
        Product product2 = new Product("Nokia", new BigDecimal(100));
        Product product3 = new Product("HTC", new BigDecimal(10));
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        System.out.println(productService.getAll());
        System.out.println(productService.get(2L));
        productService.delete(product1);
        System.out.println("" + productService.delete((long) 5));
        System.out.println("" + productService.delete((long) 2));

        System.out.println(productService.getAll());

    }
}
