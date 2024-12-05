package app.utils;

public class ApiProps {

    // == HIBERNATE CONFIG FILE ==
    public static final String DB_NAME = "products";
    public static final String DB_USER = "postgres";
    public static final String DB_PASS = "postgres";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;

    public static final String IP = System.getenv("IP");
    public static final String DB_USER_WEBSERVER = System.getenv("DB_USER_WEBSERVER");
    public static final String DB_PASS_WEBSERVER = System.getenv("DB_PASS_WEBSERVER");
    public static final String DB_URL_WEBSERVER = "jdbc:postgresql://" + IP + ":5432/" + DB_NAME;

    // == API CONFIG ==
    public static final int PORT = 7001;
    public static final String API_CONTEXT = "/api";
}
