package app.config;

import app.controllers.ExceptionController;
import app.exceptions.ApiException;
import app.routes.Routes;
import app.security.controllers.AccessController;
import app.security.routes.SecurityRoutes;
import app.utils.ApiProps;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppConfig {

    private static final Routes routes = new Routes();
    private static final ExceptionController exceptionController = new ExceptionController();
    private static final AccessController accessController = new AccessController();

    private static void configuration(JavalinConfig config) {

        config.router.contextPath = ApiProps.API_CONTEXT;
        config.bundledPlugins.enableRouteOverview("/routes");
        config.bundledPlugins.enableDevLogging();
        config.router.apiBuilder(routes.getApiRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());

    }

    private static void exceptionContext(Javalin app) {

        app.exception(ApiException.class, exceptionController::apiExceptionHandler);
        app.exception(Exception.class, exceptionController::exceptionHandler);


    }

    public static void startServer() {
        var app = io.javalin.Javalin.create(AppConfig::configuration);
        exceptionContext(app);
        app.beforeMatched(accessController::accessHandler);
        app.before(AppConfig::corsHeaders);
        app.options("/*", AppConfig::corsHeadersOptions);
        //app.error(404, ctx -> ctx.json("Resource not found"));
        app.start(ApiProps.PORT);
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }

    private static void corsHeaders(Context ctx) {
        ctx.header("Access-Control-Allow-Origin", "*");
        ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        ctx.header("Access-Control-Allow-Credentials", "true");
    }

    private static void corsHeadersOptions(Context ctx) {
        ctx.header("Access-Control-Allow-Origin", "*");
        ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        ctx.header("Access-Control-Allow-Credentials", "true");
        ctx.status(204);
    }
}
