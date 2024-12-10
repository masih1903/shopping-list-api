package app.routes;

import app.config.HibernateConfig;
import app.controllers.ProductController;
import app.daos.ProductDAO;
import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class ProductRoute {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final ProductDAO productDAO = new ProductDAO(emf);
    private final ProductController productController = new ProductController(productDAO);

    public EndpointGroup getProductRoutes() {
        return () ->
        {
            get("/{id}", productController::getById, Role.ANYONE);
            get("/", productController::getAll, Role.ANYONE);
            post("/", productController::create, Role.ADMIN);
            put("/{id}", productController::update, Role.ADMIN);
            delete("/{id}", productController::delete, Role.ADMIN);

        };
    }
}


