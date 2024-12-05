package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private ProductRoute productRoutes = new ProductRoute();

    public EndpointGroup getApiRoutes() {
        return () ->
        {
            path("/products", productRoutes.getProductRoutes());
        };
    }
}
