package app.routes;

import app.config.HibernateConfig;
import app.controllers.ShoppingListController;
import app.daos.ShoppingListDAO;
import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import static io.javalin.apibuilder.ApiBuilder.*;

public class ShoppingListRoute {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final ShoppingListDAO shoppingListDAO = new ShoppingListDAO(emf);
    private final ShoppingListController shoppingListController = new ShoppingListController(shoppingListDAO);

    public EndpointGroup getShoppingListRoutes() {
        return () ->
        {
            get("/{id}", shoppingListController::getById, Role.ANYONE);
            get("/", shoppingListController::getAll, Role.ANYONE);
            post("/", shoppingListController::create, Role.ADMIN);
            put("/{id}", shoppingListController::update, Role.ADMIN);
            delete("/{id}", shoppingListController::delete, Role.ANYONE);

        };
    }
}
