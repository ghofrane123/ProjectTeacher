package wolfsoft.routes.App;

/**
 * Created by G on 15/11/2017.
 */

public class AppConfig {
    // Server user login url

     String  user_id;


    // url to create new product
    private static String url_create_product = "http://192.168.153.1/TA/create_cours.php";
    public static String URL_LOGIN = "http://192.168.153.1/TA/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://192.168.153.1/TA/register.php";

    public String url_fetch = "http://192.168.153.1/TA/user_cours.php?user_id=" + user_id;
    public static String url_fetch1 = "http://192.168.153.1/TA/all_cours.php";
    public static String url_locations = "http://192.168.153.1/TA/all_location.php";


}

