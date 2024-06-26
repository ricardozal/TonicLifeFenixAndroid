package mx.com.bigtechsolutions.toniclifefenix.commons;

public class Constants {

    static boolean envProd = true;

    public static final String API_TONIC_LIFE_FENIX_URL_PROD = "https://gjana.com.mx";
    public static final String API_TONIC_LIFE_FENIX_URL_DEV = "https://tonic-dev.bigtechsolutions.mx/public";

    public static final String API_TONIC_LIFE_FENIX_URL = envProd ? Constants.API_TONIC_LIFE_FENIX_URL_PROD : Constants.API_TONIC_LIFE_FENIX_URL_DEV ;

    public static final String API_TONIC_LIFE_FENIX_BASE_URL = API_TONIC_LIFE_FENIX_URL + Constants.BASE_API;
    public static final String BASE_API = "/api/";

    // Preferences

    public static final String DISTRIBUTOR_ID = "DISTRIBUTOR_ID";
    public static final String DISTRIBUTOR_NAME = "DISTRIBUTOR_NAME";
    public static final String DISTRIBUTOR_EMAIL = "DISTRIBUTOR_EMAIL";
    public static final String DISTRIBUTOR_TONIC_LIFE_ID = "DISTRIBUTOR_TONIC_LIFE_ID";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String CURRENT_POINTS = "CURRENT_POINTS";
    public static final String DIST_COUNTRY = "DIST_COUNTRY";

    public static final String BRANCH_ID = "BRANCH_ID";
    public static final String COUNTRY = "COUNTRY";

    public static final String NAME_PRODUCT_SEARCH = "NAME_PRODUCT_SEARCH";

    public static final String STATE_NAME = "STATE_NAME";
    public static final String CURRENT_COUNTRY = "CURRENT_COUNTRY";

    // APIs

    public static final String STRIPE_PUBLIC_KEY = "pk_test_51HB9ItGBCPqeJ6dQ6S1Z1s2vcPg7S75x0Zp9iEyIwcN1QZkQ55cDwMLNzIYDisU7rrOcT4uWzVAEoYgDzIOVtjhg00cnRpwmVZ";
    public static final String PAYPAL_CLIENT_ID = "AW2vGNO15ZK8tNZWIlpY4Hj5jmqzYQZsdnm54rvF_WNLSj4oiJTHBfDfGgmLIlERh3YZbjWTVXvxJoD-";

}
